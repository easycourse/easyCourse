package com.easyCourse.dao;

import com.easyCourse.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教师数据访问层
 *
 * （注意，每写一个接口都需要做相应的单元测试）
 */
@Repository
public interface TeacherDao {
    /**
     * 获取教师列表（这个方法不根据is_delete筛选，如果需要请修改sql语句或重写方法）
     *
     * @return 教师列表
     */
    List<Teacher> selectAll();
}
