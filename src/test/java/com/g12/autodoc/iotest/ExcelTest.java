package com.g12.autodoc.iotest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelTest {

    @Test
    public void contextLoads() throws FileNotFoundException {


    }


    /**
     * 导入xls
     * @throws Exception
     */
    @Test
    public void readXls() throws Exception{
        FileInputStream inputStrem = new FileInputStream("D:/t2.xls");
        // 创建工作簿的对象
        // 针对xls后缀的文件，使用HSSF....类
        HSSFWorkbook workBook = new HSSFWorkbook(inputStrem);
        // 根据索引获取Sheet对象
        HSSFSheet sheet = workBook.getSheetAt(0);
        // 根据索引获取行对象
        HSSFRow row = sheet.getRow(0);
        // 根据索引获取单元格的对象
        HSSFCell cell = row.getCell(0);
        // 获取单元格的内容，如果是字符串，使用getStringCellValue
        System.out.println(cell.getStringCellValue());
        HSSFCell cell2 = row.getCell(1);
        // 如果是数字，使用getNumericCellValue
        System.out.println(cell2.getNumericCellValue());
        // 关闭工作簿
        workBook.close();
    }


    /**
     * 导入xlsx
     * @throws Exception
     */
    @Test
    public void readXlsx2() throws Exception{
        FileInputStream inputStrem = new FileInputStream("D:/t1.xlsx");
        // 创建工作簿的对象
        // 针对xlsx后缀的文件，使用XSSF....类
        XSSFWorkbook workBook = new XSSFWorkbook(inputStrem);
        // 根据索引获取Sheet对象
        XSSFSheet sheet = workBook.getSheetAt(0);
        // 获取最后一行的索引
        int num = sheet.getLastRowNum();
        // 遍历所有行
        for(int i = 0; i <= num; i++){
            // 根据索引获取行对象
            XSSFRow row = sheet.getRow(i);
            // 根据索引获取单元格的对象
            XSSFCell cell = row.getCell(0);
            // 获取单元格的内容，如果是字符串，使用getStringCellValue
            System.out.println(cell.getStringCellValue());
            XSSFCell cell2 = row.getCell(1);
            if(cell2 != null){
                // 如果是数字，使用getNumericCellValue
                System.out.println(cell2.getNumericCellValue());
            }
        }

        // 关闭工作簿
        workBook.close();
    }

    /**
     * 导出xlsx
     * @throws Exception
     */

    @Test
    public void writeXlsx() throws Exception{

        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Map<String, Object> map = new HashMap<>();
            map.put("name","zhangsan" + i);
            map.put("age", 20 + i);
            map.put("tel", "12345678901");
            list.add(map);
        }

        String[] titles = new String[]{"姓名", "年龄", "电话"};

        // 新建工作簿对象
        XSSFWorkbook workBook = new XSSFWorkbook();
        // 创建sheet对象
        XSSFSheet sheet = workBook.createSheet("学生信息");
        // 创建行,标题行
        XSSFRow row = sheet.createRow(0);
        for(int i = 0; i < titles.length; i++){
            // 创建单元格
            XSSFCell cell = row.createCell(i);
            // 设置单元格内容
            cell.setCellValue(titles[i]);
        }

        for(int i = 0; i < list.size(); i++){
            XSSFRow row2 = sheet.createRow(i + 1);
            row2.createCell(0).setCellValue((String)list.get(i).get("name"));
            row2.createCell(1).setCellValue((Integer)list.get(i).get("age"));
            row2.createCell(2).setCellValue((String)list.get(i).get("tel"));
        }
        // 写excel需要使用输出流
        FileOutputStream outputStream = new FileOutputStream("D:/t3.xlsx");
        workBook.write(outputStream);

        outputStream.flush();
        outputStream.close();

        workBook.close();

    }


}
