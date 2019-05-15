package com.easyCourse.dao;

import com.easyCourse.entity.Student;

import java.util.List;

public interface IStudentDao {

    //登录验证
    Student verify(String studentId, String passwd);

    //根据studentId查询学生
    Student selectStudentById(String id);

    //根据email查询学生
    Student selectStudentByEmail(String email);

    //增加学生记录
    void insert(String studentId, String passwd, String email,String studentName);

    //根据homeworkId查询提交了该作业的学生
    List<String> selectStudentByHomeworkId(String homeworkId);
}
