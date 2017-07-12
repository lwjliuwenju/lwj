package com.sj.common.utils;

//该例子用POI 3.0实现,请自行到APATCH网站下载POI

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
* EXCEL文档解析工具类
* 该工具能将EXCEL文档中的表解析为由JAVA基础类构成的数据集合
* 整个EXCEL表由多个行组成.每行用一个LIST表示.
* EXCEL中的行由一个LIST表示,各列的数据索引从0开始一一对齐存放在这个LIST中;
* 多个行构成整个表,由一个LIST存放多个行.
* 
*******************************************
* com.trumptech.common.fileParser.excel
* 2007-6-15
* 16:20:38
* author linfan
*******************************************
*/
public class ExcelSheetParser {

private Logger logger= Logger.getLogger(ExcelSheetParser.class);


private HSSFWorkbook workbook ;

public ExcelSheetParser(File excelFile) throws FileNotFoundException, IOException{
   workbook = new HSSFWorkbook(new FileInputStream(excelFile));
}

/**
* 获得表中的数据
* @param sheetNumber 表格索引(EXCEL 是多表文档,所以需要输入表索引号)
* @return 由LIST构成的行和表
* @throws FileNotFoundException
* @throws IOException
*/
public List<List<Object>> getDatasInSheet(int sheetNumber) throws FileNotFoundException, IOException{
   List<List<Object>> result = new ArrayList<List<Object>>();  
   //获得指定的表
   HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
  
   //获得数据总行数
   int rowCount = sheet.getLastRowNum();
   logger.info("found excel rows count: " + rowCount);
   if (rowCount < 4) {
	   return result;
   }  
   //逐行读取数据
   for (int rowIndex = 4; rowIndex <= rowCount; rowIndex++) {
   
    //获得行对象
    HSSFRow row = sheet.getRow(rowIndex);
   
    if (row != null) {
    
     List<Object> rowData = new ArrayList<Object>();
    
     //获得本行中单元格的个数
     int columnCount = row.getLastCellNum();
    
     //获得本行中各单元格中的数据
     for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {
      @SuppressWarnings("deprecation")
      HSSFCell cell = row.getCell(columnIndex);      
     // HSSFCell cell = row.getCell(columnIndex, null);     
      //获得指定单元格中数据
      Object cellStr = this.getCellString(cell);     
      rowData.add(cellStr);     
     }
    
     result.add(rowData);
    }
   }
   return result;
}

/**
* 获得单元格中的内容
* @param cell
* @return
*/
protected Object getCellString(HSSFCell cell){
   Object result = null;
   if (cell != null) {

    int cellType = cell.getCellType();
   
    switch(cellType){
   
    case HSSFCell.CELL_TYPE_STRING :
     result = cell.getRichStringCellValue().getString();
     break;
    case HSSFCell.CELL_TYPE_NUMERIC:
     result=cell.getNumericCellValue();
     break;
    case HSSFCell.CELL_TYPE_FORMULA:
     result = cell.getNumericCellValue();
     break;
    case HSSFCell.CELL_TYPE_ERROR:
     result=null;
     break;
    case HSSFCell.CELL_TYPE_BOOLEAN:
     result=cell.getBooleanCellValue();
     break;
    case HSSFCell.CELL_TYPE_BLANK:
     result=null;
     break;
    }
   }
   return result;
}

public static void main(String[] args) throws Exception {
   File file = new File("D:\\tmp\\test.xls");
   ExcelSheetParser parser = new ExcelSheetParser(file);
   List<List<Object>> datas = parser.getDatasInSheet(0);

   for(int i=0;i<datas.size();i++){//显示数据
    List<Object> row = datas.get(i);
    for(short n=0;n<row.size() ;n++){
     Object value = row.get(n);
     String data = String.valueOf(value);
     System.out.print(data +"\t");
    }
    System.out.println();
   }
}
}