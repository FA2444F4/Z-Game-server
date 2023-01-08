package com.zy.interceptor;

import com.zy.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdministratorInterceptor implements HandlerInterceptor {
    /*
    controller之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
        失败了,这里先保留不动
        WebMvcConfigurer那里先取消装配
         */
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        if (user != null && user.getType() == 0) {//如果是管理员
            return true;
        }else {
            System.out.println("管理员页面拦截,转到"+request.getContextPath()+"login");
            response.sendRedirect("http://localhost:8081");

        }

        return true;
        //如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        //如果设置为true时，请求将会继续执行后面的操作
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
