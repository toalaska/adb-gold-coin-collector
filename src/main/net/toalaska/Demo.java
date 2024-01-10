package net.toalaska;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class Demo {
    //adb shell am start com.kuaishou.nebula
    //adb shell am com.ss.android.article.lite

    public static void main(String[] args) throws IOException, JadbException {
        JadbConnection jadb = new JadbConnection();
        JadbDevice anyDevice = jadb.getAnyDevice();
        //cap(anyDevice);
        while (true){
            start(anyDevice,"com.ss.android.article.lite/.activity.SplashActivity");
            read(anyDevice, 800,100);
            stop(anyDevice,"com.ss.android.article.lite");

            start(anyDevice,"com.kuaishou.nebula");
            read(anyDevice, 500,100);
            stop(anyDevice,"com.kuaishou.nebula");

        }

     }

    private static void read(JadbDevice anyDevice, int sleepSec, int num) throws IOException, JadbException {
        for (int i = 0; i < num; i++) {
            ThreadUtil.safeSleep(sleepSec +RandomUtil.randomInt(200,500));
            up(anyDevice, sleepSec);
            log.info("i={}",i);
            //cap(anyDevice);
        }
    }

    private static InputStream up(JadbDevice device, int length) throws IOException, JadbException {
        int bottom = 1666+ RandomUtil.randomInt(0,80);
        int top = bottom - length+ RandomUtil.randomInt(0,80);
        int x1 = 520+RandomUtil.randomInt(0,100);
        int x2 = x1+RandomUtil.randomInt(-100,100);
        return device.execute("input", "swipe", x1+"", bottom+"", x2+"", top +"");
    }

    /**
     * adb shell screencap -p /sdcard/screenshot.png
     * adb pull /sdcard/screenshot.png c:/temp/s.png
     *
     * @return
     */
    public static String cap(JadbDevice device){
        String file = "/sdcard/screenshot.png";
        try {
            device.executeShell("screencap", "-p", file);
        }catch (Exception e){
            log.info("截图失败");
            return null;
        }
        try {
            String localFile = "d:/tmp/s.png";
            Runtime.getRuntime().exec(StrUtil.format("adb pull {} {}",file,localFile));
//            device.execute("pull", file, localFile);
        }catch (Exception e){
            log.info("拉取失败");
            return null;
        }
        log.info("截图成功");
        return "ok";
    }
    //pm list packages -3
    public static List<String> pkg(JadbDevice device) throws IOException, JadbException {
        InputStream inputStream = device.execute("pm" ,"list", "packages", "-3");
        String out = IoUtil.readUtf8(inputStream);
        log.info("pkg={}",out);
        return null;
    }
    //adb shell am start com.kuaishou.nebula
    //adb shell am start com.ss.android.article.lite
    public static void start(JadbDevice device,String app) throws IOException, JadbException {
        device.execute("am" ,"start", app);
    }
    //adb shell am force-stop
    public static void stop(JadbDevice device,String app) throws IOException, JadbException {
        device.execute("am" ,"force-stop", app);
    }
}
