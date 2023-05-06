package com.zy.service.impl;

import com.zy.dao.*;
import com.zy.domain.Game;
import com.zy.service.GameRatingService;
import com.zy.service.GameService;
import com.zy.util.DataUtil;
import com.zy.util.Recommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameDao gameDao;

    @Autowired
    private Game_tagDao game_tagDao;

    @Autowired
    private GameRatingService gameRatingService;

    @Autowired
    private GameRatingDao gameRatingDao;
    @Autowired
    private PlayerGameDao playerGameDao;
    @Autowired
    private TagDao tagDao;

    @Override
    public Boolean addGameAndTag(Game game, List<Integer> selectTag) {
        //添加游戏//得到id
        gameDao.addGame(game);
        Integer game_id=game.getId();
        //添加游戏标签
        for (int i=0;i<selectTag.size();i++){
            game_tagDao.addGame_tag(game_id,selectTag.get(i));
        }
        return true;
    }

    @Override
    public List<Game> getGameListByDeveloperId(Long id) {
        List<Game> gameList = gameDao.getGameListByDeveloperId(id);

        return gameList;
    }

    @Override
    public Game selectGameById(Integer id) {
        Game game=gameDao.selectGameById(id);
        return game;
    }

    @Override
    public Integer deleteGameByGameId(Integer id) {
        return gameDao.deleteGameByGameId(id);
    }

    @Override
    public ArrayList<HashMap<String, Object>> selectAllGame() {
        ArrayList<HashMap<String,Object>> gameList=new ArrayList<HashMap<String,Object>>();
        //搜索游戏基本信息
        List<Game> games = gameDao.selectAllGame();
        for (Game game : games) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("game" ,game);
            gameList.add(map);
        }
        //搜索游戏评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            //统计游戏所有评论的评分
            //如果没有则为-1
            //先搜索有几条评论
            Integer ratingNum=gameRatingDao.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingDao.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }
        //搜索各级评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            HashMap<Integer, Integer> gameRatingGrad = gameRatingService.getGameRatingGrad(gameId);
            listElement.put("one",gameRatingGrad.get(1));
            listElement.put("two",gameRatingGrad.get(2));
            listElement.put("three",gameRatingGrad.get(3));
            listElement.put("four",gameRatingGrad.get(4));
            listElement.put("five",gameRatingGrad.get(5));

        }
        //把表给前端
        return gameList;
    }

    //获取玩家拥有的游戏
    @Override
    public ArrayList<HashMap<String, Object>> getPlayerGameList(Long player_id) {
        ArrayList<HashMap<String,Object>> gameList=new ArrayList<HashMap<String,Object>>();
        //搜索游戏基本信息
//        List<Game> games = gameDao.selectAllGame();
        List<Game> games=new ArrayList<Game>();
        //先搜索已拥有游戏id
        List<Integer> haveGameIdList = playerGameDao.selectPlayerGameId(player_id);
        //根据游戏id搜索游戏
        for (Integer game_id : haveGameIdList) {
            Game game = gameDao.selectGameById(game_id);
            games.add(game);
        }

        for (Game game : games) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("game" ,game);
            gameList.add(map);
        }
        //搜索游戏评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            //统计游戏所有评论的评分
            //如果没有则为-1
            //先搜索有几条评论
            Integer ratingNum=gameRatingDao.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingDao.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }
        //把表给前端
        return gameList;
    }

    @Override
    public ArrayList<HashMap<String, Object>> selectGameByInput(String input) {
        ArrayList<HashMap<String,Object>> gameList=new ArrayList<HashMap<String,Object>>();
        //搜索游戏基本信息
        List<Game> games = gameDao.selectGameByInput(input);
        for (Game game : games) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("game" ,game);
            gameList.add(map);
        }
        //搜索游戏评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            //统计游戏所有评论的评分
            //如果没有则为-1
            //先搜索有几条评论
            Integer ratingNum=gameRatingDao.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingDao.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }
        //搜索各级评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            HashMap<Integer, Integer> gameRatingGrad = gameRatingService.getGameRatingGrad(gameId);
            listElement.put("one",gameRatingGrad.get(1));
            listElement.put("two",gameRatingGrad.get(2));
            listElement.put("three",gameRatingGrad.get(3));
            listElement.put("four",gameRatingGrad.get(4));
            listElement.put("five",gameRatingGrad.get(5));

        }
        //把表给前端
        return gameList;
    }

    //搜索游戏列表,并按评分降序排序
    @Override
    public ArrayList<HashMap<String, Object>> getGameListByRatingDescending() {
        ArrayList<HashMap<String,Object>> gameList=new ArrayList<HashMap<String,Object>>();
        //搜索游戏基本信息
        List<Game> games = gameDao.selectAllGame();
        for (Game game : games) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("game" ,game);
            gameList.add(map);
        }
        //搜索游戏评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            //统计游戏所有评论的评分
            //如果没有则为-1
            //先搜索有几条评论
            Integer ratingNum=gameRatingDao.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
                //temp,生成随机评论值
//                 listElement.put("rating",(new Random().nextInt(5))+1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingDao.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }
        //根据评分排序
        Collections.sort(gameList, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
//                System.out.println("/////////////////");
//                System.out.println(o1.get("rating").toString());
//                System.out.println(o2.get("rating").toString());
//                System.out.println(o1.get("rating").getClass());
//                System.out.println(o2.get("rating").getClass());
//                System.out.println("/////////////////");
                return DataUtil.makeNumberToInteger(o2.get("rating"))-DataUtil.makeNumberToInteger(o1.get("rating"));
//                return (Integer) o2.get("rating")-(Integer) o1.get("rating");
//                return ((Double)o2.get("rating")).intValue() -((Double)o1.get("rating")).intValue();
            }
        });
//搜索各级评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            HashMap<Integer, Integer> gameRatingGrad = gameRatingService.getGameRatingGrad(gameId);
            listElement.put("one",gameRatingGrad.get(1));
            listElement.put("two",gameRatingGrad.get(2));
            listElement.put("three",gameRatingGrad.get(3));
            listElement.put("four",gameRatingGrad.get(4));
            listElement.put("five",gameRatingGrad.get(5));

        }
//        gameList.sort();
        //把表给前端
        return gameList;

    }

    @Override
    public ArrayList<HashMap<String, Object>> getGameListByRatingAscending() {
        ArrayList<HashMap<String,Object>> gameList=new ArrayList<HashMap<String,Object>>();
        //搜索游戏基本信息
        List<Game> games = gameDao.selectAllGame();
        for (Game game : games) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("game" ,game);
            gameList.add(map);
        }
        //搜索游戏评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            //统计游戏所有评论的评分
            //如果没有则为-1
            //先搜索有几条评论
            Integer ratingNum=gameRatingDao.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
                //temp,生成随机评论值
//                 listElement.put("rating",(new Random().nextInt(5))+1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingDao.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }
        //根据评分排序
        Collections.sort(gameList, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
//                return ((Double)o1.get("rating")).intValue() -((Double)o2.get("rating")).intValue();
                return DataUtil.makeNumberToInteger(o1.get("rating"))-DataUtil.makeNumberToInteger(o2.get("rating"));
            }
        });
//搜索各级评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            HashMap<Integer, Integer> gameRatingGrad = gameRatingService.getGameRatingGrad(gameId);
            listElement.put("one",gameRatingGrad.get(1));
            listElement.put("two",gameRatingGrad.get(2));
            listElement.put("three",gameRatingGrad.get(3));
            listElement.put("four",gameRatingGrad.get(4));
            listElement.put("five",gameRatingGrad.get(5));

        }
//        gameList.sort();
        //把表给前端
        return gameList;
    }

    @Override
    public ArrayList<HashMap<String, Object>> getGameListByPriceDescending() {
        ArrayList<HashMap<String,Object>> gameList=new ArrayList<HashMap<String,Object>>();
        //搜索游戏基本信息
        List<Game> games = gameDao.selectAllGame();
        for (Game game : games) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("game" ,game);
            gameList.add(map);
        }
        //搜索游戏评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            //统计游戏所有评论的评分
            //如果没有则为-1
            //先搜索有几条评论
            Integer ratingNum=gameRatingDao.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
                //temp,生成随机评论值
//                 listElement.put("rating",(new Random().nextInt(5))+1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingDao.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }
        //根据价格排序
        Collections.sort(gameList, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
                Double gap=((Game)o2.get("game")).getPrice()-((Game)o1.get("game")).getPrice();
                int bigGap= (int)(100*gap);
                return bigGap;
            }
        });
//搜索各级评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            HashMap<Integer, Integer> gameRatingGrad = gameRatingService.getGameRatingGrad(gameId);
            listElement.put("one",gameRatingGrad.get(1));
            listElement.put("two",gameRatingGrad.get(2));
            listElement.put("three",gameRatingGrad.get(3));
            listElement.put("four",gameRatingGrad.get(4));
            listElement.put("five",gameRatingGrad.get(5));

        }
//        gameList.sort();
        //把表给前端
        return gameList;

    }

    @Override
    public ArrayList<HashMap<String, Object>> getGameListByPriceAscending() {
        ArrayList<HashMap<String,Object>> gameList=new ArrayList<HashMap<String,Object>>();
        //搜索游戏基本信息
        List<Game> games = gameDao.selectAllGame();
        for (Game game : games) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("game" ,game);
            gameList.add(map);
        }
        //搜索游戏评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            //统计游戏所有评论的评分
            //如果没有则为-1
            //先搜索有几条评论
            Integer ratingNum=gameRatingDao.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
                //temp,生成随机评论值
//                 listElement.put("rating",(new Random().nextInt(5))+1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingDao.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }
        Collections.sort(gameList, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
                Double gap=((Game)o1.get("game")).getPrice()-((Game)o2.get("game")).getPrice();
                int bigGap= (int)(100*gap);
                return bigGap;
            }
        });
//搜索各级评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            HashMap<Integer, Integer> gameRatingGrad = gameRatingService.getGameRatingGrad(gameId);
            listElement.put("one",gameRatingGrad.get(1));
            listElement.put("two",gameRatingGrad.get(2));
            listElement.put("three",gameRatingGrad.get(3));
            listElement.put("four",gameRatingGrad.get(4));
            listElement.put("five",gameRatingGrad.get(5));

        }
//        gameList.sort();
        //把表给前端
        return gameList;
    }

    @Override
    public ArrayList<HashMap<String, Object>> getWaitingRatingGameList() {
        ArrayList<HashMap<String,Object>> waitingRatingGameList=new ArrayList<HashMap<String,Object>>();

        //随机找到几条游戏
        List<Integer> gameIdList=gameDao.selectGameIdList();
        Set<Integer> set = new HashSet<>();//集合存储已经选到的随机数
        int length=gameIdList.size();
        //产生10个随机数
        while (set.size()<10){
            int randomIndex=(new Random().nextInt(length));
            //如果该序号已经有了
            if(set.contains(randomIndex)){
                continue;
            }else {//新序号
                set.add(randomIndex);
            }
        }
        //现在set里有十个序号

        //搜索游戏基本信息
        List<Game> games = gameDao.selectAllGame();
        /*for (Game game : games) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("game" ,game);
            waitingRatingGameList.add(map);
        }*/
        for(int index:set){
            HashMap<String, Object> map = new HashMap<String, Object>();
            //找对应元素
            int id=gameIdList.get(index);
            //根据id找游戏
            for (Game game : games) {
                if(game.getId()==id){
                    map.put("game",game);
                    waitingRatingGameList.add(map);
                    break;
                }
            }

        }
        //搜索游戏评分
        for (HashMap<String, Object> listElement : waitingRatingGameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            //统计游戏所有评论的评分
            //如果没有则为-1
            //先搜索有几条评论
            Integer ratingNum=gameRatingDao.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingDao.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }

        return waitingRatingGameList;
    }

    @Override
    public List<Integer> selectGameIdList() {
        return gameDao.selectGameIdList();
    }

    //根据游戏count表返回gameId对应的游戏信息
    @Override
    public ArrayList<HashMap<String, Object>> getRecommendGameListByGameIdList(List<Integer> gameCountList) {
        ArrayList<HashMap<String,Object>> resList=new ArrayList<HashMap<String,Object>>();


        for (Integer game_id : gameCountList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            //找对应元素
            //根据id找游戏
            Game game = gameDao.selectGameById(game_id);
            map.put("game",game);
            resList.add(map);
        }

        //搜索游戏评分
        for (HashMap<String, Object> listElement : resList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            //统计游戏所有评论的评分
            //如果没有则为-1
            //先搜索有几条评论
            Integer ratingNum=gameRatingDao.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingDao.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }
        //把表给前端
        return resList;
    }

    @Override
    public String getGameName(Integer id) {
        return gameDao.getGameName(id);
    }

    @Override
    public List<Integer> getTagSimilarity(List<Integer> currentTagIdList,List<Integer> ultraGamdIdList,Integer tagSlotNum) {
         //游戏id列表
        List<Integer> gameIdList = gameDao.selectGameIdList();
        //游戏数量
        Integer gameNum=gameIdList.size();
        //标签id列表
        List<Integer> tagIdList = tagDao.selectTagIdList();
        //标签数量
        Integer tagNum=tagIdList.size();
        //当前tagIdArray
        Integer[] currentTagArr=new Integer[tagNum];
        for (int i = 0; i < currentTagArr.length; i++) {
            currentTagArr[i]=0;
        }
        for (Integer tag_id : currentTagIdList) {
            currentTagArr[tagIdList.indexOf(tag_id)]=1;
        }

        //Matrix
        Integer[][] array = new Integer[gameNum][tagNum];
        for (int i = 0; i < gameNum; i++) {
            for (Integer j = 0; j < tagNum; j++) {
                array[i][j]=0;
            }
        }
        //搜索每个游戏的tag
        for (Integer game_id : gameIdList) {
            Integer x=gameIdList.indexOf(game_id);
            List<Integer> tempTagIdList = tagDao.selectTagIdListFromGame(game_id);
            for (Integer tag_id : tempTagIdList) {
                Integer y=tagIdList.indexOf(tag_id);
                array[x][y]=1;
            }
        }

        //相似度表          gameSimilarityMap<game_id,相似度>
        HashMap<Integer,Double> gameSimilarityMap=new HashMap<>();
        //记得跳过,List<Integer> ultraGamdIdList中的游戏
        //tagIdList逐渐与每个游戏比相似度
        for (int i = 0; i < array.length; i++) {
            //过滤掉已经在游戏列表里的游戏
            if(!ultraGamdIdList.contains(gameIdList.get(i))){
                //计算array[i]和Integer[] currentTagArr的相似度//
                /////////////////////////////////////////////////////////
                /*System.out.println("currentTagArr");
                for (Integer temp : currentTagArr) {
                    System.out.print(temp+",");
                }
                System.out.println();
                System.out.println("array[i]");
                for (int i1 = 0; i1 < array[i].length; i1++) {
                    System.out.print(array[i][i1]+",");
                }
                System.out.println();*/
                ///////////////////////////////////////////////////

                double similarity = Recommend.pearsonCorrelationInteger(currentTagArr, array[i]);
                //并加入map
               gameSimilarityMap.put(gameIdList.get(i),similarity);
            }
        }
//
        List<Integer> resGameIdList=new ArrayList<>();
        Integer count=tagSlotNum;
        // 使用Stream API对HashMap进行排序，并输出前x个元素到控制台
        gameSimilarityMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(count)
                .forEach(entry -> {
                    resGameIdList.add(entry.getKey());
//                    System.out.println(entry.getKey() + ": " + entry.getValue());
                });

//        System.out.println("resGameIdList");
//        System.out.println(resGameIdList);
        return resGameIdList;
    }


}
