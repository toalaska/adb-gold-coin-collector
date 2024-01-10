package net.toalaska.model.impl.pull;

import net.toalaska.model.base.PullUpBaseModel;

public class KuaiShouPullUpModel extends PullUpBaseModel {
    public KuaiShouPullUpModel(String serial) throws  Exception {
        super(serial);
        this.startPkgName="com.kuaishou.nebula";
        this.sleepSec=3000;
        this.times =60;

    }


}
