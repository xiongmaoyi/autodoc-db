//package com.g12.autodoc.templet.impl;
//
//import com.g12.autodoc.module.Parameters;
//import com.g12.autodoc.templet.WordDocTemplet;
//import org.apache.poi.xwpf.usermodel.*;
//import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
//
//import java.io.FileOutputStream;
//import java.math.BigInteger;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public  class DefautTableDoc extends WordDocTemplet {
//
//
//    /**
//     * 配置初始化标题
//     * @param doc
//     */
//    static void initHeading(XWPFDocument doc){
//        WordDocTemplet.addCustomHeadingStyle(doc, "标题 1", 1);
//        WordDocTemplet.addCustomHeadingStyle(doc, "标题 2", 2);
//    }
//
//    /**
//     * 生成word，表格
//     *
//     * @param data
//     * @throws Exception
//     */
//
//    @Override
//    public  void productWordForm(Map<String, String> tableinfo, Map<String, LinkedHashMap<String, LinkedHashMap<String, String>>> data,
//                                 Parameters parameters) throws Exception {
//        XWPFDocument xDocument = new XWPFDocument();
//
//        initHeading(xDocument);
//
//
//
//        Iterator<String> tableNameIter = data.keySet().iterator();
//
//
//        //遍历每一个表
//        while (tableNameIter.hasNext()) {
//            String table_name = tableNameIter.next();
//            //新建一个段落  xp
//            XWPFParagraph xp = xDocument.createParagraph();
//            //二级标题
//            xp.setStyle("标题 2");
//            //段落中创建一个段文本，默认：宋体（wps）/等线（office2016） 5号 两端对齐 单倍间距
//            XWPFRun r1 = xp.createRun();
//            //文本内容
//            r1.setText(table_name + " " + tableinfo.get(table_name));
//            r1.setFontSize(18);
//            //加粗
//            r1.setBold(false);
//            //行间距
//            r1.setTextPosition(10);
//            //字体，范围----效果不详
//            //r1.setFontFamily("华文新魏", XWPFRun.FontCharRange.cs);
//
//
//
//            //第二个段落  p （空行）
//            XWPFParagraph p = xDocument.createParagraph();
//            //居中对齐
//            p.setAlignment(ParagraphAlignment.CENTER);
//            //内容超过宽度自动换行
//            p.setWordWrap(true);
//
//            //获取表的结构信息
//            LinkedHashMap<String, LinkedHashMap<String, String>> columns = data.get(table_name);
//            //字段数
//            int rows = columns.size();
//
//            //7列n+1行，新建一个n+1行  7列的表
//            XWPFTable xTable = xDocument.createTable(rows + 1, 7);
//            //表格属性
//            CTTblPr tablePr = xTable.getCTTbl().addNewTblPr();
//            //表格宽度
//            CTTblWidth width = tablePr.addNewTblW();
//            width.setW(BigInteger.valueOf(8600));
//
//            int i = 0;
//            //配置第一行
//            xTable.getRow(i).setHeight(380);
//            setCellText(xDocument, xTable.getRow(i).getCell(0), "代码", "CCCCCC", getCellWidth(0));
//            setCellText(xDocument, xTable.getRow(i).getCell(1), "注释", "CCCCCC", getCellWidth(1));
//            setCellText(xDocument, xTable.getRow(i).getCell(2), "类型", "CCCCCC", getCellWidth(2));
//            setCellText(xDocument, xTable.getRow(i).getCell(3), "默认值", "CCCCCC", getCellWidth(3));
//            setCellText(xDocument, xTable.getRow(i).getCell(4), "标识", "CCCCCC", getCellWidth(4));
//            setCellText(xDocument, xTable.getRow(i).getCell(5), "主键", "CCCCCC", getCellWidth(5));
//            setCellText(xDocument, xTable.getRow(i).getCell(6), "空值", "CCCCCC", getCellWidth(6));
//
//            // 下一行
//            i = i + 1;
//            // 列column索引
//            int j = 0;
//
//            Map<String, LinkedHashMap<String, String>> keyColumnMap = keyColumns(columns);
//            //填写所有行，所有列
//            for (Iterator<String> columnNameIter = keyColumnMap.keySet().iterator(); columnNameIter.hasNext(); ) {
//                String column_name = columnNameIter.next();
//                LinkedHashMap<String, String> columnsAtt = keyColumnMap.get(column_name);
//                int cwidth = getCellWidth(j);
//                setCellText(xDocument, xTable.getRow(i).getCell(j), column_name, "FFFFFF", cwidth);
//                ++j;
//
//                Iterator<String> columnTypeIter = columnsAtt.keySet().iterator();
//
//                while (columnTypeIter.hasNext()) {
//                    String colum_type = columnTypeIter.next();
//                    cwidth = getCellWidth(j);
//                    setCellText(xDocument, xTable.getRow(i).getCell(j), columnsAtt.get(colum_type), "FFFFFF", cwidth);
//                    j++;
//                }
//                // 下一行
//                ++i;
//                // 恢复第一列
//                j = 0;
//            }
//
//            Iterator<String> cloumnsNameIter = columns.keySet().iterator();
//
//            while (cloumnsNameIter.hasNext()) {
//                xTable.getRow(i).setHeight(380);
//                String colum_name = cloumnsNameIter.next();
//                LinkedHashMap<String, String> columnsAtt = columns.get(colum_name);
//                int cwidth = getCellWidth(j);
//                if (xTable.getRow(i) == null) {
//                    continue;
//                }
//                setCellText(xDocument, xTable.getRow(i).getCell(j), colum_name, "FFFFFF", cwidth);
//
//                j++;
//
//                Iterator<String> columnTypeIter = columnsAtt.keySet().iterator();
//
//                while (columnTypeIter.hasNext()) {
//                    String colum_type = columnTypeIter.next();
//                    cwidth = getCellWidth(j);
//                    setCellText(xDocument, xTable.getRow(i).getCell(j), columnsAtt.get(colum_type), "FFFFFF", cwidth);
//                    j++;
//                }
//                // 恢复第一列
//                j = 0;
//                //下一行
//                ++i;
//            }
//
//
//            //在表头插入一行
//            XWPFTableRow row = xTable.insertNewTableRow(0);
//            row.setHeight(380);
//
//            //第一个字段
//            row.addNewTableCell();
//            CTTcPr cellPr = row.getCell(0).getCTTc().addNewTcPr();
//            //设置列宽
//            cellPr.addNewTcW().setW(BigInteger.valueOf(1600));
//            row.getCell(0).setColor("CCCCCC");
//            row.getCell(0).setText("中文名称");
//
//
//            //第二个字段
//            row.addNewTableCell();
//            cellPr = row.getCell(0).getCTTc().addNewTcPr();
//            cellPr.addNewTcW().setW(BigInteger.valueOf(3000));
//            row.getCell(1).setColor("FFFFFF");
//            row.getCell(1).setText(tableinfo.get(table_name));
//
//
//            row.addNewTableCell();
//            cellPr = row.getCell(0).getCTTc().addNewTcPr();
//            cellPr.addNewTcW().setW(BigInteger.valueOf(1200));
//            row.getCell(2).setColor("CCCCCC");
//            row.getCell(2).setText("英文名称");
//
//            row.addNewTableCell();
//            CTTc cttc = row.getCell(3).getCTTc();
//            CTTcPr ctPr = cttc.addNewTcPr();
//            cellPr = row.getCell(0).getCTTc().addNewTcPr();
//            cellPr.addNewTcW().setW(BigInteger.valueOf(2800));
//            ctPr.addNewGridSpan().setVal(BigInteger.valueOf(4));
//            ctPr.addNewHMerge().setVal(STMerge.CONTINUE);
//            cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
//            cttc.getPList().get(0).addNewR().addNewT().setStringValue(table_name);
//
//            //再插入一行
//            row = xTable.insertNewTableRow(1);
//            row.setHeight(380);
//            row.addNewTableCell();
//            cellPr = row.getCell(0).getCTTc().addNewTcPr();
//            cellPr.addNewTcW().setW(BigInteger.valueOf(1600));
//            row.getCell(0).setColor("CCCCCC");
//            row.getCell(0).setText("功能描述");
//
//
//            row.addNewTableCell();
//            cellPr = row.getCell(0).getCTTc().addNewTcPr();
//            cellPr.addNewTcW().setW(BigInteger.valueOf(7000));
//            cttc = row.getCell(1).getCTTc();
//            ctPr = cttc.addNewTcPr();
//            ctPr.addNewGridSpan().setVal(BigInteger.valueOf(6));
//            ctPr.addNewHMerge().setVal(STMerge.CONTINUE);
//            cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.LEFT);
//            cttc.getPList().get(0).addNewR().addNewT().setStringValue("");
//        }
//
//
//        //文件写到系统
//        FileOutputStream fos = new FileOutputStream(parameters.getPath() + parameters.getDatabase() + "_doc.docx");
//
//        xDocument.write(fos);
//        fos.close();
//    }
//
//    private void spanCellsAcrossRow(XWPFTable table, int rowNum, int colNum, int span) throws Exception {
//        try {
//            XWPFTableCell cell = table.getRow(rowNum).getCell(colNum);
//            if (cell.getCTTc().getTcPr() != null) {
//                cell.getCTTc().getTcPr().addNewGridSpan();
//                if (cell.getCTTc().getTcPr().getGridSpan() == null) {
//                    cell.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf((long) span));
//                }
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//
//    /**
//     * 设置单元格 格式与内容
//     * @param xDocument
//     * @param cell
//     * @param text
//     * @param bgcolor
//     * @param width
//     */
//    private void setCellText(XWPFDocument xDocument, XWPFTableCell cell, String text, String bgcolor, int width) {
//        CTTcPr cellPr = cell.getCTTc().addNewTcPr();
//        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
//        cell.setColor(bgcolor);
//        cell.setVerticalAlignment(XWPFVertAlign.CENTER);
//        cell.setText(text);
//    }
//
//    /**
//     * 检索出主键key相关的列
//     *
//     * @param columnsMap
//     * @return
//     */
//    private Map<String, LinkedHashMap<String, String>> keyColumns(HashMap<String, LinkedHashMap<String, String>> columnsMap) {
//        Map<String, LinkedHashMap<String, String>> keyColumnMap = new HashMap<String, LinkedHashMap<String, String>>();
//
//        Iterator<String> cloumnsNameIter = columnsMap.keySet().iterator();
//        while (cloumnsNameIter.hasNext()) {
//            String colum_name = cloumnsNameIter.next();
//            LinkedHashMap<String, String> columnsAtt = columnsMap.get(colum_name);
//            if (columnsAtt.get("column_key").equals("是")) {
//                keyColumnMap.put(colum_name, columnsAtt);
//                cloumnsNameIter.remove();
//            }
//        }
//
//        return keyColumnMap;
//    }
//
//
//
//
//    private int getCellWidth(int index) {
//        int cwidth = 1000;
//        if (index == 0) {
//            cwidth = 1600;
//        } else if (index == 1) {
//            cwidth = 3000;
//        } else if (index == 2) {
//            cwidth = 1200;
//        } else if (index == 3) {
//            cwidth = 900;
//        } else if (index == 4) {
//            cwidth = 600;
//        } else if (index == 5) {
//            cwidth = 600;
//        } else if (index == 6) {
//            cwidth = 700;
//        }
//        return cwidth;
//    }
//}
