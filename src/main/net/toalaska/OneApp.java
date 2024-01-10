package net.toalaska;

import net.toalaska.model.base.BaseModel;
import net.toalaska.model.impl.pull.PinDuoDuoPullUpModel;
import net.toalaska.model.impl.pull.UcBrowserPullLeftModel;
import net.toalaska.model.impl.pull.WuKongPullUpModel;
import net.toalaska.model.impl.video.TouTiaoViewVedioModel;
import net.toalaska.model.impl.video.YouShiViewVedioModel;

public class OneApp {
    public static void main(String[] args) throws  Exception {

//            BaseModel baseModel = new TouTiaoPullUpModel("8CY4C19619006917");
//            BaseModel baseModel = new BaiduPullUpModel("8CY4C19619006917");
//            BaseModel baseModel = new TouTiaoViewVedioModel("8CY4C19619006917");
//            BaseModel baseModel = new TouTiaoViewVedioModel("192.168.31.138:6666");
            BaseModel baseModel = new UcBrowserPullLeftModel("192.168.31.138:6666");
//            BaseModel baseModel = new TouTiaoViewVedioModel("192.168.31.138:6666");
//            BaseModel baseModel = new YouShiViewVedioModel("192.168.31.138:6666");
            baseModel.run();


    }
}
