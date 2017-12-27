package com.ulyne.jpa.service;

import com.ulyne.jpa.model.LocationJpa;

import java.util.List;

/**
 * location jpa 服务层
 * Created by fanwei_last on 2017/12/27.
 */
public interface LocationJpaService {

    List<LocationJpa> findAll();
}
