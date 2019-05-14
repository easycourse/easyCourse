package com.easyCourse.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easyCourse.entity.LessonHomework;
import com.easyCourse.entity.StudentHomework;
import com.easyCourse.entity.Teacher;
import com.easyCourse.service.IStudentService;
import com.easyCourse.service.LessonService;
import com.easyCourse.service.TeacherService;
import com.easyCourse.service.impl.StudentServiceImpl;
import com.easyCourse.utils.StatusCode;
import com.easyCourse.vo.LessonVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class TeacherDaoTest {

    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private IStudentService studentService;

    @Test
    public void selectByIdAndPassword() {
        String teacherId = "1";
        String password = "123456";

        Teacher teacher = teacherDao.selectByIdAndPassword(teacherId, password);
        System.out.println(teacher);
    }

    @Test
    public void selectByMail() {
        String mail = "799926701@qq.com";
        Teacher teacher = teacherDao.selectByMail(mail);
        System.out.println(teacher);
    }

    @Test
    public void selectById() {
        String id = "1";
        Teacher teacher = teacherDao.selectById(id);
        System.out.println(teacher);
    }

    @Test
    public void insertComplete() {

        Teacher teacher = new Teacher();
        teacher.setTeacherId("000004");
        teacher.setPasswd("xxxxxxx");
        teacher.setMail("hhh@qq.com");
        teacher.setTeacherName("hhh");
        teacher.setPhone("12345678900");
        teacher.setLocation("计算机学院");
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        teacher.setCreateTime(timeStamp);
        teacher.setUpdateTime(timeStamp);
        teacher.setIsDelete(0);

        teacherDao.insertComplete(teacher);
    }

    @Test
    public void insertSelective() {

        Teacher teacher = new Teacher();
        teacher.setTeacherId("000005");
        teacher.setPasswd("xxxxxxx");
        teacher.setMail("hhh@qq.com");
        teacher.setTeacherName("hhh");
        teacher.setPhone("12345678900");
        teacher.setLocation("计算机学院");
        teacher.setIsDelete(0);

        teacherDao.insertSelective(teacher);
    }

    @Test
    public void updateByPrimaryKeyComplete() {

        Teacher teacher = teacherDao.selectById("000005");


        teacher.setPasswd("yyyyyy");
        teacher.setMail("hhh@qq.com");
        teacher.setTeacherName("hhh");
        teacher.setPhone("12345678900");
        teacher.setLocation("计算机学院");
        teacher.setIsDelete(0);

        teacherDao.updateByPrimaryKeyComplete(teacher);
    }

    @Test
    public void updateByPrimaryKeySelective() {

        Teacher teacher = new Teacher();
        teacher.setTeacherId("000005");
        teacher.setPasswd("xxxxxxx");
        teacher.setMail("");
        teacher.setTeacherName("hhh");
        teacher.setPhone("12345678900");
        teacher.setLocation("计算机学院");
        teacher.setIsDelete(0);

        teacherDao.updateByPrimaryKeySelective(teacher);
    }

    @Test
    public void deleteByPrimaryKey() {

        int i = teacherDao.deleteByPrimaryKey("000004");

        System.out.println(i);
    }

    @Test
    public void importStudents() {
        Map<String,String> tem = new HashMap<>();
        tem.put("213","123");
        tem.put("111","111");

        int i = teacherDao.importStudents(tem);

        System.out.println(i);
    }

    @Test
    public void getHomeworkSubmitInfo(){
        String teacherId = "1";

        JSONArray totalHWArray = new JSONArray();

        //根据teacherId在lesson表中获取老师的所有课程（课程Id）
        List<LessonVO> lessonList =  lessonService.getLessonListByTeacherId(teacherId);

        //根据课程Id在lesson_homework表中获取所有的作业（作业Id、作业标题、作业描述、发布时间、截止时间）
        for (LessonVO lesson: lessonList) {
            String lessonId = lesson.getLessonId();
            List<LessonHomework> lessonHomeworkList = lessonService.getLessonHWListBylessonId(lessonId);

            JSONObject lessonHWObj = new JSONObject();
            lessonHWObj.put("lessonId", lessonId);
            JSONArray lessonHWArray = new JSONArray();

            for (LessonHomework homework: lessonHomeworkList) {
                JSONObject singleHWObj = new JSONObject();
                singleHWObj.put("homeworkId", homework.getHomeworkId());
                singleHWObj.put("title", homework.getTitle());
                singleHWObj.put("detail", homework.getDetail());
                singleHWObj.put("createTime", homework.getCreateTime());
                singleHWObj.put("dueTime", homework.getDueTime());

                //根据作业Id在student_homework表中获取所有此作业的提交人数
                int submitCount = teacherService.getSubmitCountForSingleHomework(String.valueOf(homework.getHomeworkId()));
                singleHWObj.put("submitCount", submitCount);//已提交人数
                singleHWObj.put("totalCount", lesson.getStudentNum());//课程总人数

                lessonHWArray.add(singleHWObj);
            }

            lessonHWObj.put("homeworkList", lessonHWArray);
            totalHWArray.add(lessonHWObj);

        }

        System.out.println(totalHWArray);
    }

    @Test
    public void test(){
        String homeworkId = "1";

        JSONArray homeworkArray =  new JSONArray();

        List<StudentHomework> studentHomeworkList = teacherService.getSubmitHomeworkByHomeworkId(homeworkId);
        for (StudentHomework sHomework:
                studentHomeworkList) {
            JSONObject homework = new JSONObject();
            String studentName = studentService.getStudentByStudentId(sHomework.getStudentId()).getStudent_name();
            homework.put("studentName", studentName);
            homework.put("submitTime", sHomework.getCreateTime());
            homework.put("studentId", sHomework.getStudentId());
            homework.put("score", sHomework.getScore());
            homeworkArray.add(homework);
        }

        JSONObject result = new JSONObject();
        result.put("statusCode", StatusCode.SUCCESS);
        result.put("homeworkList", homeworkArray);
    }

}