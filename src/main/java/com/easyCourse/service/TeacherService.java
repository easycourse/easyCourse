package com.easyCourse.service;

import com.easyCourse.entity.LessonNotice;
import com.easyCourse.entity.StudentHomework;
import com.easyCourse.entity.Teacher;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 教师服务接口
 * <p>
 * Created by devin
 * 2019-05-06 17:51
 */
public interface TeacherService {

    /**
     * 教师注册
     *
     * @param teacherId   教师Id
     * @param password    密码
     * @param teacherName 教师姓名
     * @param phone       教室电话（手机号或办公室电话）
     * @param mail        教师邮箱
     * @param location    教室办公室地址
     * @return 状态码
     */
    JSONObject register(String teacherId, String password, String teacherName,
                        String phone, String mail, String location);

    /**
     * 教师登录
     *
     * @param teacherId 教师账号
     * @param password  密码
     * @return 教师信息
     */
    JSONObject login(String teacherId, String password);

    /**
     * 根据id查询教师信息
     *
     * @param teacherId 教师id
     * @return 教师信息
     */
    Teacher getTeacherById(String teacherId);

    /**
     * 教师增加选课人数
     *
     * @param records 学号->lessonId 的Map
     * @return 教师信息
     */
    JSONObject importStudent(Map<String,String> records);

    /**
     * 获取某个作业的提交人数
     * @param homeworkId
     * @return
     */
    int getSubmitCountForSingleHomework(String homeworkId);

    /**
     * 获取某个作业的提交情况
     * @param homeworkId
     * @return
     */
    List<StudentHomework> getSubmitHomeworkByHomeworkId(String homeworkId);

    /**
     * 导入学生成绩
     * @param records
     * @param homeworkId
     * @return
     */
    JSONObject importScores(Map<String, Integer> records, String homeworkId);

    /**
     * 导入学生成绩2
     * @param records
     * @param homeworkId
     * @return
     */
    //JSONObject importScoresWithForeach(Map<String, Integer> records, String homeworkId);
}
