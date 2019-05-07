package com.easyCourse.dao;

import com.easyCourse.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 教师数据访问层
 * <p>
 * （注意，每写一个接口都需要做相应的单元测试）
 */
@Repository
public interface TeacherDao {

    /**
     * 根据账号和密码获取教师信息
     *
     * @param teacherId 账号
     * @param password  密码
     * @return 教师信息
     */
    Teacher selectByIdAndPassword(@Param("teacherId") String teacherId, @Param("password") String password);

    /**
     * 根据邮箱查询教师信息
     *
     * @param mail 邮箱
     * @return 教师信息
     */
    Teacher selectByMail(String mail);

    /**
     * 根据id查询教师信息
     *
     * @param teacherId 教师Id
     * @return 教师信息
     */
    Teacher selectById(String teacherId);

    /**
     * 插入一条教师记录
     *
     * @param teacherId   教师Id
     * @param password    密码
     * @param teacherName 教师姓名
     * @param phone       电话
     * @param mail        邮箱
     * @param location    办公室地址
     */
    void insert(@Param("teacherId") String teacherId, @Param("password") String password, @Param("teacherName") String teacherName,
                @Param("phone") String phone, @Param("mail") String mail, @Param("location") String location);
}
