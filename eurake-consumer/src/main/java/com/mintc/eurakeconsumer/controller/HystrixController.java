package com.mintc.eurakeconsumer.controller;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SuppressWarnings("ALL")
@RestController
@Configuration
public class HystrixController {

    @Autowired
    private RestTemplate restTemplate;

    public static class HystrixText extends HystrixCommand<String>{
        protected HystrixText() {
            super(HystrixCommandGroupKey.Factory.asKey("myGroup"));
        }

        @Override
        protected String run() throws Exception {
            Thread.sleep(5000);
            System.out.println("执行线程名称："+Thread.currentThread().getName());
//            int i = 1 / 0; //模拟异常
            return "模拟异常";
        }

        @Override
        protected String getFallback() {
            return "熔断回退策略";
        }
    }

    public static class HystrixObservable extends HystrixObservableCommand<String> {

        private final String name;

        public HystrixObservable(String name) {
            super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
            this.name = name;
        }

        @Override
        protected Observable<String> construct() {
            return Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    try {
                        System.out.println("当前线程名称："+Thread.currentThread().getName());
                        if(!subscriber.isUnsubscribed()) {
                            subscriber.onNext("Hello");
                            int i = 1 / 0; //模拟异常
                            subscriber.onNext(name + "!");
                            subscriber.onCompleted();
                        }
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }
            }).subscribeOn(Schedulers.io());
        }

        @Override
        protected Observable<String> resumeWithFallback() {
            return Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    try {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext("失败了！");
                            subscriber.onNext("找大神来排查一下吧！");
                            subscriber.onCompleted();
                        }
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }
            }).subscribeOn(Schedulers.io());
        }
    }

    @GetMapping(value = "/router")
    @ResponseBody
    public String router(){
        return restTemplate.getForObject("http://eureka-provider/search/1", String.class);
    }

    @com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand(fallbackMethod = "errorMethod")
    public String getUserId(String name) {
        int i = 1/0; //此处抛异常，测试服务降级
        return "你好:" + name;
    }

    public String errorMethod(String name) {
        return "errorMethod";
    }


    @Test
    public void testObservable() {
        System.out.println("调用程序线程名称："+Thread.currentThread().getName());
        Observable<String> observable= new HystrixObservable("Word").observe();

        Iterator<String> iterator = observable.toBlocking().getIterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testToObservable() {
        System.out.println("执行前线程数："+Thread.activeCount());
        Observable<String> observable= new HystrixObservable("World").observe();
        Iterator<String> iterator = observable.toBlocking().getIterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testHystrixCommand() throws ExecutionException, InterruptedException {
        System.out.println("调用程序线程名称："+Thread.currentThread().getName());
        HystrixText hystrixText = new HystrixText();
//        System.out.println(hystrixText.execute());
        Future<String> future = hystrixText.queue();
        System.out.println("result:"+future.get());
//        System.out.println("result:"+hystrixText.observe());
//        System.out.println("result:"+hystrixText.toObservable());
    }

    public static void main(String[] args) {
    }
}
