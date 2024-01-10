package net.toalaska.model.impl.pull;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import net.toalaska.model.base.PullUpBaseModel;
@Slf4j
public class PinDuoDuoPullUpModel extends PullUpBaseModel {
    public PinDuoDuoPullUpModel(String serial) throws  Exception {
        super(serial);
        this.times=60;

        this.startPkgName="com.xunmeng.pinduoduo/com.xunmeng.pinduoduo.ui.activity.MainFrameActivity";

    }

    @Override
    protected void postStart() throws Exception {
        log.info("等待15s");
        ThreadUtil.safeSleep(15_000);
        log.info("切换到视频");
        tap(354,2154);
        ThreadUtil.safeSleep(3_000);
    }


}
