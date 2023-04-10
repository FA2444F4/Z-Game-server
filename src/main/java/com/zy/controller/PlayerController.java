package com.zy.controller;

import com.zy.domain.Param;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.service.GameRatingService;
import com.zy.service.GameService;
import com.zy.service.PlayerService;
import com.zy.service.UserService;
import com.zy.util.Recommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;

    @Autowired
    private GameRatingService gameRatingService;

    @Autowired
    private GameService gameService;

    @GetMapping("homeLoad")
    public Result playerHomeLoad(HttpSession session) {
        //根据session判断是否是玩家
        User user = (User) session.getAttribute("currentUser");
        if (user == null || user.getType() != 1) {//不是玩家
            return new Result(Code.ERR, null, "错误访问");
        }
        //是玩家
        //找到玩家数据
        Player player = playerService.getPlayerById(user.getId());
        Integer id = user.getId();
        Param param = playerService.getPlayerInfo(id);
        return new Result(Code.OK, param, "玩家[" + player.getNick_name() + "],您好!");
    }

    @GetMapping("/checkDuplicateUsername/{username}")
    public Result checkDuplicateUsername(@PathVariable String username) {
        Integer id = userService.selectUserIdByUsername(username);
        return new Result(Code.OK, id, null);

    }

    @PutMapping("/updatePlayerInfo")
    public Result updatePlayerInfo(@RequestBody Param param) {


        //param中包含player和user
        //System.out.println(param);
        playerService.updatePlayerInfo(param.getUser(), param.getPlayer());
        return new Result(Code.OK, null, null);
    }

    //判断玩家游戏评论数是否>=5
    @GetMapping("/ifPlayerRatingNumberEnough")
    public Result ifPlayerRatingNumberEnough(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        Integer id = user.getId();

        return new Result(Code.OK, gameRatingService.ifPlayerRatingNumberEnough(id) == true ? 1 : 0, null);


    }

    @PutMapping("/playerSubmitRating")
    public Result playerSubmitRating(@RequestBody ArrayList<Map<String, Integer>> ratingList, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        Integer player_id = user.getId();
        /*for (Map<String, Integer> element : ratingList) {
            System.out.println(element.get("id")+","+element.get("rating"));
        }*/
        //让server带着ArrayList<Map<String,Integer>>和player去修改评论
        gameRatingService.createOrUpdatePlayerRating(ratingList, player_id);
        return new Result(Code.OK, null, null);
    }

    @GetMapping("/getTestRecommendGame")
    public Result getTestRecommendGame() {
        ArrayList<HashMap<String, Object>> gameList = gameService.selectAllGame();
        return new Result(Code.OK, gameList.subList(1, 5), null);
    }

    @GetMapping("/getRecommendGameList")
    public Result getRecommendGameList(HttpSession session) {
        //获取当前玩家的 评分数组
        User user = (User) session.getAttribute("currentUser");
        Integer player_id = user.getId();
        //selfRatingList map<游戏id,评分>
        ArrayList<Map<String, Integer>> selfRatingList = gameRatingService.getGameIdAndRatingFromOnePlayer(player_id);
        //依次获得每个人的评分数组,然后去计算
        List<Integer> playerIdList = playerService.selectPlayerIdList();
        //创建相关度初始值表[<id,相关度>]
        ArrayList<Map<String, Object>> correlationList = new ArrayList<>();
        //用户与每个其他用户计算相关度
        for (Integer otherPlayerId : playerIdList) {
            if (!otherPlayerId.equals(player_id)) {//别和自己算
                ArrayList<Map<String, Integer>> otherRatingList = gameRatingService.getGameIdAndRatingFromOnePlayer(otherPlayerId);
                Double pearsonCorrelation = Recommend.getPearsonCorrelationFromArrayList(selfRatingList, otherRatingList);
                HashMap<String, Object> map = new HashMap<>();
                map.put("player_id", otherPlayerId);
                map.put("pearsonCorrelation", pearsonCorrelation);
                correlationList.add(map);
            }

        }
        //获得了相关性表
        //将相关性表排序
        Collections.sort(correlationList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {

                Double correlation1 = ((Double) o1.get("pearsonCorrelation"));
                Double correlation2 = ((Double) o2.get("pearsonCorrelation"));
//                return DataUtil.makeNumberToInteger(correlation1)-DataUtil.makeNumberToInteger(correlation2);
                if (correlation1 > correlation2) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
//        System.out.println(correlationList);
        //相关度已经从大到小排序了
        //游戏推荐表List<map<>>"游戏ID":推荐数   并初始化
        ArrayList<HashMap<String, Integer>> gameCountList = new ArrayList<>();
        List<Integer> gameIdList = gameService.selectGameIdList();
        for (Integer game_id : gameIdList) {
            HashMap<String, Integer> tempMap = new HashMap<>();
            tempMap.put("game_id", game_id);
            tempMap.put("count", 0);
            gameCountList.add(tempMap);
        }
        //取前五名的游戏评分
        for (int i = 0; i < 5; i++) {
            Integer otherplayerId = (Integer) (correlationList.get(i).get("player_id"));
            ArrayList<Map<String, Integer>> otherRatingList = gameRatingService.getGameIdAndRatingFromOnePlayer(otherplayerId);
            for (Map<String, Integer> otherMap : otherRatingList) {
                //判断评分
                if (otherMap.get("rating") >= 1) {//有必要计入
                    for (HashMap<String, Integer> gameCountItem : gameCountList) {
                        if (gameCountItem.get("game_id").equals(otherMap.get("game_id"))) {//如果id相等
                            Integer oldCount = gameCountItem.get("count");
                            gameCountItem.put("count", oldCount + otherMap.get("rating"));
                            break;
                        }
                    }
                }
            }
        }
        //对这个游戏评分列表进行排序
        Collections.sort(gameCountList, new Comparator<Map<String, Integer>>() {
            @Override
            public int compare(Map<String, Integer> o1, Map<String, Integer> o2) {
                return o2.get("count") - o1.get("count");
            }
        });
        //根据count最高的五个游戏去搜索游戏信息
//        System.out.println(gameCountList.subList(0, 5));
        ArrayList<Integer> recommendGameIdList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            recommendGameIdList.add(gameCountList.get(i).get("game_id"));
        }

        ArrayList<HashMap<String, Object>> gameList = gameService.getRecommendGameListByGameIdList(recommendGameIdList);

//        System.out.println("#####"+gameList);


        return new Result(Code.OK, gameList, null);
    }


}
