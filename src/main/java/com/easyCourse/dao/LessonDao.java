package com.easyCourse.dao;

import com.easyCourse.model.Lesson;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程数据访问层
 *
 * （注意，每写一个接口都需要做相应的单元测试）
 */
@Repository
public interface LessonDao {

    /**
     * 获取所有课程列表
     * （这个方法不根据is_delete筛选，如果需要请修改sql语句或重写方法）
     *
     * @return 课程列表
     */
    List<Lesson> selectAll();
}
