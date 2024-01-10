package net.toalaska.model.base;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
public abstract class PullLeftBaseModel extends BaseModel{

    public int sleepSec=1200;
    public int leftLength =500;
    public int times =100;

    public PullLeftBaseModel(String serial) throws  Exception {
        super(serial);
        ThreadUtil.safeSleep(2000);
    }



    public void run() throws Exception {
        start();
        postStart();
        pullLeft();
        stop();
    }

    public   void postStart() throws Exception{} ;

    public  void pullLeft() throws Exception {
        for (int i = 0; i < times; i++) {
            int millis = sleepSec + RandomUtil.randomInt(200, 500);
            ThreadUtil.safeSleep(millis);
            left();
            log.info("app={},i={},sleep={}ms",getClass().getSimpleName(),i,millis);
            //cap(anyDevice);
        }
    }

    private  InputStream left() throws Exception {
        int bottom = 1300+ RandomUtil.randomInt(0,80);
        int top = bottom + RandomUtil.randomInt(0,80);
        int x1 = 800+RandomUtil.randomInt(0,100);
        int x2 = x1 - leftLength +RandomUtil.randomInt(-100,100);
        return device.execute("input", "swipe", x1+"", bottom+"", x2+"", top +"");
    }

}
