package com.sj.common.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.RegionUtil;
/**
 * 
 * @author wanghaiyang
 * @date 2013-11-26
 * 生成报表通用类
 *
 */
@SuppressWarnings("deprecation")
public class ExportUtils { 
	public static final short center = HSSFCellStyle.ALIGN_CENTER; //居中
	public static final short left = HSSFCellStyle.ALIGN_LEFT;
	public static final short right = HSSFCellStyle.ALIGN_RIGHT;
	/**
	 * 获取报表样式
	 * @param align   布局方位
	 * @param workbook  工作簿
	 * @param font 字体大小
	 * @return
	 */
	public  static  HSSFCellStyle getCellStyle(short align,HSSFWorkbook workbook,short font){
		HSSFCellStyle style = workbook.createCellStyle();   
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);   
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);   
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);   
		style.setAlignment(align);   
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
		style.setWrapText(true);//换行
        HSSFFont f1  = workbook.createFont();     
        f1.setFontHeightInPoints(font);// 字号  
        f1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗   
        style.setFont(f1);  
		return style;
	}
	/**
	 * 获取报表样式
	 * @param align   布局方位
	 * @param workbook  工作簿
	 * @param font 字体大小
	 * @return
	 */
	public  static  HSSFCellStyle getCellStyle(HSSFCellStyle style,short align,HSSFWorkbook workbook,short font,HSSFFont f1){
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);   
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);   
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);   
		style.setAlignment(align);   
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
		style.setWrapText(true);//换行
        // f1  = workbook.createFont();     
        f1.setFontHeightInPoints(font);// 字号  
        f1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗   
        style.setFont(f1);  
		return style;
	}
	/**
	 * @author Administrator
	 * @param workbook 工作簿
	 * @param listtable3数据库数据
	 * @param style4样式
	 * @param name  表头
	 * @param params  表中字段名称 ,字段顺序决定数据组装顺序
	 * @param orders 表中字段的顺序
	 * @return
	 * @throws Exception
	 */
	public static HSSFSheet getSheet(HSSFWorkbook workbook,List<Map<String , Object>> listtable3,HSSFCellStyle style4,
			String name,String[] params,String[] orders) throws Exception{
		HSSFSheet  sheet = workbook.createSheet(name);
        List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
		for(Map<String, Object> m : listtable3){
			LinkedHashMap<String, Object> map222 = new LinkedHashMap<String, Object>();
			for(int i=0;i<orders.length;i++){
				map222.put(orders[i], m.get(orders[i].toUpperCase()));
			}
			list.add(map222);
		}
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(65); //行高 
	    HSSFCell cell = row.createCell(0);  
	    cell.setCellStyle(style4);
	    HSSFRichTextString text = new HSSFRichTextString(name);   
	    cell.setCellValue(text);//把数据放到单元格中   
	    sheet.addMergedRegion(new Region(0, (short)0, 0, (short)7));
	    
	    /**
	     * 第一行字段名称赋值
	     */
	    HSSFRow row1 = sheet.createRow(1);
        for(int i=0;i<params.length;i++){
        	HSSFCell cells = row1.createCell(i);  
        	cells.setCellStyle(getCellStyle(center,workbook,(short)12));
			 if(i==1){
				 cells=row1.createCell(4);
				 sheet.addMergedRegion(new Region(1, (short)4, 1, (short)7));
			 }else{
				 sheet.addMergedRegion(new Region(1, (short)0, 1, (short)3));
			 }
			 cells.setCellValue(params[i]);
			 cells.setCellStyle(getCellStyle(center,workbook,(short)12));
		}
	    if(list!=null && list.size()>0){
	    	for(int i=0;i<list.size();i++){
	    		 HSSFRow rows = sheet.createRow(i+2);
	    		 for(int k=0;k<orders.length;k++){
	    			 HSSFCell cell2 = rows.createCell(k);
	    			 if(k==1){
	    				 cell2=rows.createCell(4);
	    				 sheet.addMergedRegion(new Region(i+2, (short)4, i+2, (short)7));
	    			 }else{
	    				 sheet.addMergedRegion(new Region(i+2, (short)0, i+2, (short)3));
	    			 }
	    			 cell2.setCellValue(String.valueOf(list.get(i).get(orders[k])));
	    			 cell2.setCellStyle(getCellStyle(center,workbook,(short)12));
	    		 }
	    	}
	    }
	    return sheet;
	}
	
	/**
	 * 写入一行数据
	 * @param workbook
	 * @param style4
	 * @param name
	 * @param rowData
	 * @param colspan
	 * @param rowspan
	 * @param start
	 * @return
	 */
	public static HSSFSheet writeOneRow(HSSFWorkbook workbook,HSSFCellStyle style4,String name,Map<String, Integer> rowData,int start){
		HSSFSheet sheet  = workbook.getSheet(name);
		HSSFRow row1 = sheet.createRow(start);
		row1.setHeightInPoints(50); //行高 
		int i =0;
		for (String key : rowData.keySet()) {
		      Integer value =rowData.get(key);
		      short val = 1;
		      if(value!=null){
		    	  val =  (short)(value.intValue());
		      }
		      HSSFCell cells = row1.createCell(i);
		      sheet.setColumnWidth(i,5000);
		      cells.setCellValue(key);
		      style4 = getCellStyle(style4,center,workbook,(short)16, workbook.getFontAt((short)0));
		      CellRangeAddress region = new CellRangeAddress(start,start, i, i+val-1);
		      sheet.addMergedRegion(region);
		      RegionUtil.setBorderBottom(1, region, sheet, workbook);
		      RegionUtil.setBorderLeft(1, region, sheet, workbook);
		      RegionUtil.setBorderRight(1, region, sheet, workbook);
		      RegionUtil.setBorderTop(1, region, sheet, workbook);
		      cells.setCellStyle(style4);
		      i=i+val;
		}
		return sheet;
	}
	/**
	 * 王海洋
	 * 写入一行，支持横向竖向合并单元格
	 * 2014年9月20日09:44:40
	 * @param workbook
	 * @param style4
	 * @param name
	 * @param rowData 
	 * @param start
	 * @return
	 */
	public static HSSFSheet writeNewOneRow(HSSFWorkbook workbook,HSSFCellStyle style4,String name,List<Map<String, Object>> rowData,int start){
		HSSFSheet sheet  = workbook.getSheet(name);
		HSSFRow row1 = sheet.createRow(start);
		row1.setHeightInPoints(50); //行高 
		for(int i =0;i<rowData.size();i++){
			  int rowspan  = rowData.get(i).get("rowspan")==null?0:(Integer)rowData.get(i).get("rowspan");
			  int conspan  = rowData.get(i).get("colspan")==null?0:(Integer)rowData.get(i).get("colspan");
			  Object value  = rowData.get(i).get("value");
			  int index = (Integer)rowData.get(i).get("index");
			  HSSFCell cells = row1.createCell(index);
		      sheet.setColumnWidth(index,5000);
		      cells.setCellValue(value+"");
		      style4 = getCellStyle(style4,center,workbook,(short)12, workbook.getFontAt((short)0));
		      CellRangeAddress region = new CellRangeAddress(start,start+rowspan-1, index,index+conspan-1);
		      sheet.addMergedRegion(region);
		      RegionUtil.setBorderBottom(1, region, sheet, workbook);
		      RegionUtil.setBorderLeft(1, region, sheet, workbook);
		      RegionUtil.setBorderRight(1, region, sheet, workbook);
		      RegionUtil.setBorderTop(1, region, sheet, workbook);
		      cells.setCellStyle(style4);
		}
		return sheet;
	}
	/**
	 * 
	 * @param workbook
	 * @param style4
	 * @param name
	 * @param params
	 * @param start
	 * @return
	 */
	public static HSSFSheet writeHeader(HSSFWorkbook workbook,HSSFCellStyle style4,String name,String []params ,int start){
		HSSFSheet sheet  = workbook.getSheet(name);
		  HSSFRow row1 = sheet.createRow(start);
	        for(int i=0;i<params.length;i++){
	        	HSSFCell cells = row1.createCell(i);  
	        	 sheet.setColumnWidth(i,5000);
				 cells.setCellValue(params[i]);
				 cells.setCellStyle(getCellStyle(center,workbook,(short)12));
			}
		return sheet;
	}
	/**
	 * 写入标题
	 * @param workbook
	 * @param style4
	 * @param name
	 * @param length
	 * @return
	 */
	public static HSSFSheet writefirstRow(HSSFWorkbook workbook,HSSFCellStyle style4,String name,short length){
		HSSFSheet sheet  = workbook.createSheet(name);
		HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(65); //行高 
	    HSSFCell cell = row.createCell(0);  
	    cell.setCellStyle(style4);
	    HSSFRichTextString text = new HSSFRichTextString(name);   
	    cell.setCellValue(text);//把数据放到单元格中   
	    sheet.addMergedRegion(new Region(0, (short)0, 0, length));
		return sheet;
	}
	/**
	 * 作者：王海洋
	 * 时间：2014年8月12日11:15:30
	 * 内容：对原始数据排序
	 * @param list 原始数据
	 * @param orders字段顺序
	 * @param col 从第几列开始只取有数据的列
	 * @return
	 */
	public static List<LinkedHashMap<String,Object>> getOrderList(List<Map<String, Object>> list,String []orders,int col){
		List<LinkedHashMap<String,Object>> linkList = new ArrayList<LinkedHashMap<String,Object>>();
		for(Map<String, Object> m : list){
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			int flag=0;
			for(int i=0;i<orders.length;i++){
				if(i>col){
					flag=1;
				}
				map.put(orders[i], m.get(orders[i]));
			}
			if(flag==1){
				linkList.add(map);
			}
		}
		return linkList;
	}
	/**
	 * 作者：王海洋
	 * 时间：2014年8月12日10:45:51
	 * 内容：循环写入数据
	 * @param workbook
	 * @param list 根据有序字段封装后数据
	 * @param name //sheet 名称
	 * @param orders //字段
	 * @param start //第几行开始循环
	 * @throws Exception
	 */
	public static HSSFSheet getMyBestSheet(HSSFWorkbook workbook,HSSFCellStyle style4,List<LinkedHashMap<String,Object>> list,String name,String[] orders,int start) throws Exception{
			HSSFSheet sheet  = workbook.getSheet(name);
			for(int i=0;i<list.size();i++){
				HSSFRow rows = sheet.createRow(i+start);
				for(int k=0;k<orders.length;k++){
					HSSFCell cell2 = rows.createCell(k);
					String value="";
					style4 = getCellStyle(style4,center,workbook,(short)12, workbook.getFontAt((short)0));
					cell2.setCellStyle(style4);
					if(null != list.get(i).get(orders[k])){
						value= list.get(i).get(orders[k]).toString();
						if("NaN".equals(value)){
							value="0";
						}
						try {
							cell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						    cell2.setCellValue(Double.parseDouble(value));
						} catch (Exception e) {
							cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell2.setCellValue(value);
						}
					}else{
						cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell2.setCellValue(value);
					}
					if(value.length()>5){
						sheet.setColumnWidth(k,5000);
					}else{
						sheet.setColumnWidth(k,2500);
					}
				}
			}
	    return sheet;
	}
	/**
	 * @author 王海洋
	 * @param workbook 工作簿
	 * @param listtable3数据库数据
	 * @param style4样式
	 * @param name  表头
	 * @param params  表中字段名称 ,字段顺序决定数据组装顺序
	 * @param orders 表中字段的顺序
	 * @return
	 * @throws Exception
	 */
	public static HSSFSheet getMySheet(HSSFWorkbook workbook,List<Map<String , Object>> listtable3,HSSFCellStyle style4,
			String name,String[] params,String[] orders,short length) throws Exception{
		HSSFSheet  sheet = workbook.createSheet(name);
        List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
		for(Map<String, Object> m : listtable3){
			LinkedHashMap<String, Object> map222 = new LinkedHashMap<String, Object>();
			for(int i=0;i<orders.length;i++){
				map222.put(orders[i], m.get(orders[i].toUpperCase()));
			}
			list.add(map222);
		}
        HSSFRow row = sheet.createRow(0);
       
        row.setHeightInPoints(65); //行高 
	    HSSFCell cell = row.createCell(0);  
	    cell.setCellStyle(style4);
	    HSSFRichTextString text = new HSSFRichTextString(name);   
	    cell.setCellValue(text);//把数据放到单元格中   
	    sheet.addMergedRegion(new Region(0, (short)0, 0, length));
	    
	    /**
	     * 第一行字段名称赋值
	     */
	    HSSFRow row1 = sheet.createRow(1);
        for(int i=0;i<params.length;i++){
        	HSSFCell cells = row1.createCell(i);  
        	 sheet.setColumnWidth(i,5000);
			 cells.setCellValue(params[i]);
			 style4 = getCellStyle(style4,center,workbook,(short)12, workbook.getFontAt((short)0));
			 cells.setCellStyle(style4);
		}
	    if(list!=null && list.size()>0){
	    	for(int i=0;i<list.size();i++){
	    		 HSSFRow rows = sheet.createRow(i+2);
	    		 for(int k=0;k<orders.length;k++){
	    			 HSSFCell cell2 = rows.createCell(k);
	    			 String value="";
	    			 if(null != list.get(i).get(orders[k])){
	    				 value= list.get(i).get(orders[k]).toString();
	    				 if("NaN".equals(value)){
	    					 value="0";
	    				 }
	    				 if(NumberUtils.isNumber(value)){
	    					 cell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	    				 }else{
	    					 cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
	    				 }
	    			 }
	    			 if(value.length()>5){
	    				 sheet.setColumnWidth(k,5000);
	    			 }else{
	    				 sheet.setColumnWidth(k,2500);
	    			 }
	    			 cell2.setCellValue(value);
	    			 style4 = getCellStyle(style4,center,workbook,(short)12, workbook.getFontAt((short)0));
	    			 cell2.setCellStyle(style4);
	    		 }
	    	}
	    }
	    return sheet;
	}
	
	/**
	 * @author 王海洋
	 * @param workbook 工作簿
	 * @param listtable3数据库数据
	 * @param style4样式
	 * @param name  表头
	 * @param params  表中字段名称 ,字段顺序决定数据组装顺序
	 * @param orders 表中字段的顺序
	 * @return
	 * @throws Exception
	 */
	public static HSSFSheet getMySheet(HSSFWorkbook workbook,List<Map<String , Object>> listtable3,HSSFCellStyle style4,
			String name,String[] params,String[] orders) throws Exception{
		HSSFSheet  sheet = workbook.createSheet(name);
        List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
		for(Map<String, Object> m : listtable3){
			LinkedHashMap<String, Object> map222 = new LinkedHashMap<String, Object>();
			for(int i=0;i<orders.length;i++){
				map222.put(orders[i], m.get(orders[i].toUpperCase()));
			}
			list.add(map222);
		}
        HSSFRow row = sheet.createRow(0);
       
        row.setHeightInPoints(65); //行高 
	    HSSFCell cell = row.createCell(0);  
	    cell.setCellStyle(style4);
	    HSSFRichTextString text = new HSSFRichTextString(name);   
	    cell.setCellValue(text);//把数据放到单元格中   
	    sheet.addMergedRegion(new Region(0, (short)0, 0, (short)20));
	    /**
	     * 第一行字段名称赋值
	     */
	    HSSFRow row1 = sheet.createRow(1);
        for(int i=0;i<params.length;i++){
        	HSSFCell cells = row1.createCell(i);  
        	 sheet.setColumnWidth(i,5000);
			 cells.setCellValue(params[i]);
			 cells.setCellStyle(getCellStyle(center,workbook,(short)12));
		}
	    if(list!=null && list.size()>0){
	    	for(int i=0;i<list.size();i++){
	    		 HSSFRow rows = sheet.createRow(i+2);
	    		 for(int k=0;k<orders.length;k++){
	    			 HSSFCell cell2 = rows.createCell(k);
	    			 sheet.setColumnWidth(k,5000);
	    			 String value="";
	    			 if(null != list.get(i).get(orders[k])){
	    				 value= list.get(i).get(orders[k]).toString();
	    			 }
	    			 cell2.setCellValue(value);
	    			 cell2.setCellStyle(getCellStyle(center,workbook,(short)12));
	    		 }
	    	}
	    }
	    return sheet;
	}
}
