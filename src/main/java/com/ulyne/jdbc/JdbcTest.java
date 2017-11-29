package com.ulyne.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 测试jdbc数据库连接
 * Created by fanwei_last on 2017/11/29.
 */
@RestController
@RequestMapping("/getJdbc")
public class JdbcTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 测试jdbc
     */
    @RequestMapping("/test")
    public String jdbcTest(){
        String sql = "select * from apple";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        return list.toString();
    }
}
