package com.easyCourse.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by Devin
 * 2019-04-29 20:14
 */
@Data
public class StudentHomework {

    private int id;

    private int studentId;

    private int homeworkId;

    private String homeworkName;

    private String appendix;

    private int score;

    private Date createTime;

    private Date updateTime;

    private int isDelete;
}
