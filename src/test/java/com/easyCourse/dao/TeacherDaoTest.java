package com.easyCourse.dao;

import com.easyCourse.model.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class TeacherDaoTest {

    @Autowired
    private TeacherDao teacherDao;

    @Test
    public void selectByIdAndPassword() {
        String teacherId = "1";
        String password = "123456";

        Teacher teacher = teacherDao.selectByIdAndPassword(teacherId, password);
        System.out.println(teacher);
    }

    @Test
    public void selectByMail() {
        String mail = "799926701@qq.com";
        Teacher teacher = teacherDao.selectByMail(mail);
        System.out.println(teacher);
    }

    @Test
    public void selectById() {
        String id = "1";
        Teacher teacher = teacherDao.selectById(id);
        System.out.println(teacher);
    }

    @Test
    public void insert() {
        String teacherId = "2";
        String password = "1";
        String teacherName = "hhy";
        String phone = "110";
        String mail = "110@110.com";
        String location = "信息学部";

        teacherDao.insert(teacherId, password, teacherName, phone, mail, location);
    }
}