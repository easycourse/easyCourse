package com.easyCourse.service.impl;

import com.easyCourse.dao.TeacherDao;
import com.easyCourse.entity.Teacher;
import com.easyCourse.service.TeacherService;
import com.easyCourse.utils.Jwt;
import com.easyCourse.utils.StatusCode;
import com.easyCourse.utils.StringUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 教师服务实现类
 * <p>
 * Created by devin
 * 2019-05-06 17:56
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public JSONObject register(String teacherId, String password, String teacherName,
                               String phone, String mail, String location) {

        JSONObject result = new JSONObject();

        // 1.根据教师id进行判断
        if (teacherDao.selectById(teacherId) != null) {
            result.put("status", StatusCode.DUPLICATED_TEACHERID);
            result.put("msg", "该账号已存在");
            result.put("data", null);
            return result;
        }
        // 2.根据教师mail进行判断
        if (teacherDao.selectByMail(mail) != null) {
            result.put("status", StatusCode.DUPLICATED_EMAIL);
            result.put("msg", "该邮箱已注册");
            result.put("data", null);
            return result;
        }

        // 3.插入数据库，返回成功
        teacherDao.insert(teacherId, StringUtils.MD5(password), teacherName, phone, mail, location);
        Map<String, Object> payload = new HashMap<>();
        Date date = new Date();
        payload.put("uid", teacherId); // 用户ID
        payload.put("iat", date.getTime()); // 生成时间
        payload.put("ext", date.getTime() + 1000 * 60 * 60); // 过期时间1小时
        String token = Jwt.createToken(payload);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        result.put("status", StatusCode.SUCCESS);
        result.put("msg", "注册成功");
        result.put("data", data);

        return result;
    }

    @Override
    public JSONObject login(String teacherId, String password) {
        JSONObject result = new JSONObject();

        Teacher teacher = teacherDao.selectByIdAndPassword(teacherId, StringUtils.MD5(password));
        if (teacher == null) {
            result.put("status", StatusCode.INCORRECT_PASSWORD);
            result.put("msg", "账号和密码错误");
            result.put("data", null);
            return result;
        }

        Map<String, Object> payload = new HashMap<>();
        Date date = new Date();
        payload.put("uid", teacherId); // 用户ID
        payload.put("iat", date.getTime()); // 生成时间
        payload.put("ext", date.getTime() + 1000 * 60 * 60); // 过期时间1小时
        String token = Jwt.createToken(payload);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        result.put("status", StatusCode.SUCCESS);
        result.put("msg", "教师登录成功");
        result.put("data", data);

        return result;
    }

    @Override
    public Teacher getTeacherById(String teacherId) {
        return teacherDao.selectById(teacherId);
    }
}
