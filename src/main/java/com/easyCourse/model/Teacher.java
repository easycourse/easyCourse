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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getLocation() {
        return location;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public String getMail() {
        return mail;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getPhone() {
        return phone;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}