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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
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

    //查看发布的历史通知
    @GetMapping("/notice/index")
    public String getNotice(Model model, HttpSession session) {
        // 从session中获取教师信息
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        //根据teacherId查询得到一个notice的list 然后放在resultBean里面返回，status用StatusCode.success表示成功


        List<LessonNotice> lessonNoticeList = lessonService.getNoticeListByTeacherId(teacherId);

        model.addAttribute("lessonNoticeList", lessonNoticeList);

        return "teacher/notice/index";
    }

    @PostMapping("/addNotice")
    @ResponseBody
    public JSONObject addNotice(@RequestBody JSONObject body,  HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();

        JSONArray lessonIdList = body.getJSONArray("lessonIdList");
        String title = body.getString("title");
        int noticeType = Integer.parseInt(body.getString("noticeType"));
        String detail = body.getString("detail");
        String appendix = body.getString("appendix");



        //和添加课程一样，根据参数然后添加 根据结果进一步判断，status用StatusCode.success表示成功
        JSONObject result = lessonService.addNotice(lessonIdList, teacherId, title, noticeType, detail, appendix);
        return result;
    }

    //TODO:等待lessonDao和lessonService完成后继续实现
    @PostMapping("/courseware/index")
    public String getCourseware(@RequestParam(value = "lessonName", required = false) String lessonName, Model model, HttpSession session) {

        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        //根据teacherId查到一个lesson_file的list，然后放在resultBean里面返回
        List<LessonFile> lessonFileList = lessonService.getLessonFileListByTeacherId(teacherId);
        List<LessonFile> newLessonFileList;
        if(lessonName!=null){
            newLessonFileList = new ArrayList<>();
            for(int i = 0; i < lessonFileList.size(); i++){
                String lessonId = lessonFileList.get(i).getLessonId();

                //todo:根据lessonId获取lesson，然后获取lessonName，删除和lessonName
                Lesson lesson = null;

                if(lesson.getLessonName().equals(lessonName)){
                    newLessonFileList.add(lessonFileList.get(i));

                }
            }
        } else {
            newLessonFileList = lessonFileList;
        }

        model.addAttribute("lessonFileList", newLessonFileList);


        //根据原型图，老师可以直接查看所有发布的课件，这个时候lessonName就是null/"" ，如果老师输入了lessonName，就要对上面的list进行一次过滤，返回符合结果的list
        return "teacher/courseware/index";
    }

    @PostMapping("/addCourseware")
    @ResponseBody
    public JSONObject addCourseware(@RequestBody JSONObject body,  HttpSession session) {


        String title = body.getString("title");
        JSONArray lessonIdList = body.getJSONArray("lessonIdList");
        int noticeType = Integer.parseInt(body.getString("noticeType"));
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

        return lessonService.addLessonFile(lessonFileList);

        /*以下为mapper可以批量插入list的代码，仅做参考 可以将上述lessonFileList作为参数然后插入数据库
        <insert id="insertForeach" parameterType="java.util.List" useGeneratedKeys="false">
                insert into fund
        ( id,fund_name,fund_code,date_x,data_y,create_by,create_date,update_by,update_date,remarks,del_flag)
        values
                <foreach collection="list" item="item" index="index" separator=",">
                (
    					#{item.id},
    					#{item.fundName},
    					#{item.fundCode},
    					#{item.dateX},
    					#{item.dataY},
    					#{item.createBy},
    					#{item.createDate},
    					#{item.updateBy},
    					#{item.updateDate},
    					#{item.remarks},
    					#{item.delFlag}
    				)
    		     </foreach>
        </insert>*/
    }
}
