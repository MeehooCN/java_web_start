package com.meehoo.biz.core.basic.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * 多线程并发工具
 * @author zc
 * @date 2020-07-21
 */
@Component
public class ExecutorManager {
//    private static final ExecutorManager executorManager = new ExecutorManager();
    private Executor executor;
    private static final int SleepTime = 2000;

    public ExecutorManager() {
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

//    @Bean
//    public ExecutorManager getExecutorManager(){
//        return executorManager;
//    }

    public void exeFuture(FutureTask task){
        executor.execute(task);
    }

    public <T,V> List<V> batchFunction(List<T> source, Function<T,V> consumer){
        List<FutureTask<V>> taskList = new ArrayList<>(source.size());
        for (T t : source) {
            Callable<V> callable = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return consumer.apply(t);
                }
            };
            FutureTask<V> task = new FutureTask<>(callable);
            taskList.add(task);
            exeFuture(task);
        }

        List<V> resultList = new ArrayList<>(source.size());
        for (FutureTask<V> future : taskList) {
            try {
                resultList.add(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    public <T,V> List<V> batchFunctionWithAbandon(List<T> source, Function<T,V> consumer){
//        long nowMilis = System.currentTimeMillis();

        List<FutureTask<V>> taskList = new ArrayList<>(source.size());
        for (T t : source) {
            Callable<V> callable = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return consumer.apply(t);
                }
            };
            FutureTask<V> task = new FutureTask<>(callable);
            taskList.add(task);
            exeFuture(task);
        }

        List<V> resultList = new ArrayList<>(source.size());
        List<FutureTask<V>> undoneList = new ArrayList<>();
        for (FutureTask<V> future : taskList) {
            try {
                if(future.isDone()){
                    V e = future.get();
                    resultList.add(e);
                }else{
                    undoneList.add(future);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (resultList.size()<source.size()){
            try {
                Thread.sleep(SleepTime);

                for (FutureTask<V> future : undoneList) {
                    if(future.isDone()){
                        V e = future.get();
                        resultList.add(e);
                    }else{
                        future.cancel(true);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

//    public <T> List<T> batchExeFuture(List<Callable<T>> callableList){
//        List<FutureTask<T>> collect = callableList.stream().map(e -> new FutureTask<>(e)).collect(Collectors.toList());
//        for (FutureTask<T> task : collect) {
//            exeFuture(task);
//        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        List<T>
//    }
}
