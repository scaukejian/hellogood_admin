package com.hellogood.http.task;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 消息通知线程池
 * Created by kejian on 2017/9/30.
 */
public class NoticeExecutor {

    private static volatile Executor executor = null;

    private NoticeExecutor(){

    }

    public static Executor getExecutor(){
        if(executor == null){
            synchronized (NoticeExecutor.class){
                if (executor == null){
                    executor = Executors.newFixedThreadPool(10);
                }
            }
        }
        return executor;
    }
}
