package com.easyCourse.dao;

import com.easyCourse.model.LessonHomework;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程作业（老师上传）数据访问层
 *
 * （注意，每写一个接口都需要做相应的单元测试）
 */
@Repository
public interface LessonHomeworkDao {
    /**
     * 获取教师id获取其上传的课程作业列表
     *（这个方法不根据is_delete筛选，如果需要请修改sql语句或重写方法）
     *
     * @return 指定课程作业列表
     */
    List<LessonHomework> selectByTeacherId(String teacherId);
}
