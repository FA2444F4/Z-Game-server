package com.zy.controller;

import com.zy.domain.Param;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.service.UserService;
import com.zy.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/login")
@SessionAttributes("currentUser")
public class LoginController {
    @Autowired
    private UserService userService;

    //登录
    @GetMapping("/{username}/{password}")
    public Result login(@PathVariable String username, @PathVariable String password, Model model, HttpSession session){
        User user = userService.getUserByUsernameAndPassword(username, password);
        if (user!=null){//登录成功
            //添加session
            model.addAttribute("currentUser",user);
            Param param = new Param();
            param.setUser(user);
            return new Result(Code.OK,user,null);
        }else {
            //告诉前端失败了
            return new Result(Code.ERR,null,null);
        }
    }

    //玩家注册
    @PostMapping("/playerRegister")
    public Result playerRegister(@RequestBody Param param){
        //取出参数
        User user=param.getUser();
        Player player=param.getPlayer();
        //检查用户名是否唯一
        if(!userService.verifyUsernameIsUniQue(user.getUsername())){//如果用户名不唯一
            return new Result(Code.ERR,null,"该用户名已被抢注,请重新起名");
        }
        //添加用户和玩家
        //补上时间戳
        user.setCreate_time(DataUtil.timestamp());




        return null;
    }
}
