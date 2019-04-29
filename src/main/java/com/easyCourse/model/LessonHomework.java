package com.easyCourse.model;

import lombok.Data;

import java.util.Date;

/**
 * 课程作业（老师上传）实体类
 */
@Data
public class LessonHomework {

    private int homeworkId;

    private String lessonId;

    private String teacherId;

    private String title;

    private String detail;

    private String appendix;

    private Date createTime;

    private Date dueTime;

    private int isDelete;
}
