package com.zy.controller;

import com.zy.domain.Developer;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/developer")
public class DeveloperController {
    @Autowired
    private DeveloperService developerService;

    @GetMapping("homeLoad")
    public Result developerHomeLoad(HttpSession session){
        //根据session判断是否是开发商
        User user = (User) session.getAttribute("currentUser");
        if(user==null||user.getType()!=2){//不是开发商
            return new Result(Code.ERR,null,"错误访问");
        }
        //是开发商
        //找到开发商
        Developer developer = developerService.getDeveloperById(user.getId());
        /**
         * todo
         * 准备管理员home需要的数据
         */
        return new Result(Code.OK,null,"开发商["+developer.getName()+"],您好!");
    }
}
