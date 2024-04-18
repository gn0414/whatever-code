package cn.simon.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo01 {

    AtomicInteger atomicInteger = new AtomicInteger();

    public int getAtomicInteger(){
        return atomicInteger.get();
    }

    public void addAtomicInteger(){
        atomicInteger.getAndIncrement();
    }

}
