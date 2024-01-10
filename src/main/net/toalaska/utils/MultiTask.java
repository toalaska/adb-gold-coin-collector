package net.toalaska.utils;

import lombok.extern.slf4j.Slf4j;
import net.toalaska.model.action.CheckAction;
import net.toalaska.model.impl.video.TouTiaoViewVedioModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class MultiTask {
    private boolean finish = false;
    private ExecutorService executorService;
    private int taskLength;

    public void add(CheckAction... checkTaskActions) {
        for (CheckAction checkTaskAction: checkTaskActions) {


            boolean success = false;
            try {
                success = checkTaskAction.run();
            } catch (Exception e) {


            }
            if (success) {
                try {
                    checkTaskAction.onSuccess();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            }

        }
    }

    public void add2(TouTiaoViewVedioModel.CheckTaskAction... checkTaskActions) {
        taskLength = checkTaskActions.length;
        executorService = MyThreadUtil.get();
        for (TouTiaoViewVedioModel.CheckTaskAction checkTaskAction : checkTaskActions) {
            executorService.submit(() -> {
                try {
                    boolean success = checkTaskAction.run();
                    if (success) {
                        finishTask(checkTaskAction);
                    }
                } catch (Exception e) {
                    log.info("执行异常，{},{}", checkTaskAction.getName(), e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
        }

    }

    public void run() {
    }

    private synchronized void finishTask(TouTiaoViewVedioModel.CheckTaskAction checkTaskAction) {
        if (finish) {
            log.info("已经有任务执行完了，{}来迟了", checkTaskAction.getName());
            return;
        }
        finish = true;
        log.info("第一个执行完，{}", checkTaskAction.getName());
        try {
            //返回尚未开始执行的任务列表：此方法会返回一个列表，该列表包含那些尚未开始执行的任务。如果所有任务都已开始执行，则返回一个空列表。
            List<Runnable> runnables = executorService.shutdownNow();
            log.info("强行关闭：{}", taskLength - runnables.size());
        } catch (Exception e) {

        }
        try {
            checkTaskAction.onSuccess();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
