package net.toalaska.model.impl.pull;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import net.toalaska.model.base.PullUpBaseModel;
import se.vidstige.jadb.JadbException;

import java.io.IOException;
@Slf4j
public class TouTiaoPullUpModel extends PullUpBaseModel {

    public TouTiaoPullUpModel(String serial) throws  Exception {
        super(serial);
        this.sleepSec=2000;
        this.times=60;
        this.startPkgName="com.ss.android.article.lite/.activity.SplashActivity";
    }


}
