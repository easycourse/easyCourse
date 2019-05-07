package com.easyCourse.controller;

import com.easyCourse.entity.Teacher;
import com.easyCourse.service.LessonService;
import com.easyCourse.service.TeacherService;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    //TODO:返回老师发布的历史通知
    @GetMapping("//notice/index")
    @ResponseBody
    public JSONObject getNotice(HttpSession session) {
        // 从session中获取教师信息
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        return null;
    }

    //TODO:添加通知
    @PostMapping("/addNotice")
    @ResponseBody
    public JSONObject addNotice(@RequestParam(value = "title", required = true) String title, @RequestParam(value = "noticeType", required = true) int noticeType,
                                @RequestParam(value = "detail", required = false) String detail, @RequestParam(value = "appendix", required = false) String appendix,HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();

        return null;
    }
}
