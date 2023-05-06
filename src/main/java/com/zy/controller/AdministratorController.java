package com.zy.controller;

import com.zy.domain.Administrator;
import com.zy.domain.Param;
import com.zy.domain.User;
import com.zy.service.AdministratorService;
import com.zy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private UserService userService;

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
        Long id=user.getId();
        Param param = administratorService.getAdministratorInfo(id);
        return new Result(Code.OK,param,"管理员["+administrator.getNick_name()+"],您好!");
    }

    @GetMapping("/checkDuplicateUsername/{username}")
    public Result checkDuplicateUsername(@PathVariable String username){
        Long id = userService.selectUserIdByUsername(username);
        return new Result(Code.OK,id,null);

    }

    @PutMapping("/updateAdministratorInfo")
    public Result updateAdministratorInfo(@RequestBody Param param){
        //param中包含administrator和user
        administratorService.updateAdministratorInfo(param.getAdministrator(),param.getUser());
        return new Result(Code.OK,null,null);
    }



}
