package com.easyCourse.service;

import com.easyCourse.model.Student;

public interface IStudentService {
    public Student selectStudent(String userId);

    public Student verify(String studentId,String passwd);
}
