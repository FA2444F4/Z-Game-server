package com.zy.controller;

import com.zy.domain.Tag;
import com.zy.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    //新增标签
    @PostMapping("/addTag")
    public Result addTag(@RequestBody Tag tag){
        //先检查标签名有没有重复
        //重复了,报错
        if (!tagService.ifTagNoExist(tag.getName()))
            return new Result(Code.ERR,null,"该标签已存在");
        //没重复,添加标签
        tagService.addTag(tag);
        return new Result(Code.OK,null,"新增标签成功");
    }

    //获取所有标签信息
    @GetMapping("/getAllTag")
    public Result getAllTag (){
        List<Tag> tagList=tagService.getAllTag();
        if (tagList==null)//查询为空
            return new Result(Code.ERR,null,null);
        return new Result(Code.OK,tagList,null);
    }

    //根据id删除标签
    @DeleteMapping("/deleteTag/{id}")
    public Result deleteTag(@PathVariable Integer id){
        tagService.deleteTag(id);
        return new Result(Code.OK,null,"删除成功");
    }

    //根据名称模糊查询标签
    @GetMapping("/seleteTagByNameLike/{name}")
    public Result seleteTagByNameLike(@PathVariable String name){
        return new Result(Code.OK,tagService.selectTagByNameLike(name),null);
    }

    //修改标签
    @PutMapping("/updateTag")
    public Result updateTag(@RequestBody Tag tag){
        System.out.println(tag);
        Boolean flag = tagService.updateTag(tag);
        return new Result(flag?Code.OK:Code.ERR,null,null);

    }

}
