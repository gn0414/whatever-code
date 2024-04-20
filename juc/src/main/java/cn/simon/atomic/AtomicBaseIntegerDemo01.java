package cn.simon.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子类基本数据类型演示demo
 *
 */

class MyNumber{
    AtomicInteger integer = new AtomicInteger(0);

    public void addPLus(){
        integer.getAndIncrement();
    }
}
public class AtomicBaseIntegerDemo01 {
    public static final int SIZE = 50;

    public static final CountDownLatch COUNT = new CountDownLatch(50);

    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();
        for (int i = 0; i < SIZE; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < 1000; j++) {
                        myNumber.addPLus();
                    }
                }finally {
                    COUNT.countDown();
                }
            }).start();
        }

        COUNT.await();

        System.out.println("increment result "+myNumber.integer.get());
    }
}
