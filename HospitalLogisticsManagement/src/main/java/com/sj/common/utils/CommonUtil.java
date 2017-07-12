package com.sj.common.utils;


import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.sj.common.utils.annotation.Column;
import com.sj.common.utils.annotation.Table;

public class CommonUtil {
    // 判断是不是手机号
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 对象写到excle
     *
     * @param list
     * @param out
     */
    public static void outExcle(List list, OutputStream out) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("sheet0");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        int rowCount = 0;
        HSSFRow row = sheet.createRow(rowCount);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        Class<?> classType = null;
        if (list != null && !list.isEmpty()) {
            String sheetName = "未设置";
            int colNum = 5;
            classType = list.get(0).getClass();
            if (classType.isAnnotationPresent(Table.class)) {
                Table table = classType.getAnnotation(Table.class);
                sheetName = table.name();
                colNum = table.colNum();
            }
            CellRangeAddress cra=new CellRangeAddress(0, 1, 0, colNum);        
            sheet.addMergedRegion(cra); 
            
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(sheetName);
            cell.setCellStyle(style);
            rowCount += 2;
            row = sheet.createRow(rowCount);
            Method[] methods = classType.getMethods();
            int columnCount = 0;//有多少列
            for (Method method : methods) {
                if (method.isAnnotationPresent(Column.class)) {
                    columnCount++;
                    Column column = method.getAnnotation(Column.class);
                    cell = row.createCell(column.sort());
                    cell.setCellValue(column.name());
                    cell.setCellStyle(style);
                }
            }
            //合并单元格
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount - 1));
            // 第五步，写入实体数据
            for (Object o : list) {
                rowCount++;
                row = sheet.createRow(rowCount);
                classType = o.getClass();
                methods = classType.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Column.class)) {
                        Column column = method.getAnnotation(Column.class);
                        try {
                            // 第四步，创建单元格，并设置值
                        	Object str = method.invoke(o);
                            row.createCell(column.sort()).setCellValue(str == null ? "" : str.toString());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            // 第六步，将文件存到指定位置
            try {
                wb.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
