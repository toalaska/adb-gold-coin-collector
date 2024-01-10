package net.toalaska.utils;

import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.socket.aio.IoAction;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
public class ImageUtil {

    public static String cut2(String imgFile, int x, int y, int x2, int y2) {
        String outFile = "d:/tmp/" + IdUtil.fastSimpleUUID() + ".png";
        Img.from(FileUtil.file(imgFile))
                .cut(new Rectangle(x, y, x2 - x, y2 - y))
                .write(FileUtil.file(outFile));
        return outFile;
    }

    public static String cut(String imgFile, String context, int x, int y, int x2, int y2) {

//        String outFile="d:/tmp/"+ IdUtil.fastSimpleUUID()+".png";
        String outFile = imgFile + "+" + context + ".png";
        ImgUtil.cut(
                FileUtil.file(imgFile),
                FileUtil.file(outFile),
                new Rectangle(x, y, x2 - x, y2 - y)//裁剪的矩形区域
        );

        return outFile;
    }
    private static byte[] cut(byte[] snapBytes, int x, int y, int x2, int y2) {
        ByteArrayOutputStream outputStream = StreamUtil.newOutputStream();
        ImgUtil.cut(IoUtil.toStream(snapBytes), outputStream,new Rectangle(x, y, x2 - x, y2 - y));
        return outputStream.toByteArray();
    }
    public static String cut(String imgFile, int x, int y, int x2, int y2) {
        return cut(imgFile, Thread.currentThread().getId() + "", x, y, x2, y2);

    }

    public static void main2(String[] args) throws Exception {
        String o = "[[[[[101.0, 61.0], [512.0, 61.0], [512.0, 103.0], [101.0, 103.0]], ('看视频再领63金币', 0.9961631298065186)]]]";
        String txt = ReUtil.get("\\('(.*?)'\\, 0\\.", o, 1);
        log.info("txt={}", txt);
    }

    public static void main(String[] args) throws Exception {
//        String txt=ocr("d:/tmp/3.png",232,1291,844,1427);
//        String txt=ocr("d:/tmp/2.png",416,2075,572,2229);
        boolean txt = ocrConntains("d:/tmp/2.png", "开宝箱", 416, 2075, 572, 2229);
        log.info("txt={}", txt);
    }

    public static boolean ocrConntains(byte[] snapBytes, String context, String keywords, int x, int y, int x2, int y2) throws Exception {
        String txt = ocrRaw(snapBytes, context,x, y, x2, y2);
        return contains(keywords, txt);
    }

    private static String ocrRaw(byte[] snapBytes, String context, int x, int y, int x2, int y2) {
        byte[] small = cut(snapBytes, x, y, x2, y2);
        return ocrByBytes(small,context);
    }

    public static String ocrByBytes(byte[] small,String context) {
//        FileUtil.writeBytes(small,"d:/tmp/small.png");
        long t0 = System.currentTimeMillis();
        HttpRequest request = HttpUtil.createPost("http://127.0.0.1:5000/ocr")
//                .form("fileType", "png")
                .form("context", context)
                .form("del", "0")
                .form("file",  small,"snap.png");
        HttpResponse response = request.execute();
        String body = response.body();
        log.info("response={},{}",response.getStatus(),body);
        log.info("识别耗时：{}",System.currentTimeMillis()-t0);
        return body;
    }


    public static boolean ocrConntains(String imgFile, String context, String keywords, int x, int y, int x2, int y2) throws Exception {
        String txt = ocrRaw(imgFile, context,x, y, x2, y2);
        return contains(keywords, txt);
    }

    private static boolean contains(String keywords, String txt) {
        if (keywords.contains("|")) {
            String[] arr = keywords.split("\\|");
            for (String key : arr) {
                //一个包含，返回true
                if (txt.contains(key)) {
                    return true;
                }
            }
            return false;
        }
        if (keywords.contains("&")) {
            String[] arr = keywords.split("&");
            for (String key : arr) {
                //一个不包含，返回falase
                if (!txt.contains(key)) {
                    return false;
                }
            }
            return true;
        }

        return txt.contains(keywords);
    }

    public static boolean ocrConntains(String imgFile, String keywords, int x, int y, int x2, int y2) throws Exception {
        return ocrConntains(imgFile,Thread.currentThread().getId()+"",keywords,x,y,x2,y2);
    }

    public static String ocr(String imgFile, int x, int y, int x2, int y2) throws Exception {
        String o = ocrRaw(imgFile, x, y, x2, y2);
        String txt = ReUtil.get("\\('(.*?)'\\, 0\\.", o, 1);
        log.info("txt={}", txt);
        return txt;
    }

    public static String ocrRaw(String imgFile, String context, int x, int y, int x2, int y2) throws Exception {

//        cut();
        String outFile = cut(imgFile, context,x, y, x2, y2);
        return ocrByFile(outFile);
    }

    private static String ocrByFile(String outFile) throws Exception {
        //paddleocr --image_dir ./img_phone/w.png --det false
//        String cmd = StrUtil.format("paddleocr --image_dir {} --det false",outFile);
        String cmd = StrUtil.format("python ocr.py {}", outFile);
//        FileUtil.del(outFile);
        log.info("{}", cmd);
        String out = CmdUtil.exec(cmd);
        String[] arr = out.split("\n");
        String o = (String) ArrayUtil.get(arr, -1);
        return o;
    }

    public static String ocrRaw(String imgFile, int x, int y, int x2, int y2) throws Exception {
        return ocrRaw(imgFile,Thread.currentThread().getId()+"",x,y,x2,y2);
    }
}
