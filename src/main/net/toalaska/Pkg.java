package net.toalaska;

import lombok.extern.slf4j.Slf4j;
import net.toalaska.model.impl.pull.TouTiaoPullUpModel;

import java.util.List;
@Slf4j
public class Pkg {
    public static void main(String[] args) throws Exception {
//        List<JadbDevice> devices = BaseModel.getDevices();
//        for (JadbDevice device : devices) {
//            log.info("device={}",device);
//        }
        TouTiaoPullUpModel model = new TouTiaoPullUpModel("8CY4C19619006917");
        List<String> strings = model.listPkgs();
    }
}
