package com.easyCourse.service.impl;

import com.easyCourse.dao.*;
import com.easyCourse.entity.LessonFile;
import com.easyCourse.entity.LessonHomework;
import com.easyCourse.entity.Student;
import com.easyCourse.service.IStudentService;
import com.easyCourse.utils.StatusCode;
import com.easyCourse.vo.StudentHomeworkVO;
import com.easyCourse.vo.StudentLessonVO;
import com.easyCourse.vo.StudentNoticeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("studentService")

public class StudentServiceImpl implements IStudentService {
    @Resource
    private IStudentDao studentDao;

    @Resource
    private LessonNoticeDao lessonNoticeDao;

    @Resource
    private LessonDao lessonDao;

    @Resource
    private LessonHomeworkDao lessonHomeworkDao;

    @Resource
    private LessonFileDao lessonFileDao;

    @Override
    public Student loginVerify(String studentId, String passwd) {
        return this.studentDao.verify(studentId, passwd);
    }

    @Override
    public int registerVerify(String studentId, String passwd, String email, String studentName) {
        if (this.studentDao.selectStudentById(studentId) != null) return StatusCode.DUPLICATED_STUDENTID;
        if (this.studentDao.selectStudentByEmail(email) != null) return StatusCode.DUPLICATED_EMAIL;
        this.studentDao.insert(studentId, passwd, email, studentName);
        return StatusCode.SUCCESS;
    }

    @Override
    public List<StudentNoticeVO> getAllNoticeByStudentId(String studentId) {
        return lessonNoticeDao.findAllNoticeByStudentId(studentId);
    }

    @Override
    public List<StudentLessonVO> getAllLessonsByStudentId(String studentId) {
        return lessonDao.selectByStudentId(studentId);
    }

    @Override
    public List<StudentHomeworkVO> getAllHomeworkByStudentId(String studentId) {
        return lessonHomeworkDao.selectByStudentId(studentId);
    }

    @Override
    public List<LessonFile> getAllLessonFilesByLessonId(String lessonId) {
        return lessonFileDao.selectByLessonId(lessonId);
    }
}
