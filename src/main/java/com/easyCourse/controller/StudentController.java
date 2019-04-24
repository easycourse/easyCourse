package com.easyCourse.controller;

import com.easyCourse.model.Student;
import com.easyCourse.service.IStudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}
