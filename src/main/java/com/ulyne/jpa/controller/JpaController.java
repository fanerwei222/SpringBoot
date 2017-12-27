package com.ulyne.jpa.controller;

import com.ulyne.jpa.dao.LocationJpaDao;
import com.ulyne.jpa.model.LocationJpa;
import com.ulyne.jpa.service.LocationJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * location jpa 控制器
 * Created by fanwei_last on 2017/12/27.
 */
@RequestMapping(value = "/jpa")
@Controller
public class JpaController {

    @Autowired
    LocationJpaService locationJpaService;

    /**
     * 获取所有位置
     * @return
     */
    @RequestMapping(value = "/locations")
    @ResponseBody
    public List locations() {

        List<LocationJpa> locations = locationJpaService.findAll();

        return locations;
    }
}
