package com.easyCourse.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.easyCourse.dao.*;
import com.easyCourse.entity.*;
import com.easyCourse.service.LessonService;
import com.easyCourse.utils.StatusCode;
import com.easyCourse.vo.LessonVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 课程服务实现类
 * <p>
 * Created by devin
 * 2019-05-06 19:54
 */
@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonDao lessonDao;
    @Autowired
    private LessonNoticeDao lessonNoticeDao;
    @Autowired
    private LessonFileDao lessonFileDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private LessonHomeworkDao lessonHomeworkDao;

    @Override
    public JSONObject getByTeacherId(String teacherId) {
        JSONObject result = new JSONObject();
        List<LessonVO> lessonList = lessonDao.selectByTeacherId(teacherId);

        result.put("status", StatusCode.SUCCESS);
        result.put("msg", "获取课程列表成功");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("lessonList", lessonList);
        result.put("data", dataMap);

        return result;
    }

    @Override
    public JSONObject addLesson(String lessonName, String lessonTime, String lessonDetail, String teacherId) {
        int year = new GregorianCalendar().get(Calendar.YEAR);
        long time = System.currentTimeMillis() / 1000;
        String lessonId = year + "-" + time;

        lessonDao.insert(lessonId, lessonName, lessonTime, lessonDetail, teacherId);
        JSONObject result = new JSONObject();
        result.put("status", StatusCode.SUCCESS);
        result.put("msg", "添加课程成功");
        result.put("data", null);
        return result;
    }

    @Override
    public List<LessonNotice> getNoticeListByLessonId(String lessonId){

        List<LessonNotice> lessonNoticeList = lessonNoticeDao.findAllNoticeByLessonId(lessonId);

        return lessonNoticeList;

    }

    @Override
    public List<LessonNotice> getNoticeListByTeacherId(String teacherId){

        List<LessonNotice> lessonNoticeList = lessonNoticeDao.findAllNoticeByTeacherId(teacherId);

        return lessonNoticeList;

    }

    @Override
    public JSONObject addNotice(JSONArray lessonIdList, String teacherId, String title, int noticeType, String detail, String appendix){
        int count = 0;
        for(int i = 0; i < lessonIdList.size(); i++){
            LessonNotice lessonNotice = new LessonNotice();
            lessonNotice.setLessonId(lessonIdList.getString(i));
            lessonNotice.setTeacherId(teacherId);
            lessonNotice.setTitle(title);
            lessonNotice.setDetail(detail);
            lessonNotice.setNoticeType(noticeType);
            lessonNotice.setIsDelete(0);
            lessonNotice.setAppendix(appendix);
            count += lessonNoticeDao.insertSelective(lessonNotice);
        }

        JSONObject result = new JSONObject();
        result.put("data", null);

        if(count != lessonIdList.size()) {
            result.put("status", StatusCode.INSERT_NOTICE_ERROR);
            result.put("msg", "添加通知失败");

        } else {
            result.put("status", StatusCode.SUCCESS);
            result.put("msg", "添加通知成功");
        }

        return result;

    }

    @Override
    public List<LessonFile> getLessonFileListByTeacherId(String teacherId){
        List<LessonFile> lessonFileList = lessonFileDao.selectByTeacherId(teacherId);

        for(LessonFile lessonFile:lessonFileList){
            lessonFile.setLessonName(lessonDao.selectByLessonId(lessonFile.getLessonId()).getLessonName());
            lessonFile.setUserName(teacherDao.selectById(lessonFile.getUserId()).getTeacherName());
        }
        return lessonFileList;
    }


    @Override
    public JSONObject addLessonFile(List<LessonFile> lessonFileList){
        int count = 0;
        for(int i= 0; i < lessonFileList.size(); i++){
            count += lessonFileDao.insertSelective(lessonFileList.get(i));
        }

        JSONObject result = new JSONObject();
        result.put("data", null);

        if(count != lessonFileList.size()) {
            result.put("status", StatusCode.INSERT_COURSEWARE_ERROR);
            result.put("msg", "添加课件失败");

        } else {
            result.put("status", StatusCode.SUCCESS);
            result.put("msg", "添加课件成功");
        }

        return result;
    }

    @Override
    public List<LessonVO> getLessonListByTeacherId(String teacherId){
        return lessonDao.selectByTeacherId(teacherId);
    }

    @Override
    public List<LessonHomework> getLessonHWListBylessonId(String lessonId){
        return lessonHomeworkDao.selectByLessonId(lessonId);
    }

    @Override
    public JSONObject addNewHomework(LessonHomework lessonHomework){
        JSONObject result = new JSONObject();
        int i = lessonHomeworkDao.insertSelective(lessonHomework);
        if(i!=1){
            result.put("status",StatusCode.INSERT_LESSON_HOMEWORK_ERROR);
            result.put("msg","添加作业失败");
        } else {
            result.put("status", StatusCode.SUCCESS);
            result.put("msg","添加作业成功");
        }
        return result;
    }

    @Override
    public JSONObject addNewHomeworkList(List<LessonHomework> lessonHomeworkList){
        JSONObject result = new JSONObject();
        int count = 0;
        for(int i = 0; i < lessonHomeworkList.size(); i++){
            count += lessonHomeworkDao.insertSelective(lessonHomeworkList.get(i));
        }

        if(count != lessonHomeworkList.size()) {
            result.put("status", StatusCode.INSERT_COURSEWARE_ERROR);
            result.put("msg", "添加作业失败");

        } else {
            result.put("status", StatusCode.SUCCESS);
            result.put("msg", "添加作业成功");
        }

        return result;

    }

    @Override
    public JSONObject deleteLessonByLessonId(String lessonId){
        JSONObject result = new JSONObject();
        int i = lessonDao.deleteByPrimaryKey(lessonId);
        if(i!=1){
            result.put("status", StatusCode.DELETE_LESSON_ERROR);
            result.put("msg", "删除课程失败");
        } else {
            result.put("status", StatusCode.SUCCESS);
            result.put("msg", "删除课程成功");
        }

        return result;
    }

    @Override
    public JSONObject deleteLessonFileById(int id){
        JSONObject result = new JSONObject();
        int i = lessonFileDao.deleteByPrimaryKey(id);
        if(i!=1){
            result.put("status", StatusCode.DELETE_LESSON_FILE_ERROR);
            result.put("msg", "删除课件失败");
        } else {
            result.put("status", StatusCode.SUCCESS);
            result.put("msg", "删除课件成功");
        }

        return result;
    }

    @Override
    public JSONObject deleteLessonHomeworkById(int homeworkId){
        JSONObject result = new JSONObject();
        int i = lessonHomeworkDao.deleteByPrimaryKey(homeworkId);
        if(i!=1){
            result.put("status", StatusCode.DELETE_LESSON_HOMEWORK_ERROR);
            result.put("msg", "删除作业失败");
        } else {
            result.put("status", StatusCode.SUCCESS);
            result.put("msg", "删除作业成功");
        }

        return result;

    }

    @Override
    public JSONObject deleteLessonNoticeById(int id){
        JSONObject result = new JSONObject();
        int i = lessonNoticeDao.deleteByPrimaryKey(id);
        if(i!=1){
            result.put("status", StatusCode.DELETE_LESSON_NOTICE_ERROR);
            result.put("msg", "删除通知失败");
        } else {
            result.put("status", StatusCode.SUCCESS);
            result.put("msg", "删除通知成功");
        }

        return result;

    }
}
