package com.easyCourse.controller;

import com.easyCourse.model.Student;
import com.easyCourse.service.IStudentService;
import com.easyCourse.utils.Jwt;
import com.easyCourse.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.json.JsonObject;
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

    @RequestMapping("/showStudent.do")
    public void selectStudentById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("id");
        Student student = this.studentService.selectStudent(userId);
        System.out.println(student.getStudent_name());
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(mapper));
        response.getWriter().close();
    }

    @RequestMapping(value = "/login/verify",method = RequestMethod.POST)
    public JSONObject verify(@RequestParam(value="studentId", required=true) String studentId, @RequestParam(value="passwd", required=true)
            String passwd,HttpSession session){
        Student student = this.studentService.verify(studentId, StringUtils.MD5(passwd));
        JSONObject resultJSON=new JSONObject();
        //如果验证未通过
        if(student == null){
            resultJSON.put("success", false);
            resultJSON.put("msg", "用户名密码不对");
            return resultJSON;
        }
        else {
            //创建返回resultJson
            Map<String , Object> payload=new HashMap<String, Object>();
            Date date=new Date();
            payload.put("uid", studentId);//用户ID
            payload.put("iat", date.getTime());//生成时间
            payload.put("ext",date.getTime()+1000*60*60);//过期时间1小时
            String token= Jwt.createToken(payload);
            resultJSON.put("success", true);
            resultJSON.put("msg", "登陆成功");
            resultJSON.put("token", token);
            //存储session
            session.setAttribute("userToken",token);
            session.setMaxInactiveInterval(3600);//session过期时间为1个小时
            session.setAttribute("student",student);
            return resultJSON;
        }
    }
}
