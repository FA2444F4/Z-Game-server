package com.zy.controller;

import com.zy.domain.Essay;
import com.zy.domain.User;
import com.zy.service.EssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/essay")
public class EssayController {

    @Autowired
    private EssayService essayService;



    @GetMapping("/loadEssayList")
    public Result loadEssayList() {
        return new Result(Code.OK, essayService.loadEssayList(), null);
    }

    @GetMapping("/loadMyEssayList")
    public Result loadMyEssayList(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        return new Result(Code.OK, essayService.loadMyEssayList(user.getId(), user.getType()), null);
    }

    @GetMapping("/loadEssayListTakeNew")
    public Result loadEssayListTakeNew() {
        return new Result(Code.OK, essayService.loadEssayListTakeNew(), null);
    }

    @GetMapping("/loadEssayListTakeOld")
    public Result loadEssayListTakeOld() {
        return new Result(Code.OK, essayService.loadEssayListTakeOld(), null);
    }

    @GetMapping("/loadEssayListTakeLikes")
    public Result loadEssayListTakeLikes() {
        return new Result(Code.OK, essayService.loadEssayListTakeLikes(), null);
    }

    @GetMapping("/loadEssayListTakeInput/{input}")
    public Result loadEssayListTakeInput(@PathVariable String input) {
        return new Result(Code.OK, essayService.loadEssayListTakeInput(input), null);
    }

    @PostMapping("addEssay")
    public Result addEssay(HttpSession session, @RequestBody Essay essay) {
        //根据session判断是否是管理员
        User user = (User) session.getAttribute("currentUser");
        Long publisher_id = user.getId();
        Integer user_type = user.getType();
        essayService.addEssay(user_type, publisher_id, essay.getEssay_name(), essay.getEssay_comment());
        return new Result(Code.OK, null, null);
    }

    @GetMapping("loadEssayInfo/{essay_id}")
    public Result loadEssayInfo(HttpSession session, @PathVariable Integer essay_id) {
        //获取essay
        HashMap<String, Object> essayMap = essayService.getEssayById(essay_id);
        //获取相关message
        List<HashMap<String, Object>> messageList = essayService.getMessageByEssayId(essay_id);
        HashMap<String, Object> res = new HashMap<>();
        res.put("essay", essayMap);
        res.put("message", messageList);

        //封装发送

        return new Result(Code.OK, res, null);
    }

    @PutMapping("addLikes/{essay_id}")
    public Result addLikes(@PathVariable Integer essay_id) {
        essayService.addLikes(essay_id);
        return null;
    }

    @PostMapping("addMessage/{essay_id}/{inputMessage}")
    public Result addMessage(@PathVariable Integer essay_id,@PathVariable String inputMessage,HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        Long messenger_id=user.getId();
        Integer user_type=user.getType();


         essayService.addMessage(user_type,messenger_id,essay_id,inputMessage);
         return new Result(Code.OK,null,null);
    }


}
