package com.easyCourse.dao;


import com.easyCourse.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

// 加载spring配置文件
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class IStudentDaoTest {
    @Resource
    private IStudentDao dao;

    @Test
    public void testSelectUser() throws Exception {
        String id = "1";
        Student student = dao.selectStudentById(id);
        System.out.println(student.getStudent_name());
    }
}
