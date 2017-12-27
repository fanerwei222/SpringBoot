package com.ulyne.jpa.dao;

import com.ulyne.jpa.model.LocationJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * location jpa dao层
 * Created by fanwei_last on 2017/12/27.
 */
@Repository
public interface LocationJpaDao extends JpaRepository<LocationJpa, String>{
    List<LocationJpa> findAll();
}
