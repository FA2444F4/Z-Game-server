package com.zy.controller;

import com.zy.domain.Administrator;
import com.zy.domain.Param;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.service.PlayerService;
import com.zy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;

    @GetMapping("homeLoad")
    public Result playerHomeLoad(HttpSession session){
        //根据session判断是否是玩家
        User user = (User) session.getAttribute("currentUser");
        if(user==null||user.getType()!=1){//不是玩家
            return new Result(Code.ERR,null,"错误访问");
        }
        //是玩家
        //找到玩家数据
        Player player = playerService.getPlayerById(user.getId());
        Integer id =user.getId();
        Param param = playerService.getPlayerInfo(id);
        return new Result(Code.OK,param,"玩家["+player.getNick_name()+"],您好!");
    }

    @GetMapping("/checkDuplicateUsername/{username}")
    public Result checkDuplicateUsername(@PathVariable String username){
        Integer id = userService.selectUserIdByUsername(username);
        return new Result(Code.OK,id,null);

    }

    @PutMapping("/updatePlayerInfo")
    public Result updatePlayerInfo(@RequestBody Param param){




        //param中包含player和user
        //System.out.println(param);
        playerService.updatePlayerInfo(param.getUser(),param.getPlayer());
        return new Result(Code.OK,null,null);
    }

}
