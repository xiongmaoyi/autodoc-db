package com.g12.autodoc.iotest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.EmptyFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentsIoTest {

    @Test
    public void contextLoads() throws FileNotFoundException {
        FileUtils fileUtils = new FileUtils();

        long len = FileUtils.sizeOfDirectory(new File(""));


    }


    public void listFiles() {
        //DirectoryFileFilter.INSTANCE为子目录也扫描，mptyFileFilter.NOT_EMPTY为过滤掉空文件
        Collection<File> files = FileUtils.listFiles(new File(""),
                EmptyFileFilter.NOT_EMPTY, DirectoryFileFilter.INSTANCE);

    }


    public void listFiles2() {
        //DirectoryFileFilter.INSTANCE为子目录也扫描，mptyFileFilter.NOT_EMPTY为过滤掉空文件
        Collection<File> files = FileUtils.listFiles(new File(""),
                FileFilterUtils.andFileFilter(EmptyFileFilter.NOT_EMPTY,
                        new SuffixFileFilter("txt")), DirectoryFileFilter.INSTANCE);

    }


    /**
     * 读取所有
     *
     * @throws IOException
     */
    public void readAllContent() throws IOException {
        //读取为字符串
        String msg = FileUtils.readFileToString(new File(""), "UTF-8");
        //读取为字节数组
        byte[] datas = FileUtils.readFileToByteArray(new File(""));
    }


    /**
     * 逐行读取
     *
     * @throws IOException
     */
    public void readLineContent() throws IOException {
        //读取为字符串
        List<String> lines = FileUtils.readLines(new File(""), "UTF-8");
        for (String line : lines) {
            log.info(line);
        }

    }


    public void write() throws IOException {
        File file = new File("");


        //写入字节码到文件
        FileUtils.writeByteArrayToFile(new File(""), "data".getBytes("utf-8"));
        //字符串写入到文件
        FileUtils.writeStringToFile(new File(""), "datas", "utf-8");


        List<String> lines = new ArrayList<>();
        lines.add("line1");
        lines.add("line2");
        FileUtils.writeLines(file, lines, ",");


    }

    public void copy() throws IOException {
        //复制文件，可重命名
        FileUtils.copyFile(new File(""),new File(""));
        //复制文件到目录
        FileUtils.copyFileToDirectory(new File(""),new File(""));

        //复制目录，可重命名
        FileUtils.copyDirectory(new File(""),new File(""));
        //复制目录到目录
        FileUtils.copyFileToDirectory(new File(""),new File(""));

    }


    public void  ioUtils() throws MalformedURLException {

    }


}
