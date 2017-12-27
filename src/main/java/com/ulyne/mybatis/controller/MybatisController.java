package com.ulyne.mybatis.controller;

import com.ulyne.mybatis.mapper.LocationMybatisMapper;
import com.ulyne.mybatis.model.LocationMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * mybatis 控制器
 * Created by fanwei_last on 2017/12/27.
 */
@RequestMapping("/mybatis")
@Controller
public class MybatisController {

    @Autowired
    LocationMybatisMapper locationMapper;

    /**
     * 获取所有位置
     * @return
     */
    @RequestMapping(value = "/locations")
    @ResponseBody
    @Transactional
    public List locations() {

        List<LocationMybatis> locations = locationMapper.findAllLocation();

        return locations;
    }
}
