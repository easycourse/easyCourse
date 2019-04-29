package com.easyCourse.model;

import lombok.Data;

import java.util.Date;

@Data
public class LessonNotice {

    private int id;

    private String lessonId;

    private String teacherId;

    private String title;

    private String detail;

    private int noticeType;

    private Date createTime;

    private int isDelete;

    private String appendix;
}
