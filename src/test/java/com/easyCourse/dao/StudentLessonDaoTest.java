package com.easyCourse.dao;

import com.easyCourse.model.StudentLesson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Devin
 * 2019-04-29 20:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class StudentLessonDaoTest {

    @Autowired
    private StudentLessonDao studentLessonDao;

    @Test
    public void selectByLessonId() {
        String lessonId = "1";
        List<StudentLesson> studentList = studentLessonDao.selectByLessonId(lessonId);

        studentList.forEach(System.out::println);
    }
}