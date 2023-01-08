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
//@SessionAttributes("currentUser") //用了这个销毁完session又来了
public class LoginController {
    @Autowired
    private UserService userService;

    //登录
    @GetMapping("/{username}/{password}")
    public Result login(@PathVariable String username, @PathVariable String password, Model model, HttpSession session){
        User user = userService.getUserByUsernameAndPassword(username, password);
        if (user!=null){//登录成功
            //添加session
            session.setAttribute("currentUser",user);

            //model.addAttribute("currentUser",user);
            /*Param param = new Param();
            param.setUser(user);*/
            return new Result(Code.OK,user,null);
        }else {
            //告诉前端失败了
            return new Result(Code.ERR,null,null);
        }
    }

    //玩家注册
    @PostMapping("/playerRegister")
    public Result playerRegister(@RequestBody Param param, Model model, HttpSession session){
        //取出参数
        User user=param.getUser();
        Player player=param.getPlayer();
        //检查用户名是否唯一
        if(!userService.verifyUsernameIsUniQue(user.getUsername())){//如果用户名不唯一
            return new Result(Code.ERR,null,"该用户名已被抢注,请重新起名");
        }
        //为用户补上时间戳
        user.setCreate_time(DataUtil.timestamp());
        //为密码加密
        user.setPassword(DataUtil.MD5(user.getPassword()));
        //添加用户和玩家
        userService.addUserAndPlayer(user,player);
        //添加session
        model.addAttribute("currentUser",user);
        //让客户端跳转到首页
        return new Result(Code.OK,user,null);
    }

    //告诉前端用户是否用session,有的话用户类型
    @GetMapping("/toHome")
    public Result toHome( Model model, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        if(user==null){
            return new Result(Code.ERR,null,null);
        }else if (user.getType()==0){
            return new Result(Code.OK,0,null);
        }else if (user.getType()==1){
            return new Result(Code.OK,1,null);
        }else if (user.getType()==2){
            return new Result(Code.OK,2,null);
        }else {
            //其他情况,先空着
            return new Result(Code.ERR,null,null);
        }
    }

    //销毁session
    @GetMapping("/destorySession")
    public Result destorySession(Model model, HttpSession session){
        session.removeAttribute("currentUser");
        return new Result(Code.OK,null,null);
    }
}
