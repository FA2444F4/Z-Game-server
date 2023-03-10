package com.zy.controller;

import com.zy.domain.Developer;
import com.zy.domain.Param;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.service.DeveloperService;
import com.zy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/developer")
public class DeveloperController {
    @Autowired
    private DeveloperService developerService;
    @Autowired
    private UserService userService;


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
        Integer id=user.getId();
        Param param = developerService.getDeveloperInfo(id);
        return new Result(Code.OK,param,"开发商["+developer.getName()+"],您好!");
    }

    @GetMapping("/checkDuplicateUsername/{username}")
    public Result checkDuplicateUsername(@PathVariable String username){
        Integer id = userService.selectUserIdByUsername(username);
        return new Result(Code.OK,id,null);

    }

    @PutMapping("/updateDeveloperInfo")
    public Result updatePlayerInfo(@RequestBody Param param){




        //param中包含developer和user
        //System.out.println(param);
        developerService.updateDeveloperInfo(param.getUser(),param.getDeveloper());
        return new Result(Code.OK,null,null);
    }


}
