package com.easyCourse.dao;

import com.easyCourse.entity.StudentHomework;
import com.easyCourse.vo.StudentHomeworkVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学生提交作业数据访问层
 * <p>
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

    /**
     * 根据作业Id和学生id获取作业信息
     *
     * @param homeworkId 作业id
     * @param studentId  学生id
     * @return
     */
    StudentHomework selectByHomeworkIdAndStudentId(@Param("homeworkId") String homeworkId, @Param("studentId") String studentId);

    /**
     * 根据作业id和学生id获取作业详情
     *
     * @param homeworkId 作业id
     * @param studentId  学生id
     * @return 作业信息
     */
    StudentHomeworkVO selectVOByHomeworkIdAndStudentId(@Param("homeworkId") String homeworkId, @Param("studentId") String studentId);

    /**
     * 插入学生作业
     *
     * @param studentId    学生id
     * @param homeworkId   作业id
     * @param homeworkName 作业名称
     * @param appendix     附录
     * @return 插入记录数
     */
    int insert(@Param("studentId") String studentId, @Param("homeworkId") String homeworkId,
               @Param("homeworkName") String homeworkName, @Param("appendix") String appendix);

    /**
     * 学生二次提交作业，更新信息
     *
     * @param studentId  学生id
     * @param homeworkId 作业id
     * @param appendix   作业url
     * @return 更新条数
     */
    int updateAppendix(@Param("studentId") String studentId, @Param("homeworkId") String homeworkId, @Param("appendix") String appendix);
}
