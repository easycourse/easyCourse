package com.easyCourse.dao;

import com.easyCourse.entity.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;


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
    public void insertComplete() {

        Teacher teacher = new Teacher();
        teacher.setTeacherId("000004");
        teacher.setPasswd("xxxxxxx");
        teacher.setMail("hhh@qq.com");
        teacher.setTeacherName("hhh");
        teacher.setPhone("12345678900");
        teacher.setLocation("计算机学院");
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        teacher.setCreateTime(timeStamp);
        teacher.setUpdateTime(timeStamp);
        teacher.setIsDelete(0);

        teacherDao.insertComplete(teacher);
    }

    @Test
    public void insertSelective() {

        Teacher teacher = new Teacher();
        teacher.setTeacherId("000005");
        teacher.setPasswd("xxxxxxx");
        teacher.setMail("hhh@qq.com");
        teacher.setTeacherName("hhh");
        teacher.setPhone("12345678900");
        teacher.setLocation("计算机学院");
        teacher.setIsDelete(0);

        teacherDao.insertSelective(teacher);
    }

    @Test
    public void updateByPrimaryKeyComplete() {

        Teacher teacher = teacherDao.selectById("000005");


        teacher.setPasswd("yyyyyy");
        teacher.setMail("hhh@qq.com");
        teacher.setTeacherName("hhh");
        teacher.setPhone("12345678900");
        teacher.setLocation("计算机学院");
        teacher.setIsDelete(0);

        teacherDao.updateByPrimaryKeyComplete(teacher);
    }

    @Test
    public void updateByPrimaryKeySelective() {

        Teacher teacher = new Teacher();
        teacher.setTeacherId("000005");
        teacher.setPasswd("xxxxxxx");
        teacher.setMail("");
        teacher.setTeacherName("hhh");
        teacher.setPhone("12345678900");
        teacher.setLocation("计算机学院");
        teacher.setIsDelete(0);

        teacherDao.updateByPrimaryKeySelective(teacher);
    }

    @Test
    public void deleteByPrimaryKey() {

        int i = teacherDao.deleteByPrimaryKey("000004");

        System.out.println(i);
    }
}