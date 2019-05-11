package com.easyCourse.controller;

import com.easyCourse.entity.LessonFile;
import com.easyCourse.entity.Student;
import com.easyCourse.service.IStudentService;
import com.easyCourse.utils.Jwt;
import com.easyCourse.utils.StatusCode;
import com.easyCourse.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.easyCourse.vo.StudentHomeworkVO;
import com.easyCourse.vo.StudentLessonVO;
import com.easyCourse.vo.StudentNoticeVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Resource
    private IStudentService studentService;

    //返回登录页面
    @GetMapping("/login")
    public String getLoginIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "login";
    }

    //返回注册页面
    @GetMapping("/register")
    public String getRegisterIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "register";
    }

    //返回课程主页
    @GetMapping("/index")
    public String getIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "student/index";
    }

    //返回课程详情页面
    @GetMapping("/courseIndex")
    public String getcourseIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "student/courseIndex";
    }

    //返回通知列表
    @GetMapping("/informList")
    public String getInformListIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "student/informList";
    }

    //返回作业列表
    @GetMapping("/homeworkList")
    public String getHomeworkListIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "student/homeworkList";
    }

    //返回课件列表
    @GetMapping("/resourceList")
    public String getResourceListIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "student/resourceList";
    }

    /****登录注册模块*****/
    //执行登录请求数据处理
    @RequestMapping(value = "/login/verify", method = RequestMethod.POST)
    public void loginVerify(@RequestParam(value = "studentId", required = true) String studentId, @RequestParam(value = "passwd", required = true)
            String passwd, HttpSession session, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        Student student = this.studentService.loginVerify(studentId, StringUtils.MD5(passwd));
        JSONObject resultJSON = new JSONObject();
        //如果验证未通过
        if (student == null) {
            resultJSON.put("status", StatusCode.INCORRECT_PASSWORD);
            resultJSON.put("msg", "用户名或密码错误");
            response.getWriter().write(String.valueOf(resultJSON));
        } else {
            //创建返回resultJson
            Map<String, Object> payload = new HashMap<String, Object>();
            Date date = new Date();
            payload.put("uid", studentId);//用户ID
            payload.put("iat", date.getTime());//生成时间
            payload.put("ext", date.getTime() + 1000 * 60 * 60);//过期时间1小时
            String token = Jwt.createToken(payload);
            resultJSON.put("status", StatusCode.SUCCESS);
            resultJSON.put("msg", "登陆成功");
            resultJSON.put("token", token);
            //存储session
            session.setAttribute("userToken", token);
            session.setMaxInactiveInterval(3600);//session过期时间为1个小时
            session.setAttribute("student", student);
            response.getWriter().write(String.valueOf(resultJSON));
        }
    }

    //执行注册数据处理
    @RequestMapping(value = "/register/verify", method = RequestMethod.POST)
    public void registerVerify(@RequestParam(value = "studentId", required = true) String studentId, @RequestParam(value = "passwd", required = true)
            String passwd, @RequestParam(value = "email", required = true) String email, @RequestParam(value = "studentName", required = true) String studentName, HttpSession session, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        int code = studentService.registerVerify(studentId, StringUtils.MD5(passwd), email, studentName);
        JSONObject resultJSON = new JSONObject();
        switch (code) {
            case StatusCode.DUPLICATED_STUDENTID: {
                resultJSON.put("status", StatusCode.DUPLICATED_STUDENTID);
                resultJSON.put("msg", "该学号已经被注册，请重试");
                response.getWriter().write(String.valueOf(resultJSON));
                break;
            }
            case StatusCode.DUPLICATED_EMAIL: {
                resultJSON.put("status", StatusCode.DUPLICATED_EMAIL);
                resultJSON.put("msg", "该邮箱已经被注册，请重试");
                response.getWriter().write(String.valueOf(resultJSON));
                break;
            }
            case StatusCode.SUCCESS: {
                Map<String, Object> payload = new HashMap<>();
                Date date = new Date();
                payload.put("uid", studentId);//用户ID
                payload.put("iat", date.getTime());//生成时间
                payload.put("ext", date.getTime() + 1000 * 60 * 60);//过期时间1小时
                String token = Jwt.createToken(payload);
                resultJSON.put("status", StatusCode.SUCCESS);
                resultJSON.put("msg", "注册成功");
                resultJSON.put("token", token);
                //存储session
                Student student = this.studentService.loginVerify(studentId, StringUtils.MD5(passwd));
                session.setAttribute("userToken", token);
                session.setMaxInactiveInterval(3600);//session过期时间为1个小时
                session.setAttribute("student", student);
                response.getWriter().write(String.valueOf(resultJSON));
                break;
            }
        }
    }


    /**
     * 获取某个学生对应的通知列表
     *
     * @param session  session
     * @param response response
     * @throws IOException
     */
    @RequestMapping(value = "/inform", method = RequestMethod.GET)
    public void getInform(HttpSession session, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");

        Student student = (Student) session.getAttribute("student");
        String studentId = student.getStudent_id();
        List<StudentNoticeVO> studentNoticeList = studentService.getAllNoticeByStudentId(studentId);
        JSONObject result = new JSONObject();
        result.put("status", 200);
        result.put("msg", "success");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("studentNoticeList", studentNoticeList);
        result.put("data", resultMap);

        response.getWriter().write(String.valueOf(result));
    }

    /**
     * 根据学生id获取其课程列表
     *
     * @param session  session
     * @param response response
     * @throws IOException
     */
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public void getCourse(HttpSession session, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");

        Student student = (Student) session.getAttribute("student");
        String studentId = student.getStudent_id();

        List<StudentLessonVO> studentLessonList = studentService.getAllLessonsByStudentId(studentId);
        JSONObject result = new JSONObject();
        result.put("status", 200);
        result.put("msg", "success");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("studentLessonList", studentLessonList);
        result.put("data", resultMap);

        response.getWriter().write(String.valueOf(result));
    }

    /**
     * 根据学生id获取其作业列表（包括已提交和非提交）
     *
     * @param session  session
     * @param response response
     * @throws IOException
     */
    @RequestMapping(value = "/homework", method = RequestMethod.GET)
    public void getHomework(HttpSession session, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");

        Student student = (Student) session.getAttribute("student");
        String studentId = student.getStudent_id();

        List<StudentHomeworkVO> studentHomeworkList = studentService.getAllHomeworkByStudentId(studentId);
        List<StudentHomeworkVO> submittedHomeworkList = studentHomeworkList.stream()
                .filter(studentHomeworkVO -> studentHomeworkVO.getSubmitId() != null)
                .collect(Collectors.toList());
        List<StudentHomeworkVO> unSubmittedHomeworkList = studentHomeworkList.stream()
                .filter(studentHomeworkVO -> studentHomeworkVO.getSubmitId() == null)
                .collect(Collectors.toList());

        JSONObject result = new JSONObject();
        result.put("status", 200);
        result.put("msg", "success");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("submittedHomeworkList", submittedHomeworkList);
        resultMap.put("unSubmittedHomeworkList", unSubmittedHomeworkList);
        result.put("data", resultMap);

        response.getWriter().write(String.valueOf(result));
    }

    /**
     * 根据课程id获取某一课程的所有课件
     *
     * @param lessonId 课程id
     * @param response response
     * @throws IOException
     */
    @RequestMapping(value = "/resource", method = RequestMethod.GET)
    public void getResource(@RequestParam("lessonId") String lessonId, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        List<LessonFile> lessonFileList = studentService.getAllLessonFilesByLessonId(lessonId);
        JSONObject result = new JSONObject();
        result.put("status", 200);
        result.put("msg", "success");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("lessonFileList", lessonFileList);
        result.put("data", resultMap);

        response.getWriter().write(String.valueOf(result));
    }
}
