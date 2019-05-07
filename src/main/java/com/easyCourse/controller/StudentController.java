package com.easyCourse.controller;

import com.easyCourse.entity.Student;
import com.easyCourse.service.IStudentService;
import com.easyCourse.utils.Jwt;
import com.easyCourse.utils.StatusCode;
import com.easyCourse.utils.StringUtils;
import net.minidev.json.JSONObject;
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

    @GetMapping("/index")
    public String getIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "student/index";
    }

    //返回登录页面
    @GetMapping("/login/index")
    public String getLoginIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "login";
    }

    //返回注册页面
    @GetMapping("/register")
    public String getRegisterIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "/register";
    }

    //返回学生主页
    @GetMapping("/index")
    public String getIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "student/index";
    }

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
}
