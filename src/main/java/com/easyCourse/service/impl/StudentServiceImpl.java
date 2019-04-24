package com.easyCourse.service.impl;

import com.easyCourse.dao.IStudentDao;
import com.easyCourse.model.Student;
import com.easyCourse.service.IStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("studentService")

public class StudentServiceImpl implements IStudentService {
    @Resource
    private IStudentDao studentDao;

    @Override
    public Student selectStudent(String userId) {
        return this.studentDao.selectStudentById(userId);
    }

}
