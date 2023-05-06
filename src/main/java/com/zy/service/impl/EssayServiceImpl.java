package com.zy.service.impl;

import com.zy.dao.*;
import com.zy.domain.Essay;
import com.zy.domain.EssayMessage;
import com.zy.domain.User;
import com.zy.service.EssayService;
import com.zy.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class EssayServiceImpl implements EssayService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private AdministratorDao administratorDao;
    @Autowired
    private DeveloperDao developerDao;
    @Autowired
    private EssayDao essayDao;

    @Override
    public Integer addEssay(Integer user_type, Long publisher_id, String essay_name, String essay_comment) {

        //获取publisher_name
        String publisher_name;
        if (user_type == 0) {
            publisher_name = administratorDao.getAdministratorById(publisher_id).getNick_name();
        } else if (user_type == 1) {
            publisher_name = playerDao.getPlayerById(publisher_id).getNick_name();
        } else {
            publisher_name = developerDao.getDeveloperById(publisher_id).getName();
        }
        Long create_time = DataUtil.timestamp();
        Integer likes = 0;
        essayDao.addEssay(null, publisher_id, publisher_name, create_time, essay_name, essay_comment, likes);

        return null;
    }

    @Override
    public ArrayList<HashMap<String, Object>> loadEssayList() {
        ArrayList<HashMap<String, Object>> essayList = new ArrayList<HashMap<String, Object>>();
        for (Essay essay : essayDao.selectAllEssayIdReverseOrder()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", essay.getId());
            map.put("publisher_id", essay.getPublisher_id());
            map.put("publisher_name", essay.getPublisher_name());
            map.put("create_time", essay.getCreate_time());
            map.put("essay_name", essay.getEssay_name());
            map.put("essay_comment", essay.getEssay_comment());
            map.put("likes", essay.getLikes());
            User user = userDao.getUserById(essay.getPublisher_id());
            map.put("publisher_type", user.getType());
            essayList.add(map);
        }

        return essayList;
    }

    @Override
    public ArrayList<HashMap<String, Object>> loadMyEssayList(Long user_id, Integer user_type) {
        ArrayList<HashMap<String, Object>> essayList = new ArrayList<HashMap<String, Object>>();
        //获取含该用户的essay
        List<Essay> essays = essayDao.selectEssayByPublisherId(user_id);
        for (Essay essay : essays) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", essay.getId());
            map.put("publisher_id", essay.getPublisher_id());
            map.put("publisher_name", essay.getPublisher_name());
            map.put("create_time", essay.getCreate_time());
            map.put("essay_name", essay.getEssay_name());
            map.put("essay_comment", essay.getEssay_comment());
            map.put("likes", essay.getLikes());
            map.put("publisher_type", user_type);
            essayList.add(map);
        }
        return essayList;
    }

    @Override
    public ArrayList<HashMap<String, Object>> loadEssayListTakeNew() {
        ArrayList<HashMap<String, Object>> essayList = new ArrayList<HashMap<String, Object>>();
        for (Essay essay : essayDao.selectAllEssayIdReverseOrder()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", essay.getId());
            map.put("publisher_id", essay.getPublisher_id());
            map.put("publisher_name", essay.getPublisher_name());
            map.put("create_time", essay.getCreate_time());
            map.put("essay_name", essay.getEssay_name());
            map.put("essay_comment", essay.getEssay_comment());
            map.put("likes", essay.getLikes());
            User user = userDao.getUserById(essay.getPublisher_id());
            map.put("publisher_type", user.getType());
            essayList.add(map);
        }

        return essayList;
    }

    @Override
    public ArrayList<HashMap<String, Object>> loadEssayListTakeOld() {
        ArrayList<HashMap<String, Object>> essayList = new ArrayList<HashMap<String, Object>>();
        for (Essay essay : essayDao.selectAllEssay()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", essay.getId());
            map.put("publisher_id", essay.getPublisher_id());
            map.put("publisher_name", essay.getPublisher_name());
            map.put("create_time", essay.getCreate_time());
            map.put("essay_name", essay.getEssay_name());
            map.put("essay_comment", essay.getEssay_comment());
            map.put("likes", essay.getLikes());
            User user = userDao.getUserById(essay.getPublisher_id());
            map.put("publisher_type", user.getType());
            essayList.add(map);
        }

        return essayList;
    }

    @Override
    public ArrayList<HashMap<String, Object>> loadEssayListTakeLikes() {
        ArrayList<HashMap<String, Object>> essayList = new ArrayList<HashMap<String, Object>>();
        for (Essay essay : essayDao.selectAllEssayLikes()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", essay.getId());
            map.put("publisher_id", essay.getPublisher_id());
            map.put("publisher_name", essay.getPublisher_name());
            map.put("create_time", essay.getCreate_time());
            map.put("essay_name", essay.getEssay_name());
            map.put("essay_comment", essay.getEssay_comment());
            map.put("likes", essay.getLikes());
            User user = userDao.getUserById(essay.getPublisher_id());
            map.put("publisher_type", user.getType());
            essayList.add(map);
        }

        return essayList;
    }

    @Override
    public ArrayList<HashMap<String, Object>> loadEssayListTakeInput(String input) {
        ArrayList<HashMap<String, Object>> essayList = new ArrayList<HashMap<String, Object>>();
        Integer maxId = essayDao.selectMaxId();
        //搜索essay id最后一个是多少
        int[] flag = new int[maxId + 2];
        for (int i : flag) {
            i = 0;
        }
        //根据散文名搜索相关的id
        List<Integer> idList1 = essayDao.selectIdByEssayName(input);
        //根据散文内容搜索相关的id
        List<Integer> idList2 = essayDao.selectIdByEssayComment(input);

        for (Integer id : idList1) {
            flag[id] = 1;
        }
        for (Integer id : idList2) {
            flag[id] = 1;
        }
        //根据有效id找出评论然后组合起来
        for (int i = 1; i <= maxId; i++) {
            if (flag[i] == 1) {
                Essay essay = essayDao.selectEssayById(i);
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", essay.getId());
                map.put("publisher_id", essay.getPublisher_id());
                map.put("publisher_name", essay.getPublisher_name());
                map.put("create_time", essay.getCreate_time());
                map.put("essay_name", essay.getEssay_name());
                map.put("essay_comment", essay.getEssay_comment());
                map.put("likes", essay.getLikes());
                User user = userDao.getUserById(essay.getPublisher_id());
                map.put("publisher_type", user.getType());
                essayList.add(map);

            }

        }
        return essayList;
    }

    @Override
    public HashMap<String, Object> getEssayById(Integer id) {

        Essay essay = essayDao.getEssayById(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", essay.getId());
        map.put("publisher_id", essay.getPublisher_id());
        map.put("publisher_name", essay.getPublisher_name());
        map.put("create_time", essay.getCreate_time());
        map.put("essay_name", essay.getEssay_name());
        map.put("essay_comment", essay.getEssay_comment());
        map.put("likes", essay.getLikes());
        User user = userDao.getUserById(essay.getPublisher_id());
        map.put("publisher_type", user.getType());
        return map;
    }

    @Override
    public List<HashMap<String, Object>> getMessageByEssayId(Integer essay_id) {

        List<EssayMessage> messages = essayDao.getMessageByEssayId(essay_id);
        List<HashMap<String, Object>> messageList = new ArrayList<HashMap<String, Object>>();
        for (EssayMessage message : messages) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", message.getId());
            map.put("messenger_id", message.getMessenger_id());
            map.put("messenger_name", message.getMessenger_name());
            map.put("create_time", message.getCreate_time());
            map.put("essay_id", message.getEssay_id());
            map.put("message", message.getMessage());
            User user = userDao.getUserById(message.getMessenger_id());
            map.put("messenger_type", user.getType());
            messageList.add(map);
        }
        return messageList;
    }

    @Override
    public Integer addLikes(Integer essay_id) {
        return essayDao.addLikes(essay_id);
    }

    @Override
    public Integer addMessage(Integer user_type,Long messenger_id, Integer essay_id, String message) {
        Long create_time=DataUtil.timestamp();
        String messenger_name;
        if (user_type == 0) {
            messenger_name = administratorDao.getAdministratorById( messenger_id).getNick_name();
        } else if (user_type == 1) {
            messenger_name = playerDao.getPlayerById( messenger_id).getNick_name();
        } else {
            messenger_name = developerDao.getDeveloperById( messenger_id).getName();
        }


        return essayDao.addMessage(messenger_id,messenger_name,create_time,essay_id,message);
    }
}
