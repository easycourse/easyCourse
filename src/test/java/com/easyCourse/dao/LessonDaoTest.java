package com.easyCourse.dao;

import com.alibaba.fastjson.JSONObject;
import com.easyCourse.entity.Lesson;
import com.easyCourse.utils.StatusCode;
import com.easyCourse.vo.LessonVO;
import com.easyCourse.vo.StudentLessonVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.SecureRandom;
import java.util.*;

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
    public void selectByStudentId() {
        String studentId = "2016302580297";
        List<StudentLessonVO> studentLessonVOList = lessonDao.selectByStudentId(studentId);

        studentLessonVOList.forEach(System.out::println);
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

    @Test
    public void updateByPrimaryKeySelective(){
//        Lesson lesson = lessonDao.selectByLessonId("2019-1557732751");
//        lesson.setStudentNum(lesson.getStudentNum()+20);
//        lesson.setUpdateTime(new Date());
//        int result = lessonDao.updateByPrimaryKeySelective(lesson);

        List<String> studentIdList = new ArrayList<>();
        studentIdList.add("123");
        studentIdList.add("456");
        studentIdList.add("789");
        studentIdList.add("111");

        String lessonId = "2019-1557732751";
        Map<String,String> records = new HashMap<>();
        for(int i=0;i<studentIdList.size();i++){
            records.put(studentIdList.get(i), lessonId);
        }

        Iterator<String> iter = records.keySet().iterator();
        String tempKey =iter.next();
        String lessonIdX = records.get(tempKey);
        Lesson lesson = lessonDao.selectByLessonId(lessonIdX);

        lesson.setStudentNum(lesson.getStudentNum() + records.size());
        lesson.setUpdateTime(new Date());

        int result = lessonDao.updateByPrimaryKeySelective(lesson);

        if(result!=1){
            System.out.println("xxxx");
        } else {
            System.out.println("vvvvv");
        }
    }
}