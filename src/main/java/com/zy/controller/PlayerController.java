package com.zy.controller;

import com.zy.dao.PlayerGameDao;
import com.zy.dao.UserWalletDao;
import com.zy.domain.*;
import com.zy.service.*;
import com.zy.util.DataUtil;
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

    @Autowired
    private UserWalletService userWalletService;

    @Autowired
    private PlayerGameDao playerGameDao;
    @Autowired
    private TagService tagService;


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


    //游戏推荐算法1
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

    //游戏推荐算法2
    /*
    ①当玩家评价游戏较少时tag占比权重大
    ②当评价较多时自己权重大
     */
    @GetMapping("/getRecommendGameListPlus")
    public Result getRecommendGameListPlus(HttpSession session) {
        ////////////////////////////////////////////////////////////////
        ////                         计算用户相关度               ////////////
        ////////////////////////////////////////////////////////////////
        //获取当前玩家评分数组
        User user = (User) session.getAttribute("currentUser");
        Integer mine_id = user.getId();
        ArrayList<Map<String, Integer>> self_rating_list = gameRatingService.getGameIdAndRatingFromOnePlayer(mine_id);
        //获取每个人的id
        List<Integer> all_player_id_list = playerService.selectPlayerIdList();
        //初始化相关度表
        ArrayList<Map<String, Object>> correlation_list = new ArrayList<>();
        for (Integer one_player_id : all_player_id_list) {
            if (!one_player_id.equals(mine_id)) {//别和自己算相似度
                ArrayList<Map<String, Integer>> other_rating_list = gameRatingService.getGameIdAndRatingFromOnePlayer(one_player_id);
                Double pearsonCorrelation = Recommend.getPearsonCorrelationFromArrayList(self_rating_list, other_rating_list);
                HashMap<String, Object> map = new HashMap<>();
                map.put("player_id", one_player_id);
                map.put("pearsonCorrelation", pearsonCorrelation);
                correlation_list.add(map);
            }
        }
        //按相关度从高到低排序
        Collections.sort(correlation_list, new Comparator<Map<String, Object>>() {
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
        //到此,获得了相关度从高到低的玩家id和相关度

        //从相关度高于0.5的玩家中找出好评游戏,可以组成一个
        /*
        List<Map<String,Object>> high_correlation_player_rating_list
        其中key为:
        ①游戏id
        ②这些玩家对该游戏的评分总和
         */
        ArrayList<HashMap<String, Integer>> high_correlation_player_rating_list = new ArrayList<>();
        List<Integer> gameIdList = gameService.selectGameIdList();
        for (Integer game_id : gameIdList) {
            HashMap<String, Integer> tempMap = new HashMap<>();
            tempMap.put("game_id", game_id);
            tempMap.put("sum", 0);
            high_correlation_player_rating_list.add(tempMap);
        }
        //从中筛选游戏
        //两个筛选角度
        //①与玩家喜欢游戏的tag相关度
        //②sum较高游戏


        //待处理





        return null;
    }




    @GetMapping("/selectPlayerGameByOne/{game_id}")
    public Result selectPlayerGameByOne(@PathVariable Integer game_id,HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        Integer id = user.getId();
        return new Result(Code.OK,playerService.selectPlayerGameByOne(id,game_id) , null);

    }


    @GetMapping("/getPlayerRatingList")
    public Result selectPlayerGameByOne(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        Integer id = user.getId();
        ArrayList<HashMap<String,Object>> ratingList = new ArrayList<>();
        List<GameRating> rawRatingList = gameRatingService.selectRatingListFromOnePlayer(id);
        for (GameRating element : rawRatingList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("game_id",element.getGame_id());
            map.put("rating",element.getRating());
            map.put("comment",element.getComment());
            map.put("create_time",element.getCreate_time());
            map.put("is_exist",element.getIs_exist());
            //获取游戏名
            map.put("game_name",gameService.getGameName(element.getGame_id()));
            ratingList.add(map);
        }

        return new Result(Code.OK,ratingList , null);

    }


    @GetMapping("/buyAndRatingButtonInfo/{game_id}")
    public Result buyAndRatingButtonInfo(@PathVariable Integer game_id,HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        Integer id = user.getId();

        return new Result(Code.OK,playerService.buyAndRatingButtonInfo(id,game_id) , null);

    }

    //购买游戏
    @GetMapping("/playerBuyGame/{game_id}")
    public Result playerBuyGame(@PathVariable Integer game_id,HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        Integer player_id = user.getId();
        UserWallet wallet = userWalletService.getUserWallet(player_id);
        Game game = gameService.selectGameById(game_id);
        //判断钱够不够
        if(wallet.getBalance()>=game.getPrice()){//钱够
            //扣钱
            userWalletService.subBalance(player_id,game.getPrice());
            //开发商加钱
            userWalletService.addBalance(game.getDeveloper_id(),game.getPrice());
            //登记玩家拥有游戏
            playerGameDao.addPlayerGame(player_id,game_id, DataUtil.timestamp(),1,1);

            return new Result(Code.OK,null,"购买成功");
        }else {//钱不够
            return new Result(Code.ERR,null,"余额不足");

        }

    }

    //获取玩家评分数据和大家评分数据
    @GetMapping("/getRatingInfo/{game_id}")
    public Result getRatingInfo(@PathVariable Integer game_id,HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        Integer player_id = user.getId();


        HashMap<String, Object> myRating = new HashMap<>();

        //获取玩家对于此游戏的评论
        GameRating rating = gameRatingService.selectRatingByPlayerIdAndGameId(player_id, game_id);
        if(rating!=null){
            myRating.put("accept",1);
            myRating.put("rating",rating.getRating());
            myRating.put("comment",rating.getComment());
        }else {
            myRating.put("accept",0);
            myRating.put("rating",null);
            myRating.put("comment",null);
        }
        ArrayList<HashMap<String, Object>> ratingList = new ArrayList<>();
        List<GameRating> oneGameRatingList = gameRatingService.selectRatingListByGameId(game_id);
        for (GameRating element : oneGameRatingList) {
            HashMap<String,Object> map=new HashMap<String,Object>();
            map.put("rating",element.getRating());
            map.put("comment",element.getComment());
            map.put("is_exist",element.getIs_exist());
            Integer tempPlayer_id=element.getPlayer_id();
            Player player = playerService.getPlayerById(tempPlayer_id);
            map.put("player_name",player.getNick_name());
            ratingList.add(map);

        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("myRating",myRating);
        res.put("ratingList",ratingList);
        return new Result(Code.OK,res,null);

    }

    @PutMapping("/putPlayerRating/{rating}/{comment}")
    public Result putPlayerRating(@PathVariable Integer game_id,@PathVariable Integer rating,@PathVariable String comment,HttpSession session){
        //添加评论
        User user = (User) session.getAttribute("currentUser");
        Integer player_id = user.getId();




        return new Result(Code.OK,null,null);
    }

    @GetMapping("/getPlayerWallet")
    public Result getPlayerWallet(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        Integer user_id=user.getId();
        UserWallet userWallet=userWalletService.getUserWallet(user_id);
        return new Result(Code.OK,userWallet,null);
    }

    @PutMapping("/addBalance/{x}")
    public Result addBalance(@PathVariable Double x,HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        Integer user_id=user.getId();
        userWalletService.addBalance(user_id,x);
        return new Result(Code.OK,null,null);
    }

    @GetMapping("/getTags/{game_id}")
    public Result getTags(@PathVariable Integer game_id){
        List<String> tags = tagService.selectTagNameByGameId(game_id);


        return new Result(Code.OK,tags,null);
    }




}
