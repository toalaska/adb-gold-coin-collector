package net.toalaska.model.impl.pull;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import net.toalaska.model.base.PullUpBaseModel;
@Slf4j
public class DouYinPullUpModel  extends PullUpBaseModel {
    public DouYinPullUpModel(String serial) throws  Exception {
        super(serial);
        this.sleepSec=4000;
        this.times=60;
        this.startPkgName="com.ss.android.ugc.aweme.lite/com.ss.android.ugc.aweme.splash.SplashActivity";
        ThreadUtil.safeSleep(2000);
    }
}
