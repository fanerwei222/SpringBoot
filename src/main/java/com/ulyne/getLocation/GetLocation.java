package com.ulyne.getLocation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 设置GPS地理位置
 * Created by fanwei_last on 2017/12/23.
 */
@RequestMapping("/location")
@Controller
public class GetLocation {

    @Autowired
    JdbcTemplate jdbcTemplate;

    String selectAllSql = "SELECT a.id, a.longitude, a.latitude, a.explainInfo, a.address, a.name, a.projectName, a.projectCode FROM gpsinfo a ";

    /**
     * 首页
     * @return
     */
    @RequestMapping("/index")
    public String index(){

        return "/GaoDeMap";
    }

    /**
     * 获取所有位置
     */
    @RequestMapping("/getLocation")
    @ResponseBody
    public Map<String, Object> getLocation(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Headers","X-Requested-With");
        response.addHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,DELETE");
        response.addHeader("Access-Control-Allow-Headers","test");
        Map<String, Object> result = new HashMap<>();
        String address = request.getParameter("address");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(selectAllSql);

        if (!ObjectUtils.isEmpty(list)){
            result.put("data", list);
            result.put("success", true);
            result.put("msg", "查询成功!");
        }else {
            result.put("success", false);
            result.put("msg", "查询为空!");
        }

        return result;
    }

    /**
     * 获取一个位置
     * @param request
     * @return
     */
    @RequestMapping("/getOne")
    @ResponseBody
    public Map<String, Object> getOne(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        String id = request.getParameter("id");
        String sql = "SELECT * FROM gpsinfo WHERE id=?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id);

        result.put("data", list);
        result.put("success", true);
        result.put("msg", "获取成功!");

        return result;
    }

    /**
     * 设置GPS地理位置
     * @return
     */
    @RequestMapping("/setLocation")
    @ResponseBody
    public Map<String, Object> setLocation(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        Map<String, Object> result = new HashMap<>();

        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        String explainInfo = request.getParameter("explainInfo");
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        String projectName = request.getParameter("projectName");
        String projectCode = request.getParameter("projectCode");

        if (!StringUtils.isEmpty(longitude) && !StringUtils.isEmpty(latitude)){
            String id = String.valueOf(UUID.randomUUID());
            int resRow =jdbcTemplate.update("INSERT INTO gpsinfo(id,longitude,latitude,explainInfo,address, name, projectName, projectCode) VALUES (?,?,?,?,?,?,?,?)", new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setString(1, id);
                    preparedStatement.setString(2, longitude);
                    preparedStatement.setString(3, latitude);
                    preparedStatement.setString(4, explainInfo);
                    preparedStatement.setString(5, address);
                    preparedStatement.setString(6, name);
                    preparedStatement.setString(7, projectName);
                    preparedStatement.setString(8, projectCode);
                }
            });

            result.put("success", true);
            result.put("msg", "增加成功!");


            List<Map<String, Object>> list = jdbcTemplate.queryForList(selectAllSql);

            if (!ObjectUtils.isEmpty(list)){
                result.put("data", list);
            }
        }else {
            result.put("success", false);
            result.put("msg", "增加失败!请确认参数正确!");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(selectAllSql);

            if (!ObjectUtils.isEmpty(list)){
                result.put("data", list);
            }
        }

        return result;
    }

    /**
     * 更新位置
     * @param request
     * @return
     */
    @RequestMapping("/updateLocation")
    @ResponseBody
    public Map<String, Object> updateLocation(HttpServletRequest request){

        Map<String, Object> result = new HashMap<>();

        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        String explainInfo = request.getParameter("explainInfo");
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        String id = request.getParameter("id");
        String projectName = request.getParameter("projectName");
        String projectCode = request.getParameter("projectCode");

        if (!StringUtils.isEmpty(longitude) && !StringUtils.isEmpty(latitude) && !StringUtils.isEmpty(id)){

            String idSql = "SELECT * FROM gpsinfo WHERE id=?";
            List<Map<String, Object>> idList = jdbcTemplate.queryForList(idSql, id);

            int resRow =jdbcTemplate.update("UPDATE gpsinfo SET longitude=?, latitude=?, name=?, address=?, explainInfo=?, projectName=?, projectCode=? WHERE id=?", new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    if (!StringUtils.isEmpty(longitude)){
                        preparedStatement.setString(1, longitude);
                    }
                    if (!StringUtils.isEmpty(latitude)){
                        preparedStatement.setString(2, latitude);
                    }
                    if (!StringUtils.isEmpty(name)){
                        preparedStatement.setString(3, name);
                    }else {
                        preparedStatement.setString(3, String.valueOf(idList.get(0).get("name")));
                    }
                    if (!StringUtils.isEmpty(address)){
                        preparedStatement.setString(4, address);
                    }else {
                        preparedStatement.setString(4, String.valueOf(idList.get(0).get("address")));
                    }
                    if (!StringUtils.isEmpty(explainInfo)){
                        preparedStatement.setString(5, explainInfo);
                    }else {
                        preparedStatement.setString(5, String.valueOf(idList.get(0).get("explainInfo")));
                    }
                    if (!StringUtils.isEmpty(projectName)){
                        preparedStatement.setString(6, projectName);
                    }else {
                        preparedStatement.setString(6, String.valueOf(idList.get(0).get("projectName")));
                    }
                    if (!StringUtils.isEmpty(projectCode)){
                        preparedStatement.setString(7, projectCode);
                    }else {
                        preparedStatement.setString(7, String.valueOf(idList.get(0).get("projectCode")));
                    }

                    preparedStatement.setString(8, id);
                }
            });

            result.put("success", true);
            result.put("msg", "更新成功!");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(selectAllSql);

            if (!ObjectUtils.isEmpty(list)){
                result.put("data", list);
            }
        }else {
            result.put("success", false);
            result.put("msg", "更新失败!请确认参数正确!");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(selectAllSql);

            if (!ObjectUtils.isEmpty(list)){
                result.put("data", list);
            }
        }

        return result;
    }

    /**
     * 删除位置
     */
    @RequestMapping("/deleteLocation")
    @ResponseBody
    public Map<String, Object> deleteLocation(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        String id = request.getParameter("id");
        String sql = "DELETE a FROM gpsinfo a WHERE a.id=?";
        jdbcTemplate.update(sql, id);

        result.put("success", true);
        result.put("msg", "删除成功!");

        List<Map<String, Object>> list = jdbcTemplate.queryForList(selectAllSql);

        if (!ObjectUtils.isEmpty(list)){
            result.put("data", list);
        }

        return result;
    }
}
