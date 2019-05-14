package com.easyCourse.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.easyCourse.entity.Lesson;
import com.easyCourse.entity.LessonFile;
import com.easyCourse.entity.LessonHomework;
import com.easyCourse.entity.LessonNotice;
import com.easyCourse.vo.LessonVO;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 课程服务接口
 * <p>
 * Created by devin
 * 2019-05-06 19:50
 */
public interface LessonService {

    /**
     * 根据教师id获取其课程信息
     *
     * @param teacherId 教师id
     * @return 课程信息
     */
    JSONObject getByTeacherId(String teacherId);

    /**
     * 教师添加课程
     *
     * @param lessonName   课程名
     * @param lessonTime   课程时间
     * @param lessonDetail 课程详情
     * @param teacherId    教师id
     * @return 添加结果
     */
    JSONObject addLesson(String lessonName, String lessonTime, String lessonDetail, String teacherId);

    /**
     * 通过lessonId获取某个课程的全部通知
     * @param lessonId
     * @return
     */
    List<LessonNotice> getNoticeListByLessonId(String lessonId);

    /**
     * 通过teacherId获取某个老师发布的全部通知
     * @param teacherId
     * @return
     */
    List<LessonNotice> getNoticeListByTeacherId(String teacherId);

    /**
     * 添加通知
     * @param lessonIdList
     * @param teacherId
     * @param title
     * @param noticeType
     * @param detail
     * @param appendix
     * @return
     */
    JSONObject addNotice(JSONArray lessonIdList, String teacherId, String title, int noticeType, String detail, String appendix);

    /**
     * 根据教师id获取上传的课件
     * @param teacherId
     * @return
     */
    List<LessonFile> getLessonFileListByTeacherId(String teacherId);


    /**
     * 添加课程文件
     * @param lessonFileList
     * @return
     */
    JSONObject addLessonFile(List<LessonFile> lessonFileList);

    /**
     * 获取课程列表
     * @param teacherId
     * @return
     */
    List<LessonVO> getLessonListByTeacherId(String teacherId);

    List<LessonHomework> getLessonHWListBylessonId(String lessonId);

    JSONObject addNewHomework(LessonHomework lessonHomework);

    JSONObject addNewHomeworkList(List<LessonHomework> lessonHomeworkList);

}
