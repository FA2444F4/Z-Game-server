package com.zy.controller;

import com.zy.domain.Administrator;
import com.zy.domain.Param;
import com.zy.domain.User;
import com.zy.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    @GetMapping("homeLoad")
    public Result administratorHomeLoad(HttpSession session){
        ///配置拦截器因为等待问题搞不定

        //根据session判断是否是管理员
        User user = (User) session.getAttribute("currentUser");
        if(user==null||user.getType()!=0){//不是管理员
            return new Result(Code.ERR,null,"错误访问");
        }
        //是管理员
        //找到管理员数据
        Administrator administrator = administratorService.getAdministratorById(user.getId());
        /**
         *
         * 准备管理员home需要的数据
         */
        Integer id=user.getId();
        Param param = administratorService.getAdministratorInfo(id);
        return new Result(Code.OK,param,"管理员["+administrator.getNick_name()+"],您好!");
    }



}
