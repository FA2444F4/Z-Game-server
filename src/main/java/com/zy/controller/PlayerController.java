package com.zy.controller;

import com.zy.domain.Administrator;
import com.zy.domain.Param;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.service.GameRatingService;
import com.zy.service.PlayerService;
import com.zy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;

    @Autowired
    private GameRatingService gameRatingService;

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

    //判断玩家游戏评论数是否>=5
    @GetMapping("/ifPlayerRatingNumberEnough")
    public Result ifPlayerRatingNumberEnough(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        Integer id =user.getId();

        return new Result(Code.OK,gameRatingService.ifPlayerRatingNumberEnough(id)==true?1:0,null);


    }

    @PutMapping("/playerSubmitRating")
    public Result playerSubmitRating(@RequestBody ArrayList<Map<String,Integer>> ratingList, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        Integer player_id =user.getId();
        /*for (Map<String, Integer> element : ratingList) {
            System.out.println(element.get("id")+","+element.get("rating"));
        }*/
        //让server带着ArrayList<Map<String,Integer>>和player去修改评论
        gameRatingService.createOrUpdatePlayerRating(ratingList,player_id);
        return new Result(Code.OK,null,null);
    }
}
