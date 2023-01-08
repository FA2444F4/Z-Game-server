package com.zy.controller;

import com.zy.domain.Administrator;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping("homeLoad")
    public Result administratorHomeLoad(HttpSession session){
        //根据session判断是否是玩家
        User user = (User) session.getAttribute("currentUser");
        if(user==null||user.getType()!=1){//不是玩家
            return new Result(Code.ERR,null,"您不是玩家");
        }
        //是玩家
        //找到玩家数据
        Player player = playerService.getPlayerById(user.getId());
        /**
         * todo
         * 准备管理员home需要的数据
         */
        return new Result(Code.OK,null,"玩家["+player.getNick_name()+"],您好!");
    }

}
