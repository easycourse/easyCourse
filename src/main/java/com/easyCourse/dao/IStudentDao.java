package com.easyCourse.dao;

import com.easyCourse.model.Student;

public interface IStudentDao {
    Student selectStudentById(String id);
}
