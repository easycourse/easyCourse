package com.easyCourse.dao;

import com.easyCourse.entity.LessonNotice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Devin
 * 2019-04-29 20:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class LessonNoticeDaoTest {

    @Autowired
    private LessonNoticeDao lessonNoticeDao;

    @Test
    public void selectByLessonId() {
        String lessonId = "1";
        List<LessonNotice> lessonNoticeList = lessonNoticeDao.selectByLessonId(lessonId);

        lessonNoticeList.forEach(System.out::println);

    }
}