package cn.simon.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicArrayIntegerDemo02 {

    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]);

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
        }

        int tmpInt = 0;

        tmpInt = atomicIntegerArray.getAndSet(0,1122);

        System.out.println("tmpInt "+tmpInt +" res "+atomicIntegerArray.get(0));

        tmpInt = atomicIntegerArray.getAndIncrement(0);

        System.out.println("tmpInt "+tmpInt+" res "+atomicIntegerArray.get(0));
    }
}
