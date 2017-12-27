package com.ulyne.jpa.service;

import com.ulyne.jpa.dao.LocationJpaDao;
import com.ulyne.jpa.model.LocationJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * location jpa 实现层
 * Created by fanwei_last on 2017/12/27.
 */
@Service
public class LocationJpaServiceImpl implements LocationJpaService{

    @Autowired
    LocationJpaDao locationJpaDao;

    @Override
    public List<LocationJpa> findAll() {
        return locationJpaDao.findAll();
    }
}
