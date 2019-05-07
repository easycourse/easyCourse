package com.easyCourse.service.impl;

import com.easyCourse.dao.IStudentDao;
import com.easyCourse.entity.Student;
import com.easyCourse.service.IStudentService;
import com.easyCourse.utils.StatusCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("studentService")

public class StudentServiceImpl implements IStudentService {
    @Resource
    private IStudentDao studentDao;

    @Override
    public Student loginVerify(String studentId, String passwd) {
        return this.studentDao.verify(studentId,passwd);
    }

    @Override
    public int registerVerify(String studentId, String passwd, String email, String studentName) {
        if(this.studentDao.selectStudentById(studentId) !=null) return StatusCode.DUPLICATED_STUDENTID;
        if(this.studentDao.selectStudentByEmail(email) !=null) return StatusCode.DUPLICATED_EMAIL;
        this.studentDao.insert(studentId,passwd,email,studentName);
        return StatusCode.REGISTER_SUCCESS;
    }
}
