package net.toalaska.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadUtil {

    private static ExecutorService executorService=Executors.newFixedThreadPool(20);;

    public static ExecutorService get(){
        return executorService;
    }
}
