package com.easyCourse.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.easyCourse.dao.LessonDao;
import com.easyCourse.dao.LessonFileDao;
import com.easyCourse.dao.LessonNoticeDao;
import com.easyCourse.dao.TeacherDao;
import com.easyCourse.entity.Lesson;
import com.easyCourse.entity.LessonFile;
import com.easyCourse.entity.LessonNotice;
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
}
