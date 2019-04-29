package com.easyCourse.dao;

import com.easyCourse.model.LessonNotice;
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
}
