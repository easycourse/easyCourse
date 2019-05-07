package com.easyCourse.dao;


import com.easyCourse.entity.Student;
import com.easyCourse.service.IStudentService;
import com.easyCourse.utils.StringUtils;
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

    @Resource
    private IStudentService iStudentService;

    @Test
    public void testSelectUser() throws Exception {
        Student student = this.iStudentService.loginVerify("2016302580313", StringUtils.MD5("swx6868752"));
        System.out.println(student.getStudent_name());
    }
}
