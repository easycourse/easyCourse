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
        return "register";
    }

    //返回通知页面
    @GetMapping("/informManage")
    public String getInformManageIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "teacher/informManage";
    }

    //返回课件页面
    @GetMapping("/resourceManage")
    public String getResourceManageIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "teacher/resourceManage";
    }

    //返回作业管理页面
    @GetMapping("/homeworkManage")
    public String getHomeworkManageIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "teacher/homeworkManage";
    }

    //添加课程管理页面
    @GetMapping("/addCourse")
    public String getAddCourseIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "teacher/addCourse";
    }

    //添加通知页面
    @GetMapping("/addInform")
    public String getAddInformIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "teacher/addInform";
    }

    //添加作业页面
    @GetMapping("/addHomework")
    public String getAddHomeworkIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "teacher/addHomework";
    }

    //上传课件页面
    @GetMapping("/addResource")
    public String getAddResourceIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "teacher/addResource";
    }

    //作业批改界面
    @GetMapping("/homeworkCorrect")
    public String getHomeworkCorrectIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "teacher/homeworkCorrect";
    }


    /************登录注册模块**************/
    //注册接口
    @PostMapping("/register/verify")
    public void register(@RequestParam(value = "teacherId", required = true) String teacherId, @RequestParam(value = "passwd", required = true) String password,
                               @RequestParam(value = "teacherName", required = true) String teacherName, @RequestParam(value = "phone", required = true) String phone,
                               @RequestParam(value = "mail", required = true) String mail, @RequestParam(value = "location", required = true) String location,
                         HttpServletResponse httpServletResponse,HttpSession session) throws IOException  {
        JSONObject result = teacherService.register(teacherId, password, teacherName, phone, mail, location);
        if (result.get("data") != null) {
            Map data = (Map) result.get("data");
            Object token = data.get("token").toString();
            session.setAttribute("userToken", token);
            session.setMaxInactiveInterval(3600);
            Teacher teacher = teacherService.getTeacherById(teacherId);
            session.setAttribute("teacher", teacher);
            httpServletResponse.getWriter().write(String.valueOf(result));
        }
    }

    //登录验证
    @PostMapping("/login/verify")
    public void login(@RequestParam(value = "teacherId", required = true) String teacherId,
                            @RequestParam(value = "passwd", required = true) String password, HttpSession session,HttpServletResponse httpServletResponse)throws IOException{
        JSONObject result = teacherService.login(teacherId, password);
        // 登录成功
        if (result.get("data") != null) {
            Map data = (Map) result.get("data");
            String token = data.get("token").toString();
            session.setAttribute("userToken", token);
            session.setMaxInactiveInterval(3600);
            Teacher teacher = teacherService.getTeacherById(teacherId);
            session.setAttribute("teacher", teacher);
            httpServletResponse.getWriter().write(String.valueOf(result));
        }
    }


    /***************************************课程管理模块*********************************************************/
    //教师主页数据，获取教师教授的课程信息
    @GetMapping("/course")
    @ResponseBody
    public JSONObject course(HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        JSONObject result = lessonService.getByTeacherId(teacherId);
        return result;
    }

    //教师添加课程
    @PostMapping("/addLesson")
    @ResponseBody
    public JSONObject addLesson(@RequestParam(value = "lessonName", required = true) String lessonName, @RequestParam(value = "lessonTime", required = true) String lessonTime,
                                @RequestParam(value = "lessonDetail", required = false) String lessonDetail, HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        return lessonService.addLesson(lessonName, lessonTime, lessonDetail, teacherId);
    }

    //教师添加学生选课记录
    @PostMapping("/importStudents")
    @ResponseBody
    public JSONObject importStudents(@RequestParam(value = "lessonId", required = true) String lessonId,
                          @RequestParam(value = "studentList", required = true) String studentList,HttpSession session) {
        JSONArray jsonArray = JSONArray.parseArray(studentList);
        Map<String,String> studentInfoList = new HashMap<>();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject job = jsonArray.getJSONObject(i);
            studentInfoList.put(job.get("studentId").toString(),lessonId);
        }
        return teacherService.importStudent(studentInfoList);
    }

    /****************************************通知模块******************************************************/
    //查看发布的历史通知
    @GetMapping("/inform")
    public void getInformData(HttpServletResponse httpServletResponse, HttpSession session) throws IOException {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        List<LessonNotice> lessonNoticeList = lessonService.getNoticeListByTeacherId(teacherId);
        JSONObject result = new JSONObject();
        result.put("status",StatusCode.SUCCESS);
        result.put("lessonNoticeList", lessonNoticeList);
        httpServletResponse.getWriter().write(String.valueOf(result));
    }

    //TODO：根据类型筛选通知
    @GetMapping("/searchInform")
    public void getInformDataByType(@RequestParam(value = "noticeType",required = true) String noticeType, HttpServletResponse httpServletResponse, HttpSession session) throws IOException {
        //根据noticeType进行筛选通知
    }

    //发布新通知
    @PostMapping("/inform")
    public void addNotice(@RequestBody JSONObject body,  HttpServletResponse httpServletResponse, HttpSession session) throws IOException {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();

        JSONArray lessonIdList = body.getJSONArray("lessonIdList");
        String title = body.getString("title");
        int noticeType = Integer.parseInt(body.getString("noticeType"));
        String detail = body.getString("detail");
        String appendix = body.getString("appendix");
        JSONObject result = lessonService.addNotice(lessonIdList, teacherId, title, noticeType, detail, appendix);
        httpServletResponse.getWriter().write(String.valueOf(result));
    }


    /****************************************课件模块******************************************************/
    //课件主页
    @GetMapping("/courseware")
    public void getCoursewareIndexData(HttpSession httpSession, HttpServletResponse httpServletResponse) throws IOException {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        httpServletResponse.setCharacterEncoding("UTF-8");
        List<LessonFile> lessonFileList = lessonService.getLessonFileListByTeacherId(teacherId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",StatusCode.SUCCESS);
        jsonObject.put("lessonFileList",lessonFileList);

        httpServletResponse.getWriter().write(String.valueOf(jsonObject));
    }

    //根据名称搜索相关课件
    @GetMapping("/searchCourseware")
    @ResponseBody
    public JSONObject getCoursewareByLessonName(@RequestParam(value = "lessonName") String lessonName,HttpSession session) {
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

    //添加课件
    @PostMapping("/courseware")
    public void addCourseware(@RequestBody String param,  HttpSession session, HttpServletResponse httpServletResponse) throws IOException {
        JSONObject body = JSONObject.parseObject(param);
        String title = body.getString("title");
        JSONArray lessonIdList = body.getJSONArray("lessonIdList");
        String detail = body.getString("detail");
        String appendix = body.getString("appendix");
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
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

    //TODO:******************************************:作业模块********************************************/
    //查看发布的作业
    @GetMapping("/homework")
    public void getHomework(HttpSession session) throws IOException {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        //包含信息有 课程	作业标题	作业描述	发布时间	截至时间	提交人数
    }

    //根据id查看某个作业的提交情况(包括提交的学生姓名、时间、学号、分数等等)
    @GetMapping("/homework/{homeworkId}")
    @ResponseBody
    public JSONObject getHomeworkByHomeworkId(@PathVariable(value = "homeworkId") String homeworkId,HttpSession session) {
        return null;
    }

    //发布新作业
    @PostMapping("/homework")
    public void addHomework(@RequestBody String param, HttpSession session) throws IOException {
        JSONObject body = JSONObject.parseObject(param);
        String title = body.getString("title");
        JSONArray lessonIdList = body.getJSONArray("lessonIdList");
        String detail = body.getString("detail");
        String dueTime = body.getString("dueTime");

        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();

    }

    //导入学生成绩
    @PostMapping("/correctHomework")
    public void correctHomework(@RequestParam(value = "homeworkId", required = true) String lessonId,
                                @RequestParam(value = "studentList", required = true) String studentList,@RequestParam(value = "scoreList", required = true) String scoreList,HttpSession session) throws IOException {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();

        //可以参考importStudents接口里面相关的mybatis导入map的操作
        JSONArray idArray = JSONArray.parseArray(studentList);
        JSONArray scoreArray = JSONArray.parseArray(scoreList);


        Map<String,String> studentScoreInfoList = new HashMap<>();
        for(int i=0;i<idArray.size();i++){
            JSONObject id = idArray.getJSONObject(i);
            JSONObject score = scoreArray.getJSONObject(i);
            studentScoreInfoList.put(id.getString("studentId"),score.getString("score"));
        }

    }
}
