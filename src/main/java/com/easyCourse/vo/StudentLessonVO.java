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

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(String lessonTime) {
        this.lessonTime = lessonTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
