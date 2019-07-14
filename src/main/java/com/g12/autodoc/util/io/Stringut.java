package com.g12.autodoc.util.io;

import java.io.File;

/**
 * @Author sjx
 * @Classname Stringut
 * @Description TODO
 * @Date 2019/7/7 17:21
 */
public class Stringut {


    public void judgeOs(){
        String os = System.getProperty("os.name");
        String basePath;

        // 判断操作系统是否为Windows
        if(os.startsWith("win")){
            basePath = "E:/fileUpload";
        } else {
            basePath = "/var/www/jifen";
        }
        // 将字符串中的/转换为系统路径分隔符
        basePath = basePath.replace("/" , File.separator);

    }



}
