package net.toalaska.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CmdUtil {
    public static String exec(String cmd) throws Exception {
        log.info(cmd);
        Process exec = Runtime.getRuntime().exec(StrUtil.format(cmd));
        String out = IoUtil.read(exec.getInputStream(), "gbk");
        String err = IoUtil.read(exec.getErrorStream(), "gbk");
        log.info("out={}",out);
        log.info("err={}",err);
        return err+"\n\n"+out;
    }
}
