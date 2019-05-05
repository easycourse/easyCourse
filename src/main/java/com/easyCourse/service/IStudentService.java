package com.easyCourse.service;

import com.easyCourse.model.Student;

public interface IStudentService {
    //学生登录验证
    public Student loginVerify(String studentId,String passwd);

    //学生注册验证
    public int registerVerify(String studentId,String passwd,String email,String studentName);
}
