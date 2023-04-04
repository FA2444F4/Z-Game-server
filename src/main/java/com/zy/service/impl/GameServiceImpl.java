package com.zy.service.impl;

import com.zy.dao.GameDao;
import com.zy.dao.Game_tagDao;
import com.zy.domain.Game;
import com.zy.service.GameRatingService;
import com.zy.service.GameService;
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
    public List<Game> getGameListByDeveloperId(Integer id) {
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
            Integer ratingNum=gameRatingService.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingService.countRatingAvg(gameId);
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
            Integer ratingNum=gameRatingService.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingService.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
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
            Integer ratingNum=gameRatingService.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
                //temp,生成随机评论值
//                 listElement.put("rating",(new Random().nextInt(5))+1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingService.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }
        //根据评分排序
        Collections.sort(gameList, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
                return (Integer) o2.get("rating")-(Integer) o1.get("rating");
            }
        });

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
            Integer ratingNum=gameRatingService.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
                //temp,生成随机评论值
//                 listElement.put("rating",(new Random().nextInt(5))+1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingService.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }
        //根据评分排序
        Collections.sort(gameList, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
                return (Integer) o1.get("rating")-(Integer) o2.get("rating");
            }
        });

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
            Integer ratingNum=gameRatingService.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
                //temp,生成随机评论值
//                 listElement.put("rating",(new Random().nextInt(5))+1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingService.countRatingAvg(gameId);
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
            Integer ratingNum=gameRatingService.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
                //temp,生成随机评论值
//                 listElement.put("rating",(new Random().nextInt(5))+1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingService.countRatingAvg(gameId);
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
            Integer ratingNum=gameRatingService.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingService.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }
        //把表给前端
        return waitingRatingGameList;
    }


}
