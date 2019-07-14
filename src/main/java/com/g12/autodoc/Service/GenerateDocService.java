package com.g12.autodoc.Service;

import com.g12.autodoc.bean.MetaTable;
import com.g12.autodoc.module.TablesGetter;
import com.g12.autodoc.templet.WordDocTemplet;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;


import java.util.List;

/**
 * @Author sjx
 * @Classname GenerateDocService
 * @Description TODO
 * @Date 2019/7/14 2:21
 */
@Slf4j
@Service
public class GenerateDocService {




    public void excute(String path,String dbType, String host, String port, String database, String user, String pwd,List<String> tableNames,String docTemplet) throws Exception {

        long startTime = System.currentTimeMillis();

        Class c = Class.forName("com.g12.autodoc.templet.impl."+docTemplet);
        WordDocTemplet templet = (WordDocTemplet)c.newInstance();


        List<MetaTable> tables = TablesGetter.getInstance(dbType,host,port,database,user,pwd)
                .getTables(tableNames);

        XWPFDocument xwpfDocument = templet.getXWPDocument(tables);
        //文件写到系统
        FileOutputStream fos = new FileOutputStream(path + "_doc.docx");
        xwpfDocument.write(fos);
        fos.close();

        long endTime = System.currentTimeMillis();
        log.warn("文档生成完毕，总共用时:{} ms" , (endTime - startTime));

    }


}
