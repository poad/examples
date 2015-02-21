package com.github.poad.java_examples.rx;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import rx.Observable;

public class RxTest {

    @Test
    public static void test() {
        Observable
        .just(100, 200, 100, 300, 100)
        .concatMap(ms -> Observable.just(ms).delay(ms, TimeUnit.MILLISECONDS))
        .timeInterval()
        .toBlocking()
        .forEach(n -> System.out.println(n));
    }
}
