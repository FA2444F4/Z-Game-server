package com.zy.service;

import com.zy.domain.Essay;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Transactional
public interface EssayService {
    Integer addEssay(
            Integer user_type, Integer publisher_id, String essay_name, String essay_comment);

    ArrayList<HashMap<String,Object>> loadEssayList();
    ArrayList<HashMap<String,Object>> loadMyEssayList(Integer user_id,Integer user_type);
    ArrayList<HashMap<String,Object>> loadEssayListTakeNew();
    ArrayList<HashMap<String,Object>> loadEssayListTakeOld();
    ArrayList<HashMap<String,Object>> loadEssayListTakeLikes();
    ArrayList<HashMap<String,Object>> loadEssayListTakeInput(String input);

    HashMap<String,Object> getEssayById(Integer id);

    List<HashMap<String,Object>> getMessageByEssayId(Integer essay_id);

    Integer addLikes(Integer essay_id);

    Integer addMessage(Integer user_type, Integer messenger_id,Integer essay_id,String message);
}
