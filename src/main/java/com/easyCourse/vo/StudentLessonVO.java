package com.easyCourse.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by Devin
 * 2019-05-10 16:05
 */
@Data
public class StudentLessonVO {
    /* 以下是课程信息 */
    private String lessonId;

    private String lessonName;

    private String lessonTime;

    private String detail;

    /* 以下是教师信息 */

    private String teacherName;

    private String phone;

    private String mail;

    private String location;
}
