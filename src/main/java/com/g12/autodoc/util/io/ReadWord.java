package com.g12.autodoc.util.io;


import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 读取docx文件
 * @author Administrator
 *POI在读写word docx文件时是通过xwpf模块来进行的，其核心是XWPFDocument。一个XWPFDocument代表一个docx文档，其可以用来读docx文档，也可以用来写docx文档。
 *XWPFDocument中主要包含下面这几种对象：
 *XWPFParagraph：代表一个段落。
 *XWPFRun：代表具有相同属性的一段文本。
 *XWPFTable：代表一个表格。
 *XWPFTableRow：表格的一行。
 *XWPFTableCell：表格对应的一个单元格。
 */
public class ReadWord {

    /**
     * 通过XWPFWordExtractor读取word文档
     * 只能获取到文本，不能获取到文本对应的属性值
     */
    public static void readByXWPFWordExtractor() throws IOException {
        try {
            FileInputStream input = new FileInputStream("f:/test/肥胖早知道模板.docx");
            XWPFDocument doc = new XWPFDocument(input);
            XWPFWordExtractor docE = new XWPFWordExtractor(doc);
            String  text = docE.getText();
            //将读取到文档中的文本信息，存放在一个txt文件中
            FileWriter write = new FileWriter("f:/test/肥胖早知道的文本信息.txt");
            write.write(text);
            write.close();

            //获取文档的附属信息
            POIXMLProperties.CoreProperties coreP = docE.getCoreProperties();
            //打印文档的分类信息
            System.out.println(coreP.getCategory());
            //打印创建者信息
            System.out.println(coreP.getCreator());
            //打印创建时间
            System.out.println(coreP.getCreated());
            //打印标题
            System.out.println(coreP.getTitle());

            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过XWPFDocument读取word文档
     * 通过XWPFDocument读取docx文档时，我们就可以获取到文本比较精确的属性信息了。比如我们可以获取到某一个XWPFParagraph、XWPFRun或者是某一个XWPFTable，包括它们对应的属性信息
     */
    public static void readByXWPFDocument(){
        Date data = new Date();
        try {
            FileInputStream inputStream = new FileInputStream("f:/test/肥胖早知道模板.docx");
            XWPFDocument doc =new XWPFDocument(inputStream);
            //获取所有段落
            List<XWPFParagraph> list;
            list = doc.getParagraphs();
            FileWriter writer = new FileWriter("f:/test/肥胖早知道 带属性.txt");
            StringBuffer str = new StringBuffer();
            for (XWPFParagraph xwpfParagraph : list) {
                //获取当前段落的属性
                CTPPr CPPR = xwpfParagraph.getCTP().getPPr();
                str.append(xwpfParagraph.getText());
            }
            writer.write(str.toString());

            //获取多有table
            List<XWPFTable> tableList = doc.getTables();
            List<XWPFTableRow> rowList;
            List<XWPFTableCell> cellList;
            StringBuilder build = new StringBuilder();
            for (XWPFTable xwpfTable : tableList) {
                //获取表格信息
                CTTblPr tablePer = xwpfTable.getCTTbl().getTblPr();
                //获取表格相对应的行
                rowList =xwpfTable.getRows();
                for (XWPFTableRow xwpfTableRow : rowList) {
                    cellList = xwpfTableRow.getTableCells();
                    for (XWPFTableCell xwpfTableCell : cellList) {
                        build.append("【单元格信息】："+xwpfTableCell.getText()+"\r\n");
                    }
                }
            }
            writer.append(build);
            writer.close();


            inputStream.close();

            Date date2 = new Date();
            System.out.println("消耗时间:"+(date2.getTime()-data.getTime())+"ms");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //readByXWPFWordExtractor();
        readByXWPFDocument();
    }
}