package com.easyCourse.dao;

import com.easyCourse.entity.Lesson;
import com.easyCourse.vo.LessonVO;
import com.easyCourse.vo.StudentLessonVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程数据访问层
 */
@Repository
public interface LessonDao {

    /**
     * 根据教师id获取课程视图对象列表
     *
     * @param teacherId 教师id
     * @return 课程视图对象列表
     */
    List<LessonVO> selectByTeacherId(String teacherId);

    /**
     * 根据学生id获取其所选课程列表
     *
     * @param studentId 学生id
     * @return 课程视图对象列表
     */
    List<StudentLessonVO> selectByStudentId(String studentId);

    /**
     * 添加课程
     *
     * @param lessonId     课程id
     * @param lessonName   课程名称
     * @param lessonTime   课程时间
     * @param lessonDetail 课程详情
     * @param teacherId    教师id
     */
    void insert(@Param(("lessonId")) String lessonId, @Param("lessonName") String lessonName, @Param("lessonTime") String lessonTime,
                @Param("lessonDetail") String lessonDetail, @Param("teacherId") String teacherId);


    //根据id搜索得到Lesson课程
    Lesson selectByLessonId(String lessonId);
}
