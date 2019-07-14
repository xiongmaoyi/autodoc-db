package com.g12.autodoc.controller;


import com.alibaba.fastjson.JSONObject;
import com.g12.autodoc.Service.GenerateDocService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * jiekou
 */
@Slf4j
@RestController
@RequestMapping("/doc")
public class ExcuteController {

    @Autowired
    private GenerateDocService generateDocService;


    @RequestMapping(value = "/autodoc",method = RequestMethod.POST)
    public Object generateDoc(@RequestBody JSONObject p){
        try{
            List<String> tables = Arrays.asList(p.getString("tablesStr").split(","));
            generateDocService.excute(
                    p.getString("path"),
                    p.getString("dbType"),
                    p.getString("host"),
                    p.getString("port"),
                    p.getString("database"),
                    p.getString("user"),
                    p.getString("pwd"),
                    tables,
                    p.getString("docTemplet"));
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.toString());
            return e;
        }
        return JSONObject.parseObject("{\"result\":\"文档生成成功\"}");
    }



}
