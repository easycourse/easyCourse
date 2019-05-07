package com.easyCourse.dao;

import com.easyCourse.entity.Student;

public interface IStudentDao {

    //登录验证
    Student verify(String studentId, String passwd);

    //根据studentId查询学生
    Student selectStudentById(String id);

    //根据email查询学生
    Student selectStudentByEmail(String email);

    //增加学生记录
    void insert(String studentId, String passwd, String email,String studentName);
}
