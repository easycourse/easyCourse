package com.easyCourse.dao;

import com.easyCourse.entity.LessonNotice;
import com.easyCourse.vo.StudentNoticeVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程通知数据库访问层
 *
 * （注意，每写一个接口都需要做相应的单元测试）
 */
@Repository
public interface LessonNoticeDao {
    /**
     * 获取某一课程的所有通知
     *
     * @return 课程通知列表
     */
    List<LessonNotice> selectByLessonId(String lessonId);

    /**
     * 获取某一课程的所有通知,实现了按添加时间降序排序以及过滤掉删除的通知
     * @param lessonId
     * @return
     */
    List<LessonNotice> findAllNoticeByLessonId(String lessonId);

    /**
     * 获取一个老师发布的所有通知,实现了按添加时间降序排序以及过滤掉删除的通知
     * @param teacherId
     * @return
     */
    List<LessonNotice> findAllNoticeByTeacherId(String teacherId);

    /**
     * 添加一条通知
     * @param lessonNotice
     * @return
     */
    int insertSelective(LessonNotice lessonNotice);

    /**
     * 查询与某个学生相关的所有通知
     * @param studentId
     * @return
     */
    List<StudentNoticeVO> findAllNoticeByStudentId(String studentId);
}
