package com.zy.config;

import com.zy.interceptor.AdministratorInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    //尝试解决跨域问题--失败
    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//项目中的所有接口都支持跨域
                .allowedOrigins("*")//所有地址都可以访问，也可以配置具体地址
                .allowCredentials(true) //是否允许请求带有验证信息
                .allowedMethods("*")//"GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"
                .allowedHeaders("*").maxAge(3600);// 跨域允许时间
    }*/



    //配置拦截器,并设置生效范围--生效,但是其他问题解决不了
    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    //    //注册TestInterceptor拦截器
    //    //失败了,拦截了但是前端一直在
    //    InterceptorRegistration registration = registry.addInterceptor(new AdministratorInterceptor());
    //    registration.addPathPatterns("/administrator/homeLoad");
    //
    //
    //    //registration.addPathPatterns("/**"); //所有路径都被拦截
    //    //registration.excludePathPatterns(    //添加不拦截路径
    //    //        "/login",                    //登录路径
    //    //        "/**/*.html",                //html静态资源
    //    //        "/**/*.js",                  //js静态资源
    //    //        "/**/*.css"                  //css静态资源
    //    //);
    //
    //}
}
