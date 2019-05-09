package com.easyCourse.controller;

import com.easyCourse.entity.Student;
import com.easyCourse.service.IStudentService;
import com.easyCourse.utils.Jwt;
import com.easyCourse.utils.StatusCode;
import com.easyCourse.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Resource
    private IStudentService studentService;

    //返回登录页面
    @GetMapping("/login")
    public String getLoginIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "login";
    }

    //返回注册页面
    @GetMapping("/register")
    public String getRegisterIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "register";
    }

    //返回课程主页
    @GetMapping("/index")
    public String getIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "student/index";
    }

    //返回课程详情页面
    @GetMapping("/courseIndex")
    public String getcourseIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "student/courseIndex";
    }

    //返回通知列表
    @GetMapping("/informList")
    public String getInformListIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "student/informList";
    }

    //返回作业列表
    @GetMapping("/homeworkList")
    public String getHomeworkListIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "student/homeworkList";
    }

    //返回课件列表
    @GetMapping("/resourceList")
    public String getResourceListIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "student/resourceList";
    }

    /****登录注册模块*****/
    //执行登录请求数据处理
    @RequestMapping(value = "/login/verify", method = RequestMethod.POST)
    public void loginVerify(@RequestParam(value = "studentId", required = true) String studentId, @RequestParam(value = "passwd", required = true)
            String passwd, HttpSession session, HttpServletResponse response) throws IOException {
        Student student = this.studentService.loginVerify(studentId, StringUtils.MD5(passwd));
        JSONObject resultJSON = new JSONObject();
        //如果验证未通过
        if (student == null) {
            resultJSON.put("status", StatusCode.INCORRECT_PASSWORD);
            resultJSON.put("msg", "用户名或密码错误");
            response.getWriter().write(String.valueOf(resultJSON));
        } else {
            //创建返回resultJson
            Map<String, Object> payload = new HashMap<String, Object>();
            Date date = new Date();
            payload.put("uid", studentId);//用户ID
            payload.put("iat", date.getTime());//生成时间
            payload.put("ext", date.getTime() + 1000 * 60 * 60);//过期时间1小时
            String token = Jwt.createToken(payload);
            resultJSON.put("status", StatusCode.SUCCESS);
            resultJSON.put("msg", "登陆成功");
            resultJSON.put("token", token);
            //存储session
            session.setAttribute("userToken", token);
            session.setMaxInactiveInterval(3600);//session过期时间为1个小时
            session.setAttribute("student", student);
            response.getWriter().write(String.valueOf(resultJSON));
        }
    }

    //执行注册数据处理
    @RequestMapping(value = "/register/verify", method = RequestMethod.POST)
    public void registerVerify(@RequestParam(value = "studentId", required = true) String studentId, @RequestParam(value = "passwd", required = true)
            String passwd, @RequestParam(value = "email", required = true) String email,@RequestParam(value = "studentName", required = true) String studentName, HttpSession session, HttpServletResponse response) throws IOException {
        int code = studentService.registerVerify(studentId,StringUtils.MD5(passwd),email,studentName);
        JSONObject resultJSON = new JSONObject();
        switch (code){
            case StatusCode.DUPLICATED_STUDENTID:{
                resultJSON.put("status", StatusCode.DUPLICATED_STUDENTID);
                resultJSON.put("msg", "该学号已经被注册，请重试");
                response.getWriter().write(String.valueOf(resultJSON));
                break;
            }
            case StatusCode.DUPLICATED_EMAIL:{
                resultJSON.put("status", StatusCode.DUPLICATED_EMAIL);
                resultJSON.put("msg", "该邮箱已经被注册，请重试");
                response.getWriter().write(String.valueOf(resultJSON));
                break;
            }
            case StatusCode.SUCCESS:{
                Map<String, Object> payload = new HashMap<>();
                Date date = new Date();
                payload.put("uid", studentId);//用户ID
                payload.put("iat", date.getTime());//生成时间
                payload.put("ext", date.getTime() + 1000 * 60 * 60);//过期时间1小时
                String token = Jwt.createToken(payload);
                resultJSON.put("status", StatusCode.SUCCESS);
                resultJSON.put("msg", "注册成功");
                resultJSON.put("token", token);
                //存储session
                Student student = this.studentService.loginVerify(studentId, StringUtils.MD5(passwd));
                session.setAttribute("userToken", token);
                session.setMaxInactiveInterval(3600);//session过期时间为1个小时
                session.setAttribute("student", student);
                response.getWriter().write(String.valueOf(resultJSON));
                break;
            }
        }
    }


    /****TODO:消息通知模块*****/
    //执行登录请求数据处理
    @RequestMapping(value = "/inform", method = RequestMethod.GET)
    public void getInform(HttpSession session) throws IOException {
        Student student = (Student)session.getAttribute("student");
        String studentId = student.getStudent_id();
        //根据studentID查询得到所有与学生相关的通知
        //提示：1. 新建StudentNotice类 包含属性id，lesson_id,teacher_id,title，detail，noticeType，create——time，is_delete,appendix，发布人的姓名（要联合teacher表查询）
        // 2. 多表联合查询  student_lesson,lesson_notice,user_teacher多表联合 查学生选了课程相关的notice
        //（具体返回类的属性应该考虑前端的需求，比如这里前端需要的数据有 xxx,xxxx,xxxxx,x等）
        //返回notice作为json
    }

    /****TODO:课程信息相关模块*****/
    //执行登录请求数据处理
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public void getCourse(HttpSession session) throws IOException {
        Student student = (Student)session.getAttribute("student");
        String studentId = student.getStudent_id();
        //根据studentID查询得到所有与学生相关课程信息
        //提示：1. 新建StudentLesson类
        // 2. 多表联合查询  student_lesson,lesson,teacher多表联合
        //返回结果作为json
        //（具体返回类的属性应该考虑前端的需求，比如这里前端需要的数据有 课程名称，课程详情，课程时间，授课人，授课人联系方式等）
    }

    /****TODO:作业模块*****/
    //执行登录请求数据处理
    @RequestMapping(value = "/homework", method = RequestMethod.GET)
    public void getHomework(HttpSession session) throws IOException {
        Student student = (Student)session.getAttribute("student");
        String studentId = student.getStudent_id();
        //根据studentID查询得到所有与学生相关的作业
        //提示：1. 新建StudentHomework类 除了homework自身属性外，应该还有发布老师的相关属性，发布课程的相关属性（课程名）
        // 2. 多表联合查询  student_homework,lesson,user_teacher多表联合
        //（具体返回类的属性应该考虑前端的需求，比如这里前端需要的数据有 xxx,xxxx,xxxxx,x等）
        //返回查询结果作为json
    }

    /****TODO:课件模块*****/
    //执行登录请求数据处理
    @RequestMapping(value = "/resource", method = RequestMethod.GET)
    public void getResource(HttpSession session) throws IOException {
        Student student = (Student)session.getAttribute("student");
        String studentId = student.getStudent_id();
        //根据studentID查询得到所有与学生相关的课件
        //提示：1. 新建StudentFile类 除了file自身属性外，应该还有发布老师的相关属性，发布课程的相关属性（课程名）
        // 2. 多表联合查询  file,lesson,user_teacher多表联合
        //（具体返回类的属性应该考虑前端的需求，比如这里前端需要的数据有 xxx,xxxx,xxxxx,x等）
        //返回查询结果作为json
    }

}
