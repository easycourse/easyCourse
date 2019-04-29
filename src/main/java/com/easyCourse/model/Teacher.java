package com.easyCourse.model;

import java.util.Date;
import lombok.Data;

/**
 * 教师实体类
 */
@Data
public class Teacher {

    private String teacherId;

    private String passwd;

    private String mail;

    private String teacherName;

    private String phone;

    private String location;

    private Date createTime;

    private Date updateTime;

    private int isDelete;
}