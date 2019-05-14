package com.easyCourse.dao;

import com.easyCourse.entity.LessonNotice;
import com.easyCourse.vo.StudentNoticeVO;
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

    @Test
    public void findNoticeByLessonId() {
        String lessonId = "2";
        List<LessonNotice> lessonNoticeList = lessonNoticeDao.findAllNoticeByLessonId(lessonId);

        System.out.println("xxx");
        lessonNoticeList.forEach(System.out::println);
    }

    @Test
    public void findNoticeByTeacherId() {
        String teacherId = "1";
        List<LessonNotice> lessonNoticeList = lessonNoticeDao.findAllNoticeByTeacherId(teacherId);

        System.out.println("xxx");
        lessonNoticeList.forEach(System.out::println);
    }

    @Test
    public void findNoticeByStudentId() {
        String studentId = "2016302580297";
        List<StudentNoticeVO> studentNoticeList = lessonNoticeDao.findAllNoticeByStudentId(studentId);

        System.out.println("xxx");
        studentNoticeList.forEach(System.out::println);
    }

    @Test
    public void insertSelective() {
        LessonNotice lessonNotice = new LessonNotice();
        lessonNotice.setTitle("hhhh");
        lessonNotice.setLessonId("1");
        lessonNotice.setTeacherId("1");
        lessonNotice.setDetail("xxxxx");
        lessonNotice.setNoticeType(0);
        lessonNotice.setIsDelete(0);
        int i = lessonNoticeDao.insertSelective(lessonNotice);
        System.out.println(i);

    }

    @Test
    public void findAllNoticeByStudentId() {
        List<StudentNoticeVO> list = lessonNoticeDao.findAllNoticeByStudentId("2016302580297");
        list.forEach(System.out::println);
    }
}