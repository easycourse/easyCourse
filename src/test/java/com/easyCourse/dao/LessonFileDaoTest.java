package com.easyCourse.dao;

import com.easyCourse.entity.LessonFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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

    @Test
    public void selectByTeacherId() {
        String teacherId = "1";
        List<LessonFile> lessonFileList = lessonFileDao.selectByTeacherId(teacherId);

        lessonFileList.forEach(System.out::println);
    }

    @Test
    public void insertSelective() {
        LessonFile lessonFile = new LessonFile();
        lessonFile.setLessonId("2");
        lessonFile.setUserId("1");
        lessonFile.setTitle("kkkkk");
        lessonFile.setDetail("hhhh");
        lessonFile.setAppendix("xxx.com");
        lessonFile.setIsDelete(0);

        int i = lessonFileDao.insertSelective(lessonFile);
    }
}