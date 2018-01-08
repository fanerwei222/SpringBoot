package com.ulyne.importAndExportExcel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fanwei_last on 2018/1/5.
 */
public class ImportAndExportExcel {


    /**
     * 批量导入
     * @Description:TODO
     * @author:fanwei
     * @time:2017年12月6日 下午5:49:15
     * @param: @param file
     * @param: @param request
     * @param: @param model
     * @param: @return
     * @param: @throws IOException
     * @return: Map<String,Object>
     */
    @RequestMapping(value="/uploadXlsxFile", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadXlsxFile(@RequestParam(value = "file", required = false)MultipartFile file,
                                              HttpServletRequest request, Model model) throws IOException {

        Map<String, Object> result = new HashMap<>();
        String fileOriginalName = file.getOriginalFilename();
        String suff = fileOriginalName.substring(fileOriginalName.indexOf("xls"));
        InputStream iStream = null;//输入流
        if ("xls".equals(suff)) {//读取xls格式excel文件
            HSSFWorkbook hWorkbook = null;//excel对象
            iStream = file.getInputStream();//读取file文件
            hWorkbook = new HSSFWorkbook(iStream);//转换为excel文档
            HSSFSheet hSheet = hWorkbook.getSheetAt(0);//获取第一行
            if (null!=hSheet) {
                for(int rowNum=1;rowNum<=hSheet.getLastRowNum();rowNum++) {
                    //从第二行开始读取数据
                    HSSFRow hssfRow = hSheet.getRow(rowNum);
                    if (null!=hssfRow) {
                        getValue(hssfRow.getCell(0));

                        result.put("success", true);
                        result.put("msg", "导入成功");
                    }
                }
            }else {
                result.put("success", false);
                result.put("msg", "导入失败");
            }
        }else if ("xlsx".equals(suff)) { //读取xlsx格式excel文件
            XSSFWorkbook xssfWorkbook = null;//excel对象
            iStream = file.getInputStream();//读取file文件
            xssfWorkbook = new XSSFWorkbook(iStream);//转换为excel文档
            XSSFSheet hSheet = xssfWorkbook.getSheetAt(0);//获取第一行
            if (null!=hSheet) {
                for(int rowNum=1;rowNum<=hSheet.getLastRowNum();rowNum++) {
                    //从第二行开始读取数据
                    XSSFRow hssfRow = hSheet.getRow(rowNum);
                    if (null!=hssfRow) {
                        getVal(hssfRow.getCell(0));

                        result.put("success", true);
                        result.put("msg", "导入成功");
                    }
                }
            }else {
                result.put("success", false);
                result.put("msg", "导入失败");
            }
        }

        return result;
    }


    /**
     * 获取xls文件行值
     * @Description:TODO
     * @author:fanwei
     * @time:2017年8月16日 下午3:13:26
     * @param: @param hssfCell
     * @param: @return
     * @return: String
     */
    @SuppressWarnings({ "deprecation", "static-access" })
    private static String getValue(HSSFCell hssfCell) {
        DecimalFormat df = new DecimalFormat("#");
        switch (hssfCell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue()));
                }
                return df.format(hssfCell.getNumericCellValue());
            case HSSFCell.CELL_TYPE_STRING:
                return hssfCell.getStringCellValue();
            case HSSFCell.CELL_TYPE_FORMULA:
                return hssfCell.getCellFormula();
            case HSSFCell.CELL_TYPE_BLANK:
                return "";
        }
        return "";
    }

    /**
     * 获取xlsx文件行值
     * @Description:TODO
     * @author:fanwei
     * @time:2017年8月16日 下午3:12:32
     * @param: @param hssfCell
     * @param: @return
     * @return: String
     */
    @SuppressWarnings({ "deprecation", "static-access" })
    private static String getVal(XSSFCell hssfCell) {
		/*
		 * if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) { return
		 * String.valueOf(hssfCell.getBooleanCellValue()); } else if
		 * (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) { return
		 * String.valueOf(hssfCell.getNumericCellValue()); } else { return
		 * String.valueOf(hssfCell.getStringCellValue()); }
		 */

        DecimalFormat df = new DecimalFormat("#");
        switch (hssfCell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue()));
                }
                return df.format(hssfCell.getNumericCellValue());
            case HSSFCell.CELL_TYPE_STRING:
                return hssfCell.getStringCellValue();
            case HSSFCell.CELL_TYPE_FORMULA:
                return hssfCell.getCellFormula();
            case HSSFCell.CELL_TYPE_BLANK:
                return "";
        }
        return "";
    }
}
