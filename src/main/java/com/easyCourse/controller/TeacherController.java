package com.easyCourse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.easyCourse.entity.Lesson;
import com.easyCourse.entity.LessonFile;
import com.easyCourse.entity.LessonNotice;
import com.easyCourse.entity.Teacher;
import com.easyCourse.service.LessonService;
import com.easyCourse.service.TeacherService;
//import net.minidev.json.JSONArray;
//import net.minidev.json.JSONObject;
import com.easyCourse.utils.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @Resource
    private LessonService lessonService;

    //返回教师主页
    @GetMapping("/index")
    public String getIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "teacher/index";
    }

    //返回注册页面
    @GetMapping("/register")
    public String getRegisterIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "/register";
    }

    //todo:修改返回值为void，并用httpServletResponse.getWriter().write存储JSON
    //注册验证
    @PostMapping("/register/verify")
    @ResponseBody
    public JSONObject register(@RequestParam(value = "teacherId", required = true) String teacherId, @RequestParam(value = "passwd", required = true) String password,
                               @RequestParam(value = "teacherName", required = true) String teacherName, @RequestParam(value = "phone", required = true) String phone,
                               @RequestParam(value = "mail", required = true) String mail, @RequestParam(value = "location", required = true) String location,
                               HttpSession session) {
        JSONObject result = teacherService.register(teacherId, password, teacherName, phone, mail, location);

        // 注册成功，则获取token和教师信息，保存到session中
        if (result.get("data") != null) {
            Map data = (Map) result.get("data");
            Object token = data.get("token").toString();
            session.setAttribute("userToken", token);
            session.setMaxInactiveInterval(3600);
            Teacher teacher = teacherService.getTeacherById(teacherId);
            session.setAttribute("teacher", teacher);
        }
        return result;
    }

    //todo:修改返回值为void，并用httpServletResponse.getWriter().write存储JSON
    //登录验证
    @PostMapping("/login/verify")
    @ResponseBody
    public JSONObject login(@RequestParam(value = "teacherId", required = true) String teacherId,
                            @RequestParam(value = "passwd", required = true) String password, HttpSession session) {
        JSONObject result = teacherService.login(teacherId, password);

        // 登录成功
        if (result.get("data") != null) {
            Map data = (Map) result.get("data");
            String token = data.get("token").toString();
            session.setAttribute("userToken", token);
            session.setMaxInactiveInterval(3600);
            Teacher teacher = teacherService.getTeacherById(teacherId);
            session.setAttribute("teacher", teacher);
        }

        return result;
    }


    //todo:修改返回值为void，并用httpServletResponse.getWriter().write存储JSON
    //教师添加课程
    @PostMapping("/addLesson")
    @ResponseBody
    public JSONObject addLesson(@RequestParam(value = "lessonName", required = true) String lessonName, @RequestParam(value = "lessonTime", required = true) String lessonTime,
                                @RequestParam(value = "lessonDetail", required = false) String lessonDetail, HttpSession session) {
        // 从session中获取教师信息
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();

        return lessonService.addLesson(lessonName, lessonTime, lessonDetail, teacherId);
    }

    //返回老师教授的课程信息
    @GetMapping("/center")
    @ResponseBody
    public JSONObject center(HttpSession session) {
        // 从session中获取教师信息
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        return lessonService.getByTeacherId(teacherId);
    }

    //通知主页
    @GetMapping("/notice/index")
    public String getNoticeIndexPage(HttpServletRequest httpServletRequest) {
        return "teacher/courseware/getIndexInfo";
    }

    //查看发布的历史通知
    @PostMapping("/notice/getIndexInfo")
    public void getNoticeIndexData(HttpServletResponse httpServletResponse, HttpSession session) throws IOException {
        // 从session中获取教师信息
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        //根据teacherId查询得到一个notice的list 然后放在resultBean里面返回，status用StatusCode.success表示成功
        List<LessonNotice> lessonNoticeList = lessonService.getNoticeListByTeacherId(teacherId);

        JSONObject result = new JSONObject();
        result.put("lessonNoticeList", lessonNoticeList);
        httpServletResponse.getWriter().write(String.valueOf(result));
    }

    @PostMapping("/addNotice")
    @ResponseBody
    public void addNotice(@RequestBody JSONObject body,  HttpServletResponse httpServletResponse, HttpSession session) throws IOException {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();

        JSONArray lessonIdList = body.getJSONArray("lessonIdList");
        String title = body.getString("title");
        int noticeType = Integer.parseInt(body.getString("noticeType"));
        String detail = body.getString("detail");
        String appendix = body.getString("appendix");

        //和添加课程一样，根据参数然后添加 根据结果进一步判断，status用StatusCode.success表示成功
        JSONObject result = lessonService.addNotice(lessonIdList, teacherId, title, noticeType, detail, appendix);

        httpServletResponse.getWriter().write(String.valueOf(result));
    }

    //课件主页
    @GetMapping("/courseware/index")
    public String getCoursewareIndexPage(HttpServletRequest httpServletRequest) {
        return "teacher/courseware/getIndexInfo";
    }

    //课件主页
    @GetMapping("/courseware/getIndexInfo")
    public void getCoursewareIndexData(HttpSession httpSession, HttpServletResponse httpServletResponse) throws IOException {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        //根据teacherId查到一个lesson_file的list，然后放在resultBean里面返回
        List<LessonFile> lessonFileList = lessonService.getLessonFileListByTeacherId(teacherId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lessonFileList",lessonFileList);

        httpServletResponse.getWriter().write(String.valueOf(jsonObject));
    }

    //todo:修改返回值为void，并用httpServletResponse.getWriter().write存储JSON
    //根据名称搜索相关课件
    @GetMapping("/courseware/index/{lessonName}")
    @ResponseBody
    public JSONObject getCoursewareByLessonName(@PathVariable(value = "lessonName") String lessonName,HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        //根据teacherId查到一个lesson_file的list，然后放在resultBean里面返回
        List<LessonFile> lessonFileList = lessonService.getLessonFileListByTeacherId(teacherId);
        List<LessonFile> newLessonFileList;
        if(lessonName!=null){
            newLessonFileList = new ArrayList<>();
            for(int i = 0; i < lessonFileList.size(); i++){
                if(lessonFileList.get(i).getLessonName().contains(lessonName)){
                    newLessonFileList.add(lessonFileList.get(i));
                }
            }
        } else {
            newLessonFileList = lessonFileList;
        }
        JSONObject resultJSON = new JSONObject();
        resultJSON.put("status", StatusCode.SUCCESS);
        resultJSON.put("msg", "搜索成功");
        resultJSON.put("data", newLessonFileList);
        return resultJSON;
    }


    //测试完成，添加课件接口
    @PostMapping("/addCourseware")
    @ResponseBody
    public void addCourseware(@RequestBody String param,  HttpSession session, HttpServletResponse httpServletResponse) throws IOException {

        JSONObject body = JSONObject.parseObject(param);

        String title = body.getString("title");
        JSONArray lessonIdList = body.getJSONArray("lessonIdList");
        String detail = body.getString("detail");
        String appendix = body.getString("appendix");


        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        //因为可以选择多个课程，所以会有lessonIdList，需要将上述数据一次性插入到数据库中

        //得到lessonFileList
        List<LessonFile> lessonFileList = new ArrayList<>();
        for(int i=0;i<lessonIdList.size();i++){
            LessonFile lessonFile = new LessonFile();
            lessonFile.setLessonId(lessonIdList.getString(i));
            lessonFile.setTitle(title);
            lessonFile.setAppendix(appendix);
            lessonFile.setDetail(detail);
            lessonFile.setUserId(teacherId);
            lessonFileList.add(lessonFile);
        }

        JSONObject result = lessonService.addLessonFile(lessonFileList);

        httpServletResponse.getWriter().write(String.valueOf(result));
    }
}
