package com.easyCourse.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//登录拦截器
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // 执行完毕，返回前拦截
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // 在处理过程中，执行拦截
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        HttpSession session = request.getSession();


        String requestUri = request.getRequestURI();
        if(requestUri.startsWith(request.getContextPath())){
            requestUri = requestUri.substring(request.getContextPath().length());
        }
        //系统根目录
        if (requestUri.equals("/")) {
            return true;
        }
        if(session.getAttribute("userToken") == null) {
            //拒绝此次请求
            response.sendRedirect("/student/register");
            return false;
        }else {
            return true;
        }
    }
}

