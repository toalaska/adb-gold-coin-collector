package net.toalaska.model.base;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import net.toalaska.utils.CmdUtil;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
@Slf4j
public abstract class BaseModel {
    public String startPkgName;
    public String getStopPkgName(){
        return startPkgName.split("/")[0];
    }
    public JadbDevice device;

    public BaseModel(String serial) throws  Exception {
        log.info("初始化{}",this.getClass().getSimpleName());
        JadbConnection jadb = new JadbConnection();
        List<JadbDevice> devices = jadb.getDevices();
        log.info("设备列表：{}",devices);
        for (JadbDevice item : devices) {
            if (item.getSerial().equals(serial)) {
                device=item;
            }
        }
        if(device==null){
            log.error("手机未连接，serial={}",serial);
        }
     }

    public abstract void run() throws Exception;
    public    List<JadbDevice> getDevices() throws  Exception {
         Runtime.getRuntime().exec("adb start-server");

         JadbConnection jadb = new JadbConnection();
         return jadb.getDevices();
     }
    public  String capRaw( ){
        String localFile = "d:/tmp/" + IdUtil.fastSimpleUUID() + ".png";
        try {
            InputStream screencap = device.executeShell("screencap", "-p");
//            byte[] bytes = IoUtil.readBytes(screencap);
//            byte[] newBytes=new byte[bytes.length];
//            int i=0;
//            for (byte b : bytes) {
//                if(b == '\r'){
//                    continue;
//                }
//                newBytes[i]=b;
//                i++;
//            }
            FileUtil.writeFromStream(screencap, localFile);
        }catch (Exception e){
            log.error("截图失败={}",e);
        }
        return localFile;
    }

    /**
     * adb shell screencap -p /sdcard/screenshot.png
     * adb pull /sdcard/screenshot.png c:/temp/s.png
     *
     * @return
     */
    public  String cap( ){
        String localFile="d:/tmp/"+ IdUtil.fastSimpleUUID()+".png";
        cap(localFile);
        return localFile;
    }
    public  void cap(String localFile){
        String file = "/sdcard/screenshot.png";
        try {
            device.executeShell("screencap", "-p", file);
        }catch (Exception e){
            log.info("截图失败");
            return ;
        }
        boolean fileOk = checkFileOk(file);
        if(!fileOk){
            log.info("检查文件大小失败");
            return;
        }
        try {

            String cmd = StrUtil.format("adb -s {} pull {} {}", device.getSerial(),file, localFile);
            CmdUtil.exec(cmd);

        }catch (Exception e){
            log.info("拉取失败");
            return ;
        }
        log.info("截图成功");
        ThreadUtil.safeSleep(500);

    }

    private boolean checkFileOk(String file) {
        HashMap<String, Integer> count = new HashMap<>();
        for (int i = 0; i < 30; i++) {
            InputStream fileInfoInstream = null;
            try {
                // ls -al screenshot.png|awk '{print $5}'
                fileInfoInstream = device.executeShell("ls", "-al", file);
            } catch ( Exception e) {
                log.info("获取文件信息失败");
            }
            String fileInfo = IoUtil.readUtf8(fileInfoInstream);
            String fileSize = fileInfo.split(" ")[4];
            if (!count.containsKey(fileSize)) {
                count.put(fileSize,0);
            }
            Integer newVal = count.get(fileSize)+1;
            count.put(fileSize,newVal);
            log.info("file={}", fileSize);
            if(newVal>=5){
                return true;
            }
            ThreadUtil.safeSleep(100);
        }
        return false;
    }

    //pm list packages -3
    public List<String> listPkgs() throws IOException, JadbException {
        InputStream inputStream = device.execute("pm" ,"list", "packages", "-3");
        String out = IoUtil.readUtf8(inputStream);
        log.info("pkg={}",out);
        return null;
    }
    //adb shell am start com.kuaishou.nebula
    //adb shell am start com.ss.android.article.lite
    public  void start() throws  Exception {
        device.execute("am" ,"start", startPkgName);
    }
    //adb shell am force-stop
    public  void stop() throws  Exception {
        String stopPkgName = getStopPkgName();
        log.info("stop-app {}",stopPkgName);
        device.execute("am" ,"force-stop", stopPkgName);
    }
    public void tap(int x,int y)throws  Exception{
        log.info("tap,{},{}",x,y);
        device.execute("input","tap",x+"",y+"");
    }
}
