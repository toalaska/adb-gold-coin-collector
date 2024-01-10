package net.toalaska.model.impl.pull;

import net.toalaska.model.base.PullUpBaseModel;

public class WuKongPullUpModel extends PullUpBaseModel {
    public WuKongPullUpModel(String serial) throws  Exception {
        super(serial);
        this.times=60;

        this.startPkgName="com.cat.readall";

    }


}
