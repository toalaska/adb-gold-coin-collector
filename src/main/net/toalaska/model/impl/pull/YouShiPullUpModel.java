package net.toalaska.model.impl.pull;

import net.toalaska.model.base.PullUpBaseModel;

public class YouShiPullUpModel extends PullUpBaseModel {
    public YouShiPullUpModel(String serial) throws  Exception {
        super(serial);
        this.times=60;

        this.startPkgName="com.ss.android.article.search/com.ss.android.article.news.activity.MainActivity";

    }


}
