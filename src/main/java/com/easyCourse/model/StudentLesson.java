package com.easyCourse.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by Devin
 * 2019-04-29 20:28
 */
@Data
public class StudentLesson {

    private int id;

    private String studentId;

    private String lessonId;

    private Date createTime;

    private Date updateTime;

    private int isDelete;
}
