package com.easyCourse.dao;

import com.easyCourse.model.LessonFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class LessonFileDaoTest {

    @Autowired
    private LessonFileDao lessonFileDao;

    @Test
    public void selectByLessonId() {
        String lessonId = "1";
        List<LessonFile> lessonFileList = lessonFileDao.selectByLessonId(lessonId);

        lessonFileList.forEach(System.out::println);
    }
}