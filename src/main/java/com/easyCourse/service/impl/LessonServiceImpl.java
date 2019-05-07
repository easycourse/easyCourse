package com.easyCourse.service.impl;

import com.easyCourse.dao.LessonDao;
import com.easyCourse.service.LessonService;
import com.easyCourse.utils.StatusCode;
import com.easyCourse.vo.LessonVO;
import net.minidev.json.JSONObject;
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

    @Override
    public JSONObject getByTeacherId(String teacherId) {
        JSONObject result = new JSONObject();
        List<LessonVO> lessonList = lessonDao.selectByTeacherId(teacherId);

        result.put("status", StatusCode.SUCCESS);
        result.put("msg", "获取课堂信息成功");
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
}
