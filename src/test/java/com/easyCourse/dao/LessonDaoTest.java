package com.easyCourse.dao;

import com.easyCourse.model.Lesson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class LessonDaoTest {

    @Autowired
    private LessonDao lessonDao;

    @Test
    public void selectAll() {
        List<Lesson> lessonList = lessonDao.selectAll();
        lessonList.forEach(System.out::println);
    }
}