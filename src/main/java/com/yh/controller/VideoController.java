package com.yh.controller;


import com.yh.pojo.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("video")
public class VideoController {


    @RequestMapping("/add")
    public JsonData updateVideo(){

        return JsonData.buildSuccess("video添加成功");

    }

}
