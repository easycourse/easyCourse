package com.easyCourse.service;

import com.easyCourse.entity.LessonFile;
import com.easyCourse.entity.LessonHomework;
import com.easyCourse.entity.LessonNotice;
import com.easyCourse.entity.Student;
import com.easyCourse.vo.LessonVO;
import com.easyCourse.vo.StudentHomeworkVO;
import com.easyCourse.vo.StudentLessonVO;
import com.easyCourse.vo.StudentNoticeVO;

import java.util.List;

public interface IStudentService {
    //学生登录验证
    Student loginVerify(String studentId,String passwd);

    //学生注册验证
    int registerVerify(String studentId,String passwd,String email,String studentName);

    //获取指定学生的所有通知
    List<StudentNoticeVO> getAllNoticeByStudentId(String studentId);

    // 获取指定学生的所有课程
    List<StudentLessonVO> getAllLessonsByStudentId(String studentId);

    // 获取指定学生未完成的作业列表
    List<StudentHomeworkVO> getAllHomeworkByStudentId(String studentId);

    // 获取某一课程的所有课件
    List<LessonFile> getAllLessonFilesByLessonId(String lessonId);

    //根据学生id获取学生
    Student getStudentByStudentId(String id);

    // 根据课程id获取作业列表
    List<LessonHomework> getHomeworkByLessonId(String lessonId);

    // 根据课程id获取通知列表
    List<LessonNotice> getLessonNoticesByLessonId(String lessonId);

    LessonVO getLessonInfoByLessonId(String lessonId);

    int commitHomework(String studentId, String homeworkId, String homeworkName, String appendix);

    int getHomeworkScore(String studentId, String homeworkId);

    List<String> getHomeworkByHomeworkId(String homeworkId);
}
