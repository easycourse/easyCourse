package com.easyCourse.controller;

import com.easyCourse.entity.LessonFile;
import com.easyCourse.entity.Teacher;
import com.easyCourse.service.LessonService;
import com.easyCourse.service.TeacherService;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @Resource
    private LessonService lessonService;

    //返回教师主页
    @GetMapping("/index")
    public String getIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "teacher/index";
    }

    //返回注册页面
    @GetMapping("/register")
    public String getRegisterIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "/register";
    }

    //注册验证
    @PostMapping("/register/verify")
    @ResponseBody
    public JSONObject register(@RequestParam(value = "teacherId", required = true) String teacherId, @RequestParam(value = "passwd", required = true) String password,
                               @RequestParam(value = "teacherName", required = true) String teacherName, @RequestParam(value = "phone", required = true) String phone,
                               @RequestParam(value = "mail", required = true) String mail, @RequestParam(value = "location", required = true) String location,
                               HttpSession session) {
        JSONObject result = teacherService.register(teacherId, password, teacherName, phone, mail, location);

        // 注册成功，则获取token和教师信息，保存到session中
        if (result.get("data") != null) {
            Map data = (Map) result.get("data");
            Object token = data.get("token").toString();
            session.setAttribute("userToken", token);
            session.setMaxInactiveInterval(3600);
            Teacher teacher = teacherService.getTeacherById(teacherId);
            session.setAttribute("teacher", teacher);
        }
        return result;
    }

    //登录验证
    @PostMapping("/login/verify")
    @ResponseBody
    public JSONObject login(@RequestParam(value = "teacherId", required = true) String teacherId,
                            @RequestParam(value = "passwd", required = true) String password, HttpSession session) {
        JSONObject result = teacherService.login(teacherId, password);

        // 登录成功
        if (result.get("data") != null) {
            Map data = (Map) result.get("data");
            String token = data.get("token").toString();
            session.setAttribute("userToken", token);
            session.setMaxInactiveInterval(3600);
            Teacher teacher = teacherService.getTeacherById(teacherId);
            session.setAttribute("teacher", teacher);
        }

        return result;
    }


    //教师添加课程
    @PostMapping("/addLesson")
    @ResponseBody
    public JSONObject addLesson(@RequestParam(value = "lessonName", required = true) String lessonName, @RequestParam(value = "lessonTime", required = true) String lessonTime,
                                @RequestParam(value = "lessonDetail", required = false) String lessonDetail, HttpSession session) {
        // 从session中获取教师信息
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();

        return lessonService.addLesson(lessonName, lessonTime, lessonDetail, teacherId);
    }

    //返回老师教授的课程信息
    @GetMapping("/center")
    @ResponseBody
    public JSONObject center(HttpSession session) {
        // 从session中获取教师信息
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        return lessonService.getByTeacherId(teacherId);
    }

    //TODO:查看发布的历史通知
    @GetMapping("//notice/index")
    @ResponseBody
    public JSONObject getNotice(HttpSession session) {
        // 从session中获取教师信息
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        //根据teacherId查询得到一个notice的list 然后放在resultBean里面返回，status用StatusCode.success表示成功
        return null;
    }

    //TODO:添加通知
    @PostMapping("/addNotice")
    @ResponseBody
    public JSONObject addNotice(@RequestParam(value = "title", required = true) String title, @RequestParam(value = "noticeType", required = true) int noticeType,
                                @RequestParam(value = "detail", required = false, defaultValue = "") String detail, @RequestParam(value = "appendix", required = false , defaultValue = "") String appendix,HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        //和添加课程一样，根据参数然后添加 根据结果进一步判断，status用StatusCode.success表示成功
        return null;
    }

    //TODO:查看发布的课件
    @PostMapping("/courseware/index")
    @ResponseBody
    public JSONObject getCourseware(@RequestParam(value = "lessonName", required = false) String lessonName,HttpSession session) {

        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        //根据teacherId查到一个lesson_file的list，然后放在resultBean里面返回

        //根据原型图，老师可以直接查看所有发布的课件，这个时候lessonName就是null/"" ，如果老师输入了lessonName，就要对上面的list进行一次过滤，返回符合结果的list
        return null;
    }

    //TODO:老师上传新课件
    @PostMapping("/addCourseware")
    @ResponseBody
    public JSONObject addCourseware(@RequestParam(value = "title", required = true) String title, @RequestParam(value = "lessonIdList", required = true) List<String> lessonIdList,
                                @RequestParam(value = "detail", required = false, defaultValue = "") String detail, @RequestParam(value = "appendix", required = true) String appendix,HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String teacherId = teacher.getTeacherId();
        //因为可以选择多个课程，所以会有lessonIdList，需要将上述数据一次性插入到数据库中

        //得到lessonFileList
        List<LessonFile> lessonFileList = new ArrayList<>();
        for(int i=0;i<lessonIdList.size();i++){
            LessonFile lessonFile = new LessonFile();
            lessonFile.setLessonId(lessonIdList.get(i));
            lessonFile.setTitle(title);
            lessonFile.setAppendix(appendix);
            lessonFile.setDetail(detail);
            lessonFile.setUserId(teacherId);
            lessonFileList.add(lessonFile);
        }

        /*以下为mapper可以批量插入list的代码，仅做参考 可以将上述lessonFileList作为参数然后插入数据库
        <insert id="insertForeach" parameterType="java.util.List" useGeneratedKeys="false">
                insert into fund
        ( id,fund_name,fund_code,date_x,data_y,create_by,create_date,update_by,update_date,remarks,del_flag)
        values
                <foreach collection="list" item="item" index="index" separator=",">
                (
    					#{item.id},
    					#{item.fundName},
    					#{item.fundCode},
    					#{item.dateX},
    					#{item.dataY},
    					#{item.createBy},
    					#{item.createDate},
    					#{item.updateBy},
    					#{item.updateDate},
    					#{item.remarks},
    					#{item.delFlag}
    				)
    		     </foreach>
        </insert>*/
        return null;
    }
}
