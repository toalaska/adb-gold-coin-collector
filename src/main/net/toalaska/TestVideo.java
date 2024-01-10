package net.toalaska;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import net.toalaska.model.base.TestModel;
import net.toalaska.utils.ImageUtil;

@Slf4j
public class TestVideo {



    public static void main(String[] args) throws Exception {

//        TestModel model = new TestModel("8CY4C19619006917");
        TestModel model = new TestModel("192.168.31.138:6666");
        //任务
//        ImageUtil.ocr(model.cap(),350, 2100, 570, 2222);
        //右下宝箱
//        ImageUtil.ocr(model.cap(),732, 1800, 1028, 2048);
        //中间
//        ImageUtil.ocr(model.cap(),220, 1300, 840, 1430);
        String snapFile = "d:/tmp/snap.png";
        model.cap(snapFile);
        byte[] snapBytes = FileUtil.readBytes(snapFile);

//        boolean res = ImageUtil.ocrConntains(snapBytes, "test", "开宝箱", 416, 2075, 572, 2229);
        boolean res = ImageUtil.ocrConntains(snapBytes, "test", "领取成功",
                590, 0, 1090, 200);
        log.info("res={}",res);

    }
}
