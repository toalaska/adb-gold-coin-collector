package net.toalaska.model.impl.video;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.toalaska.model.action.CheckAction;
import net.toalaska.model.base.BaseModel;
import net.toalaska.model.base.ViewVedioBaseModel;
import net.toalaska.utils.ImageUtil;
import net.toalaska.utils.MultiTask;

@Slf4j
public class YouShiViewVedioModel extends ViewVedioBaseModel {


    public YouShiViewVedioModel(String serial) throws Exception {
        super(serial);
        this.sleepSec = 1000;
        this.times = 60;
        this.startPkgName = "com.ss.android.article.search/com.ss.android.article.news.activity.MainActivity";
    }



    public void run() throws Exception {
        start();

        for (int i = 0; i < 20; i++) {



            try {
//                String snapFile = "d:/tmp/snap"+ IdUtil.fastSimpleUUID() +".png";
                String snapFile = "d:/tmp/snap.png";
                cap(snapFile);
                byte[] snapBytes = FileUtil.readBytes(snapFile);
                MultiTask multiTask = new MultiTask();
                multiTask.add(



                        new TouTiaoViewVedioModel.CheckTaskAction(this, snapBytes, "右下宝箱", "开宝箱",
                                758, 1744, 1030, 2030,
                                920, 1896,
                                1, 1000),

  new CheckTaskAction(this, snapBytes, "继续观看", "继续观看",
         220, 1200, 840, 1430,
                                540, 1240,
                                1, 1000),

                        new CheckTaskAction(this, snapBytes, "中间点播放视频_1", "看视频再&金币",
                                240, 1300, 844, 1455,
                                550, 1370,
                                2, 1000),


                        new CheckTaskAction(this, snapBytes, "中间点播放视频_2", "看视频再",
                                240, 1170, 844, 1317,
                                550, 1252,
                                2, 1000),
                        new CheckTaskAction(this, snapBytes, "中间点播放视频_3", "看视频再",
                                240, 1293, 861, 1444,
                                570, 1282,
                                2, 1000),

                        new CheckTaskAction(this, snapBytes, "继续观看", "继续观看",
                                290, 1202, 816, 1309,
                                500, 1250,
                                2, 1000),

                new CheckTaskAction(this, snapBytes, "点击右上领取成功", "领取成功",
                        590, 0, 1090, 200,
                        888, 128,
                        3, 1000),
                        new CheckTaskAction(this, snapBytes, "检查tab中任务", "任务",
                                713, 2087, 830, 2235,
                                770, 2116,
                                1, 1000)
                );
                //adb shell input keyevent 4：发送模拟的返回键事件。
//                new CheckTaskAction(this, snapBytes, "点击右上领取成功", "领取成功",
//                        590, 0, 1090, 200,
//                        888, 128,
//                        3, 1000)
//                );
                ThreadUtil.safeSleep(1000);
            } catch(Exception e){
                log.error("err={}",e);
            }

        }
        stop();


    }

    @AllArgsConstructor
    @Data
    public static class CheckTaskAction  extends CheckAction {
        private BaseModel baseModel;
        private byte[] snapBytes;
        private String name="检查tab中任务";
        private String keywords="开宝箱";
        private int x1=350;
        private int y1=2091;
        private int x2=572;
        private int y2=2091;

        private int tabX=470;
        private int tabY=2170;
        private int times=10;
        private int waitMs=1000;

@Override
        public boolean run() throws Exception {

            for (int i = 0; i < times; i++) {
                boolean ok = ImageUtil.ocrConntains(snapBytes,name, keywords, x1, y1, x2, y2);
                log.info("检查{}，结果={}，i={}",name,ok,i);
                if(ok){
                    return true;
                }
                ThreadUtil.safeSleep(waitMs);
            }
           log.info("{}执行失败",name);
            return false;
        }
        @Override
        public    void onSuccess() throws Exception {
            this.baseModel.tap(tabX,tabY);
        }
    }


}
