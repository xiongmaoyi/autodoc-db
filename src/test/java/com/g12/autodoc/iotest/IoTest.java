package com.g12.autodoc.iotest;

import lombok.extern.slf4j.Slf4j;
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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IoTest {

    @Test
    public void contextLoads() throws IOException {


        //选择FileInputstream作为工具，读取数据s
        InputStream is = new FileInputStream(new File("E:/test-data/新建文本文档括弧四.txt"));


        //read() 方法返回字节代号，读到文本结尾会返回-1
        while(is.read()!=-1){

        }



    }




    @Test
    public void getText() throws IOException {
        log.info(File.pathSeparator);
        log.warn(File.separator);

        String path = "E:/test-data";
        String aFilePath = "E:/test-data/新建文本文档括弧四.txt";
        File dataPathFile = new File(path);


        //目录/文件名
        String fileName = dataPathFile.getName();
        long fileFreeSpace = dataPathFile.getFreeSpace();
        //file 构建时的路径
        String filePath = dataPathFile.getPath();
        String absolutePath = dataPathFile.getAbsolutePath();
        String parentPath = dataPathFile.getPath();

        //相当于linux下的pwd
        log.info(System.getProperty("user.dir"));
        //用户主目录
        log.info(System.getProperty("user.home"));
        log.info(System.getProperty("os.name"));

        //(new File("E:/test-data/aaa/bbb/ddd.txt"));











        //目录内的文件名,以下三个方法是针对目录的，文件的话会返回null
        String[] fileNames = dataPathFile.list();
        //目录内的文件
        File[] files = dataPathFile.listFiles();
        File[] someFiles = dataPathFile.listFiles();

        log.info("list():{}",fileNames.toString());
        //文件大小（字节数），目录的话会返回0
        log.info(Long.toString(dataPathFile.length()));

        log.info("-------------------------------");
        printFilePathRecursion(dataPathFile,"");

        getFileLen(dataPathFile);
        log.info("fileLen:{} 字节",fileLen);
    }


    /**
     * 判断文件状态
     * @param file
     */
    public void fileStateJudge(File file){
        //判断状态
        if(file.exists()){

            if(file.isFile()){
                //文件
            }else if(file.isDirectory()){
                //目录
            }

        }else {
            log.error("不存在");
        }
    }


    /**
     * 递归打印
     * @param file
     */
    public void printFilePathRecursion(File file,String deep){
        //判断状态
        if(file.exists()){
            if(file.isFile()){
                log.warn(deep+file.getName());
                //文件
            }else if(file.isDirectory()){
                log.warn(deep+file.getName());
                deep+="--- ";
                //目录
                File[] files = file.listFiles();
                for(File thisFile : files){
                    printFilePathRecursion(thisFile,deep);
                }
            }
        }else {
            log.error("不存在");
        }

    }



    private long fileLen = 0;

    /**
     * 递归获取文件夹大小
     * @param file
     */
    public void getFileLen(File file){
        //判断状态
        if(file.exists()){

            if(file.isFile()){
                fileLen += file.length();
                //文件

            }else if(file.isDirectory()){
                //目录
                for(File thisFile:file.listFiles()){
                    getFileLen(thisFile);
                }
            }
        }else {
            log.error("不存在");
        }

    }

    /**
     * 文件操作
     * @param file
     * @throws IOException
     */
    public void createFile(File file) throws IOException {
        //创建文件，已有文件则抛出异常
        file.createNewFile();
        //直接删除文件，不管该文件是否存在，一经调用立即执行
        file.delete();
        //创建目录，上级目录不必须存在
        file.mkdirs();




    }






}
