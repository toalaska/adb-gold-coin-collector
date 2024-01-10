package net.toalaska.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtil {

    public static ByteArrayOutputStream newOutputStream(){
        return  new ByteArrayOutputStream(1024);
    }

    public static InputStream toInputStream(ByteArrayOutputStream outputStream){
       return new ByteArrayInputStream(outputStream.toByteArray());
    }

    /***
     * 获取 rescoures下的文件流
     * @param fileName  如templates/blank.docx
     * @return
     */
    public static InputStream getResource(String fileName){
       return  ResourceUtil.getStream("classpath:"+fileName);
    }


}
