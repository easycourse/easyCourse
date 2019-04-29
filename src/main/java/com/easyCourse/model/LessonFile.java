package com.easyCourse.model;

import lombok.Data;

import java.util.Date;
/**
 * 课程文件实体类
 */
@Data
public class LessonFile {

    private int id;

    private String lessonId;

    private String userId;

    private String title;

    private String detail;

    private String appendix;

    private Date createTime;

    private int isDelete;
}
