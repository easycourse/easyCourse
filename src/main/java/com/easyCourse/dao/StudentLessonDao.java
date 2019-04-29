package com.easyCourse.dao;

import com.easyCourse.model.Student;
import com.easyCourse.model.StudentLesson;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学生选课情况数据访问层
 *
 * （注意，每写一个接口都需要做相应的单元测试）
 */
@Repository
public interface StudentLessonDao {

    /**
     * 选择选了该课程的所有学生
     *
     * @return
     */
    List<StudentLesson> selectByLessonId(String lessonId);
}
