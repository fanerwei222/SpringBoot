package com.ulyne.mybatis.mapper;

import com.ulyne.mybatis.model.LocationMybatis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 位置实体类的 mapper 接口
 * Created by fanwei_last on 2017/12/27.
 */
@Mapper
public interface LocationMybatisMapper {

    @Select("select * from gpsinfo")
    List<LocationMybatis> findAllLocation();
}
