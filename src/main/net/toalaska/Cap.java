package net.toalaska;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import net.toalaska.model.base.BaseModel;
import net.toalaska.model.base.TestModel;
import net.toalaska.model.impl.pull.TouTiaoPullUpModel;
import net.toalaska.utils.ImageUtil;

import java.util.List;

@Slf4j
public class Cap {



    public static void main(String[] args) throws Exception {

//        TestModel model = new TestModel("192.168.31.138:6666");
        TestModel model = new TestModel("100.68.197.91:6666");

        String snapFile = "d:/tmp/snap.png";
        model.cap(snapFile);


    }
}
