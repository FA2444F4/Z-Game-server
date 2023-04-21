package com.zy.controller;

import com.zy.domain.Game;
import com.zy.domain.Param;
import com.zy.domain.Tag;
import com.zy.domain.User;
import com.zy.service.GameRatingService;
import com.zy.service.GameService;
import com.zy.service.TagService;
import com.zy.util.JsonXMLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private TagService tagService;
    @Autowired
    private GameService gameService;
    @Autowired
    private GameRatingService gameRatingService;

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
        List<Integer> selectTag = (List<Integer>) map.get("selectTag");
        game.setDeveloper_id(developer_id);
        game.setIs_exist(1);
        //添加游戏+获取游戏id+添加游戏标签
        gameService.addGameAndTag(game, selectTag);


        return new Result(Code.OK, null, null);

    }

    @GetMapping("/getGameListByDeveloperId")
    public Result getGameListByDeveloperId(HttpSession session) {
        //获取管理员
        User user = (User) session.getAttribute("currentUser");
        Integer developer_id = user.getId();
        //获取指定开发商的所有游戏
        List<Game> gameList = gameService.getGameListByDeveloperId(developer_id);
        return new Result(Code.OK, gameList, null);

    }

    //开发商获取旗下所有游戏
    @GetMapping("/getAllGame")
    public Result getAllGame(HttpSession session) {


        //获取User
        User user = (User) session.getAttribute("currentUser");
        Integer developer_id = user.getId();
        //获取指定开发商的所有游戏
        List<Game> gameList = gameService.getGameListByDeveloperId(developer_id);
        return new Result(Code.OK, gameList, null);

    }


    @GetMapping("/gameInfoLoad/{id}")
    public Result gameInfoLoad(@PathVariable Integer id, HttpSession session) {
        //id为游戏id
        //获取游戏
        Game game = gameService.selectGameById(id);
        //获取游戏标签
        List<String> tags = tagService.selectTagNameByGameId(id);
        //
        HashMap<String, Object> map = new HashMap<>();
        map.put("game", game);
        map.put("tags", tags);

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

        ArrayList<HashMap<String, Object>> gameList = gameService.selectAllGame();
        return new Result(Code.OK, gameList, null);

    }

    //获取玩家拥有的游戏
    @GetMapping("/getPlayerGameList")
    public Result getPlayerGameList(HttpSession session) {

        User user = (User) session.getAttribute("currentUser");
        //开发商id
        Integer id = user.getId();
        ArrayList<HashMap<String, Object>> gameList = gameService.getPlayerGameList(id);
        return new Result(Code.OK, gameList, null);

    }

    @GetMapping("/getGameListByRatingDescending")
    public Result getGameListByRatingDescending() {
        ArrayList<HashMap<String, Object>> gameList = gameService.getGameListByRatingDescending();
        return new Result(Code.OK, gameList, null);
    }

    @GetMapping("/getGameListByRatingAscending")
    public Result getGameListByRatingAscending() {
        ArrayList<HashMap<String, Object>> gameList = gameService.getGameListByRatingAscending();
        return new Result(Code.OK, gameList, null);
    }

    @GetMapping("/getGameListByPriceDescending")
    public Result getGameListByPriceDescending() {
        ArrayList<HashMap<String, Object>> gameList = gameService.getGameListByPriceDescending();
        return new Result(Code.OK, gameList, null);
    }

    @GetMapping("/getGameListByPriceAscending")
    public Result getGameListByPriceAscending() {
        ArrayList<HashMap<String, Object>> gameList = gameService.getGameListByPriceAscending();
        return new Result(Code.OK, gameList, null);
    }

    //根据模糊查询获得游戏
    @GetMapping("/selectGameByInput/{input}")
    public Result selectGameByInput(@PathVariable String input, HttpSession session) {
        ArrayList<HashMap<String, Object>> gameList = gameService.selectGameByInput(input);
        return new Result(Code.OK, gameList, null);
    }

    //获取单个游戏
    @GetMapping("/getGameInfo/{id}")
    public Result getGameInfo(@PathVariable Integer id, HttpSession session) {

        HashMap<String, Object> map = new HashMap<>();
        //添加游戏
        Game game = gameService.selectGameById(id);
        map.put("game", game);
        //添加游戏对应评分
        Integer num = gameRatingService.countRatingNum(id);
        if (num == 0) {
            map.put("rating", -1);
        } else {
            map.put("rating", gameRatingService.countRatingAvg(id));
        }
        HashMap<Integer, Integer> gameRatingGrad = gameRatingService.getGameRatingGrad(id);
        map.put("one", gameRatingGrad.get(1));
        map.put("two", gameRatingGrad.get(2));
        map.put("three", gameRatingGrad.get(3));
        map.put("four", gameRatingGrad.get(4));
        map.put("five", gameRatingGrad.get(5));

        return new Result(Code.OK, map,null);
    }








    //获得待评论游戏列表
    @GetMapping("/getWaitingRatingGameList")
    public Result getWaitingRatingGameList(HttpSession session) {
        ArrayList<HashMap<String, Object>> gameList = gameService.getWaitingRatingGameList();
        return new Result(Code.OK, gameList, null);

    }


}
