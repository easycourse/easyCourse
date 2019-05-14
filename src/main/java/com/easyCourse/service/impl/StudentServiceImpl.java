package com.easyCourse.service.impl;

import com.easyCourse.dao.*;
import com.easyCourse.entity.*;
import com.easyCourse.service.IStudentService;
import com.easyCourse.utils.StatusCode;
import com.easyCourse.vo.LessonVO;
import com.easyCourse.vo.StudentHomeworkVO;
import com.easyCourse.vo.StudentLessonVO;
import com.easyCourse.vo.StudentNoticeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    private StudentHomeworkDao studentHomeworkDao;

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

    @Override
    public Student getStudentByStudentId(String id) {
        return studentDao.selectStudentById(id);
    }

    @Override
    public List<LessonHomework> getHomeworkByLessonId(String lessonId) {
        return lessonHomeworkDao.selectByLessonId(lessonId);
    }

    @Override
    public List<LessonNotice> getLessonNoticesByLessonId(String lessonId) {
        return lessonNoticeDao.selectByLessonId(lessonId);
    }

    @Override
    public LessonVO getLessonInfoByLessonId(String lessonId) {
        return lessonDao.selectLessonVOByLessonId(lessonId);
    }

    @Override
    public int commitHomework(String studentId, String homeworkId, String homeworkName, String appendix) {
        StudentHomeworkVO studentHomework = studentHomeworkDao.selectVOByHomeworkIdAndStudentId(homeworkId, studentId);
        if (studentHomework == null) {
            int num = studentHomeworkDao.insert(studentId, homeworkId, homeworkName, appendix);
            if (num == 1) {
                return 200;
            }
            return 500; // 插入记录失败
        }

        if (studentHomework.getDueTime().before(new Date())) {
            return 301; // 过截止时间
        }

        int num = studentHomeworkDao.updateAppendix(studentId, homeworkId, appendix);
        if (num == 1) {
            return 200;
        } else {
            return 500; // 更新失败
        }
    }

    @Override
    public int getHomeworkScore(String studentId, String homeworkId) {
        StudentHomework studentHomework = studentHomeworkDao.selectByHomeworkIdAndStudentId(homeworkId, studentId);
        if (studentHomework == null) {
            return 301; // 未提交作业
        }

        if (studentHomework.getScore() == -1) {
            return 302; // 老师尚未给分
        }

        return studentHomework.getScore(); // 分数
    }

    @Override
    public StudentHomeworkVO getHomeworkByHomeworkIdAndStudentId(String homeworkId, String studentId) {
        return studentHomeworkDao.selectVOByHomeworkIdAndStudentId(homeworkId, studentId);
    }
}
