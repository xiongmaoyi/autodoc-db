package com.g12.autodoc.templet.impl;

import com.g12.autodoc.bean.MetaTable;
import com.g12.autodoc.bean.TabColumn;
import com.g12.autodoc.templet.WordDocTemplet;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Component;


import java.math.BigInteger;
import java.util.*;



@Component
public  class BuleTableDoc extends WordDocTemplet {


    private final String DEEP_COLOR = "D9E2F3";
    private final String BASE_COLOR = "FFFFFF";

    /**
     * 配置初始化标题
     * @param doc
     */
    private void initHeading(XWPFDocument doc){
        WordDocTemplet.addCustomHeadingStyle(doc, "标题 1", 1);
        WordDocTemplet.addCustomHeadingStyle(doc, "标题 2", 2);
    }


    /**
     * 获取 word 模板
     * @param tables
     * @return
     * @throws Exception
     */
    @Override
    public XWPFDocument getXWPDocument(List<MetaTable> tables) throws Exception {

        XWPFDocument xwpDocument = new XWPFDocument();
        initHeading(xwpDocument);

        //遍历每一个表
        for(MetaTable metaTable:tables){
            setTabTitle(xwpDocument,metaTable.getTableName());
            //获取表的结构信息
            List<TabColumn> columns = metaTable.getColumns();
            XWPFTable xwpTable = setTabStruct(xwpDocument,columns);
            setTabHeader(xwpDocument,xwpTable);
            setTabContent(xwpDocument,xwpTable,columns);
            setPreHeader(xwpTable,metaTable);
        }

       return xwpDocument;
    }


    /**
     * 配置小标题
     * @param xwpDocument
     * @param tableName
     */
    private void setTabTitle(XWPFDocument xwpDocument,String tableName){
        //新建一个段落  xp(标题)
        XWPFParagraph tableTitle = xwpDocument.createParagraph();
        //二级标题
        tableTitle.setStyle("标题 2");
        //段落中创建一个段文本，默认：宋体（wps）/等线（office2016） 5号 两端对齐 单倍间距
        XWPFRun r1 = tableTitle.createRun();
        //文本内容
        r1.setText(tableName);
        r1.setFontSize(18);
        //加粗
        r1.setBold(false);
        //行间距
        r1.setTextPosition(11);
        //字体，范围----效果不详
        r1.setFontFamily("微软雅黑", XWPFRun.FontCharRange.cs);


        //第二个段落  p （空行）
        XWPFParagraph empetyParagraph = xwpDocument.createParagraph();
        //居中对齐
        empetyParagraph.setAlignment(ParagraphAlignment.CENTER);
        //内容超过宽度自动换行
        empetyParagraph.setWordWrap(true);

    }


    /**
     * 设置表格式
     * @param xwpDocument
     * @param columns
     * @return
     */
    private XWPFTable setTabStruct(XWPFDocument xwpDocument,List<TabColumn> columns){
        //字段数
        int rows = columns.size();
        //7列n+1行，新建一个n+1行  7列的表
        XWPFTable xwpTable = xwpDocument.createTable(rows + 1, 7);
        //表格属性
        CTTblPr tablePr = xwpTable.getCTTbl().addNewTblPr();
        //表格宽度
        CTTblWidth width = tablePr.addNewTblW();
        width.setW(BigInteger.valueOf(8600));
        return xwpTable;

    }



    /**
     * 配置表头
     * @param xwpDocument
     * @param xwpTable
     */
    private void setTabHeader(XWPFDocument xwpDocument,XWPFTable xwpTable){
        xwpTable.getRow(0).setHeight(380);
        setCellText(xwpDocument, xwpTable.getRow(0).getCell(0), "字段名", DEEP_COLOR, getCellWidth(0));
        setCellText(xwpDocument, xwpTable.getRow(0).getCell(1), "注释", DEEP_COLOR, getCellWidth(1));
        setCellText(xwpDocument, xwpTable.getRow(0).getCell(2), "类型", DEEP_COLOR, getCellWidth(2));
        setCellText(xwpDocument, xwpTable.getRow(0).getCell(3), "默认", DEEP_COLOR, getCellWidth(3));
        setCellText(xwpDocument, xwpTable.getRow(0).getCell(4), "自增", DEEP_COLOR, getCellWidth(4));
        setCellText(xwpDocument, xwpTable.getRow(0).getCell(5), "主键", DEEP_COLOR, getCellWidth(5));
        setCellText(xwpDocument, xwpTable.getRow(0).getCell(6), "空值", DEEP_COLOR, getCellWidth(6));
    }


    /**
     * 配置表内容格式及顺序
     * @param xwpDocument
     * @param xwpTable
     * @param columns
     */
    private void setTabContent(XWPFDocument xwpDocument,XWPFTable xwpTable,List<TabColumn> columns){

        List< String> infoSeq = new ArrayList<>();
        infoSeq.add("colName");
        infoSeq.add("dsc");
        infoSeq.add("mixTypeName");
        infoSeq.add("defValue");
        infoSeq.add("isAutoIncrement");
        infoSeq.add("isPk");
        infoSeq.add("isNullAble");


        int i = 1;
        int j = 0;
        //i行
        for (TabColumn tabCol:columns) {
            xwpTable.getRow(i).setHeight(380);
            if (xwpTable.getRow(i) == null) {
                continue;
            }
            //j列
            for(String key:infoSeq){
                setCellText(xwpDocument, xwpTable.getRow(i).getCell(j), tabCol.getColInfoByKey(key), BASE_COLOR, getCellWidth(j));
                j++;
            }
            j = 0;
            ++i;
        }

    }


    /**
     * 配置大表头
     * @param xwpTable
     * @param metaTable
     */
    private void setPreHeader(XWPFTable xwpTable,MetaTable metaTable){

        String tableName = metaTable.getTableName();
        //在表头插入一行
        XWPFTableRow row = xwpTable.insertNewTableRow(0);
        row.setHeight(380);
        //------------------------------------------------
        row.addNewTableCell();
        CTTcPr cellPr = row.getCell(0).getCTTc().addNewTcPr();
        //设置列宽
        cellPr.addNewTcW().setW(BigInteger.valueOf(300));
        cellPr.addNewTcW().setType(STTblWidth.DXA);

        row.getCell(0).setColor(DEEP_COLOR);
        row.getCell(0).setText("表名称");
        //第二个字段
        row.addNewTableCell();
        cellPr = row.getCell(0).getCTTc().addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(4000));

        row.getCell(1).setColor(BASE_COLOR);
        row.getCell(1).setText(tableName);

        row.addNewTableCell();
        cellPr = row.getCell(0).getCTTc().addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(2400));
        row.getCell(2).setColor(DEEP_COLOR);
        row.getCell(2).setText("英文名称");



        row.addNewTableCell();
        CTTc cttc = row.getCell(3).getCTTc();
        CTTcPr ctPr = cttc.addNewTcPr();
        cellPr = row.getCell(0).getCTTc().addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(2800));

        //几列合并
        ctPr.addNewGridSpan().setVal(BigInteger.valueOf(4));
        ctPr.addNewHMerge().setVal(STMerge.CONTINUE);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cttc.getPList().get(0).addNewR().addNewT().setStringValue(tableName);


        //------------------------------------------------
        //再插入一行
        row = xwpTable.insertNewTableRow(1);
        row.setHeight(380);
        row.addNewTableCell();

        cellPr = row.getCell(0).getCTTc().addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(1600));
        row.getCell(0).setColor(DEEP_COLOR);
        row.getCell(0).setText("功能描述");

        //设置描述字段
        row.addNewTableCell();
        cellPr = row.getCell(0).getCTTc().addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(7000));
        cttc = row.getCell(1).getCTTc();
        ctPr = cttc.addNewTcPr();
        ctPr.addNewGridSpan().setVal(BigInteger.valueOf(6));
        ctPr.addNewHMerge().setVal(STMerge.CONTINUE);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.LEFT);
        cttc.getPList().get(0).addNewR().addNewT().setStringValue(metaTable.getTableRemarks());
    }





    /**
     * 设置单元格 格式与内容
     * @param xDocument
     * @param cell
     * @param text
     * @param bgcolor
     * @param width
     */
    private  void setCellText(XWPFDocument xDocument, XWPFTableCell cell, String text, String bgcolor, int width) {
        CTTcPr cellPr = cell.getCTTc().addNewTcPr();
        //设置列宽
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        //固定列宽

        cell.setColor(bgcolor);
        //对齐方式
        cell.setVerticalAlignment(XWPFVertAlign.CENTER);
        cell.setText(text);
    }







    private  int getCellWidth(int index) {
        int cwidth = 1000;
        if (index == 0) {
            cwidth = 1300;
        } else if (index == 1) {
            cwidth = 4000;
        } else if (index == 2) {
            cwidth = 2400;
        } else if (index == 3) {
            cwidth = 900;
        } else if (index == 4) {
            cwidth = 600;
        } else if (index == 5) {
            cwidth = 600;
        }
        return cwidth;
    }
}
