package com.g12.autodoc.templet;

import com.g12.autodoc.bean.MetaTable;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * @Author sjx
 * @Classname WordDocTemplet
 * @Description  文档模板抽象规范
 * @Date 2019/7/8 21:56
 */
public abstract class WordDocTemplet {








   public abstract XWPFDocument getXWPDocument(List<MetaTable> tables) throws Exception;



   /**
    * 增加自定义标题样式。这里用的是stackoverflow的源码
    *
    * @param docxDocument 目标文档
    * @param strStyleId 样式名称
    * @param headingLevel 样式级别
    */
   public static void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) {

      CTStyle ctStyle = CTStyle.Factory.newInstance();
      ctStyle.setStyleId(strStyleId);

      CTString styleName = CTString.Factory.newInstance();
      styleName.setVal(strStyleId);
      ctStyle.setName(styleName);

      CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
      indentNumber.setVal(BigInteger.valueOf(headingLevel));

      // lower number > style is more prominent in the formats bar
      ctStyle.setUiPriority(indentNumber);

      CTOnOff onoffnull = CTOnOff.Factory.newInstance();
      ctStyle.setUnhideWhenUsed(onoffnull);

      // style shows up in the formats bar
      ctStyle.setQFormat(onoffnull);

      // style defines a heading of the given level
      CTPPr ppr = CTPPr.Factory.newInstance();
      ppr.setOutlineLvl(indentNumber);
      ctStyle.setPPr(ppr);

      XWPFStyle style = new XWPFStyle(ctStyle);

      // is a null op if already defined
      XWPFStyles styles = docxDocument.createStyles();

      style.setType(STStyleType.PARAGRAPH);
      styles.addStyle(style);

   }
}
