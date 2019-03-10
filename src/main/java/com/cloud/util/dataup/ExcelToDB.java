package com.cloud.util.dataup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cloud.util.StringUtils;

public class ExcelToDB {
	public static void main(String[] args) {
		
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String filePath = "D:\\test.xlsx";
        String columns[] = {"辅助机构","类型","案号","委托日期","原审法院","委托号","标的内容"};
        wb = readExcel(filePath);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        if(StringUtils.isNotBlank(cellData)){
//                        	if(cellData.contains("自主网络拍卖委托活动结果")){
//                        		System.out.println(cellData);
//                        	}else if(cellData.contains("拍卖机构")){
//                        		System.out.println(cellData);
//                        	}else{
                    		map.put(columns[j], cellData);
//                        	}
                        }
                        
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        }
        //遍历解析出来的list
        for (Map<String,String> map : list) {
            for (Entry<String,String> entry : map.entrySet()) {
                System.out.print(entry.getKey()+":"+entry.getValue()+",");
            }
            System.out.println();
        }

    }
    //读取excel
    public static Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
        	
            //判断cell类型
            switch(cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC:{
            	System.out.println(cell.getCellType()+"******"+String.valueOf(cell.getNumericCellValue()));
            	 if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
                     SimpleDateFormat sdf = null;  
                     if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
                         sdf = new SimpleDateFormat("HH:mm");  
                     } else {// 日期  
                         sdf = new SimpleDateFormat("yyyy-MM-dd");  
                     }  
                     Date date = cell.getDateCellValue();  
                     cellValue = sdf.format(date);  
                 }else{
                	 cellValue = String.valueOf(cell.getNumericCellValue());
                 }
                break;
            }
            case Cell.CELL_TYPE_FORMULA:{
//            	System.out.println("********"+HSSFDateUtil.isCellDateFormatted(cell));
                //判断cell是否为日期格式
                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    //转换为日期格式YYYY-mm-dd
                    cellValue = cell.getDateCellValue();
                }else{
                    //数字
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            case Cell.CELL_TYPE_STRING:{
                cellValue = cell.getRichStringCellValue().getString();
                break;
            }
            default:
                cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }



}

