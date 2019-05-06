package com.easyCourse.controller;

import com.easyCourse.model.Teacher;
import com.easyCourse.service.LessonService;
import com.easyCourse.service.TeacherService;
import com.easyCourse.utils.Jwt;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * Created by devin
 * 2019-05-06 17:26
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private LessonService lessonService;

    @PostMapping("/register/index")
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

    @PostMapping("/login/verify")
    @ResponseBody
    public JSONObject login(@RequestParam(value = "teacherId", required = true) String teacherId,
                            @RequestParam(value = "passwd", required = true) String password, HttpSession session) {

        JSONObject result = teacherService.login(teacherId, password);

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

    @GetMapping("/center")
    @ResponseBody
    public JSONObject center(HttpSession session) {
        // 从session中获取教师信息
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();

        return lessonService.getByTeacherId(teacherId);
    }

    @PostMapping("/addLesson")
    @ResponseBody
    public JSONObject addLesson(@RequestParam(value = "lessonName", required = true) String lessonName, @RequestParam(value = "lessonTime", required = true) String lessonTime,
                                @RequestParam(value = "lessonDetail", required = false) String lessonDetail, HttpSession session) {
        // 从session中获取教师信息
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();

        return lessonService.addLesson(lessonName, lessonTime, lessonDetail, teacherId);
    }
}
