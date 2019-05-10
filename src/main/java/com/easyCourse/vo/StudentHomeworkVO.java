package com.easyCourse.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by Devin
 * 2019-05-10 16:29
 */
@Data
public class StudentHomeworkVO {
    private int homeworkId;

    private String title;

    private String detail;

    private String appendix;

    private Date createTime;

    private Date dueTime;

    private String lessonId;

    private String lessonName;

    private Integer submitId;
}
