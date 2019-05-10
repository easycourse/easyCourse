package com.easyCourse.dao;

import com.easyCourse.entity.LessonHomework;
import com.easyCourse.vo.StudentHomeworkVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class LessonHomeworkDaoTest {

    @Autowired
    private LessonHomeworkDao lessonHomeworkDao;

    @Test
    public void selectByTeacherId() {
        String teacherId = "1";
        List<LessonHomework> lessonHomeworkList = lessonHomeworkDao.selectByTeacherId(teacherId);

        lessonHomeworkList.forEach(System.out::println);
    }

    @Test
    public void selectByStudentId() {
        String studentId = "2016302580297";
        List<StudentHomeworkVO> studentHomeworkVOList = lessonHomeworkDao.selectByStudentId(studentId);

        studentHomeworkVOList.forEach(System.out::println);

    }
}