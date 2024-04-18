package cn.simon.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo02 {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5,2022)+" change after value"+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5,2022)+" change after value"+atomicInteger.get());

    }
}
