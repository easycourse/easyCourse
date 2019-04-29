package com.easyCourse.model;

import lombok.Data;

import java.util.Date;

/**
 * 课程实体类
 */
@Data
public class Lesson {

    private String lessonId;

    private String lessonName;

    private String teacherId;

    private String lessonTime;

    private String detail;

    private Date createTime;

    private Date updateTime;

    private int isDelete;

}