package com.easyCourse.dao;


import com.easyCourse.model.Student;
import com.easyCourse.service.IStudentService;
import com.easyCourse.utils.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
        Student student = this.iStudentService.verify("2016302580313", StringUtils.MD5("swx6868752"));
        System.out.println(student.getStudent_name());
    }
}
