package com.easyCourse.dao;

import com.easyCourse.entity.LessonFile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程文件数据访问层
 *
 * （注意，每写一个接口都需要做相应的单元测试）
 */
@Repository
public interface LessonFileDao {

    /**
     * 获取课程id获取课程文件列表
     * （这个方法不根据is_delete筛选，如果需要请修改sql语句或重写方法）
     *
     * @return 指定课程文件列表
     */
    List<LessonFile> selectByLessonId(String lessonId);


    /**
     * 根据教师id获取课程文件列表
     * @param teacherId
     * @return
     */
    List<LessonFile> selectByTeacherId(String teacherId);

    /**
     * 插入一条课程文件记录,可以有空属性
     * @param lessonFile
     * @return
     */
    int insertSelective(LessonFile lessonFile);
}
