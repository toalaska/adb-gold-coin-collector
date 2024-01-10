package net.toalaska.model.impl.pull;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import net.toalaska.model.base.PullLeftBaseModel;
@Slf4j
public class UcBrowserPullLeftModel extends PullLeftBaseModel {
    public UcBrowserPullLeftModel(String serial) throws  Exception {
        super(serial);
        this.times=100;
        this.sleepSec=1000;
        this.startPkgName="com.ucmobile.lite/com.uc.browser.InnerUCMobile";
    }

    @Override
    public void run() throws Exception {
        start();
        pullLeft();

    }
//    protected void postStart() throws Exception {
//        log.info("等待15s");
//        ThreadUtil.safeSleep(15_000);
//        tap(349,2135);
//        ThreadUtil.safeSleep(5000);
//        tap(464,1648);
//        ThreadUtil.safeSleep(2000);
//    }


}
