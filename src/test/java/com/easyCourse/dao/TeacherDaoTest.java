package com.easyCourse.dao;

import com.easyCourse.model.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class TeacherDaoTest {

    @Autowired
    private TeacherDao teacherDao;

    @Test
    public void selectAll() {
        List<Teacher> teacherList = teacherDao.selectAll();
        teacherList.forEach(System.out::println);
    }
}