package com.zy.controller;

import com.alibaba.fastjson.JSON;
import com.zy.domain.Game;
import com.zy.domain.Param;
import com.zy.domain.Tag;
import com.zy.domain.User;
import com.zy.service.GameService;
import com.zy.service.TagService;
import com.zy.util.JsonXMLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private TagService tagService;
    @Autowired
    private GameService gameService;

    @GetMapping("/addGamePageLoad")
    public Result addGamePageLoad(HttpSession session) {
        //给前端开发商id和所有标签
        //找到user
        User user = (User) session.getAttribute("currentUser");
        //开发商id
        //Integer developer_id=user.getId();
        //所有标签
        List<Tag> tagList = tagService.getAllTag();
        Param param = new Param();
        param.setUser(user);
        param.setTagList(tagList);
        return new Result(Code.OK, param, null);

    }

    @PostMapping("/addGame")
    public Result addGame(@RequestBody Map<String, Object> map, HttpSession session) throws Exception {
        //给前端开发商id和所有标签
        //找到user
        User user = (User) session.getAttribute("currentUser");
        //开发商id
        Integer developer_id = user.getId();
        Game game = JsonXMLUtils.map2obj((Map<String, Object>) map.get("game"), Game.class);
        List<Integer> selectTag=(List<Integer>)map.get("selectTag");
        game.setDeveloper_id(developer_id);
        game.setIs_exist(1);
        //添加游戏+获取游戏id+添加游戏标签
        gameService.addGameAndTag(game,selectTag);


        return new Result(Code.OK, null, null);

    }

    @GetMapping("/getGameListByDeveloperId")
    public Result getGameListByDeveloperId(HttpSession session) {
        //获取管理员
        User user = (User) session.getAttribute("currentUser");
        Integer developer_id=user.getId();
        //获取指定开发商的所有游戏
        List<Game> gameList = gameService.getGameListByDeveloperId(developer_id);
        return new Result(Code.OK, gameList, null);

    }

    //开发商获取旗下所有游戏
    @GetMapping("/getAllGame")
    public Result getAllGame(HttpSession session) {


        //获取User
        User user = (User) session.getAttribute("currentUser");
        Integer developer_id=user.getId();
        //获取指定开发商的所有游戏
        List<Game> gameList = gameService.getGameListByDeveloperId(developer_id);
        return new Result(Code.OK, gameList, null);

    }



    @GetMapping("/gameInfoLoad/{id}")
    public Result gameInfoLoad(@PathVariable Integer id, HttpSession session) {
        //id为游戏id
        //获取游戏
        Game game=gameService.selectGameById(id);
        //获取游戏标签
        List<String> tags = tagService.selectTagNameByGameId(id);
        //
        HashMap<String, Object> map = new HashMap<>();
        map.put("game",game);
        map.put("tags",tags);

        return new Result(Code.OK, map, null);

    }

    @DeleteMapping("/deleteGame/{id}")
    public Result deleteGame(@PathVariable Integer id, HttpSession session) {
        //删除标签
        tagService.deleteTagByGameId(id);
        //删除游戏
        gameService.deleteGameByGameId(id);

        return new Result(Code.OK, null, null);

    }

    //获得所有所有的游戏信息的列表
    @GetMapping("/getAllGame2")
    public Result getAllGame2(HttpSession session) {

        ArrayList<HashMap<String,Object>> gameList= gameService.selectAllGame();
        return new Result(Code.OK, gameList, null);

    }

}
