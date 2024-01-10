package net.toalaska.model.base;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Slf4j
public abstract class PullUpBaseModel extends BaseModel{

    public int sleepSec=1200;
    public int upLength =800;
    public int times =100;

    public PullUpBaseModel(String serial) throws  Exception {
        super(serial);
        ThreadUtil.safeSleep(2000);
    }



    public void run() throws Exception {
        start();
        postStart();
        pullUp();
        stop();
    }

    private  void pullUp() throws Exception {
        for (int i = 0; i < times; i++) {
            int millis = sleepSec + RandomUtil.randomInt(200, 500);
            ThreadUtil.safeSleep(millis);
            up();
            log.info("app={},i={},sleep={}ms",getClass().getSimpleName(),i,millis);
            //cap(anyDevice);
        }
    }

    private  InputStream up() throws Exception {
        int bottom = 1666+ RandomUtil.randomInt(0,80);
        int top = bottom - upLength+ RandomUtil.randomInt(0,80);
        int x1 = 520+RandomUtil.randomInt(0,100);
        int x2 = x1+RandomUtil.randomInt(-100,100);
        return device.execute("input", "swipe", x1+"", bottom+"", x2+"", top +"");
    }

    protected   void postStart() throws Exception{

    }
}
