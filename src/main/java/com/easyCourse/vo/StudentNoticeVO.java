package com.easyCourse.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by Devin
 * 2019-05-10 15:26
 */
@Data
public class StudentNoticeVO {

    private String id;

    private String lessonId;

    private String teacherId;

    private String teacherName;

    private String title;

    private String detail;

    private int noticeType;

    private Date createTime;

    private String appendix;
}
