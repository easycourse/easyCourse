package com.easyCourse.dao;

import com.easyCourse.entity.StudentHomework;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学生提交作业数据访问层
 *
 * （注意，每写一个接口都需要做相应的单元测试）
 */
@Repository
public interface StudentHomeworkDao {

    /**
     * 根据作业Id选取所有提交了作业的学生的提交情况
     *
     * @return
     */
    List<StudentHomework> selectByHomeworkId(String homeworkId);

    int updateStudentHomeworkScore(@Param("studentId") String studentId, @Param("homeworkId") String homeworkId, @Param("score") int score);
}
