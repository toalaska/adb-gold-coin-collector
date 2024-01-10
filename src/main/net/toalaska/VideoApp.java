package net.toalaska;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import net.toalaska.model.base.BaseModel;

import java.util.Set;
@Slf4j
public class VideoApp {
    public static void main(String[] args) throws  Exception {
        Set<Class<?>> classes = ClassUtil.scanPackage("net.toalaska.model.impl.video");

        while (true) {
            for (Class<?> aClass : classes) {
                if (isKip(aClass)) {
                    continue;
                }
                log.info("cls={}",aClass.getSimpleName());
//                BaseModel baseModel = (BaseModel) ReflectUtil.newInstance(aClass,"8CY4C19619006917");
//                BaseModel baseModel = (BaseModel) ReflectUtil.newInstance(aClass,"192.168.31.138:6666");
                BaseModel baseModel = (BaseModel) ReflectUtil.newInstance(aClass,"192.168.31.138:6666");
//                BaseModel baseModel = (BaseModel) ReflectUtil.newInstance(aClass,"100.68.197.91:6666");
                baseModel.run();
            }
        }

    }

    private static boolean isKip(Class<?> aClass) {
         //return false;
       return aClass.getSimpleName().contains("CheckTaskAction");
    }
}
