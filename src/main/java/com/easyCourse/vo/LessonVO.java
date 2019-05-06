package com.easyCourse.vo;

import lombok.Data;

import java.util.Date;

/**
 * 课程类视图对象
 * <p>
 * Created by devin
 * 2019-05-06 19:31
 */
@Data
public class LessonVO {

    /* 以下是课程信息 */
    private String lessonId;

    private String lessonName;

    private String lessonTime;

    private String detail;

    private int studentNum;

    /* 以下是教师信息 */
    private String teacherId;

    private String teacherName;

    private String phone;

    private String mail;

    private String location;
}
