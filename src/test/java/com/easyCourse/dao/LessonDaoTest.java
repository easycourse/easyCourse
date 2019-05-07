package com.easyCourse.dao;

import com.easyCourse.vo.LessonVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class LessonDaoTest {

    @Autowired
    private LessonDao lessonDao;

    @Test
    public void selectByTeacherId() {
        String teacherId = "1";

        List<LessonVO> lessonVOList = lessonDao.selectByTeacherId(teacherId);
        lessonVOList.forEach(System.out::println);
    }

    @Test
    public void insert() {
        int year = new GregorianCalendar().get(Calendar.YEAR);
        long time = System.currentTimeMillis() / 1000;
        String lessonId = year + "-" + time;
        String lessonName = "hello world";
        String lessonTime = "每周二下午6-8节";
        String lessonDetail = null;
        String teacherId = "1";

        lessonDao.insert(lessonId, lessonName, lessonTime, lessonDetail, teacherId);
    }
}