package com.easyCourse.dao;

import com.easyCourse.model.Student;

public interface IStudentDao {
    Student selectStudentById(String id);

    //提供初次身份验证
    Student verify(String studentId, String passwd);
}
