package com.easyCourse.controller;

import com.easyCourse.model.Student;
import com.easyCourse.service.IStudentService;
import com.easyCourse.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    public String verify(@RequestParam(value="studentId", required=true) String studentId, @RequestParam(value="passwd", required=true)
            String passwd, HttpSession session){
        Student student = this.studentService.verify(studentId, StringUtils.MD5(passwd));
        //如果验证未通过
        if(student == null){
            //应该返回一个视图
            return "请重新登录";
        }
        else {
            session.setAttribute("studentId",student.getStudent_id());
            return "登录成功";
        }
    }
}
