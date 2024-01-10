package net.toalaska.model.impl.pull;

import net.toalaska.model.base.PullUpBaseModel;

public class BaiduPullUpModel extends PullUpBaseModel {
    public BaiduPullUpModel(String serial) throws  Exception {
        super(serial);
        this.times=60;
        this.sleepSec=3000;

        this.startPkgName="com.baidu.searchbox.lite/com.baidu.searchbox.MainActivity";

    }


}
