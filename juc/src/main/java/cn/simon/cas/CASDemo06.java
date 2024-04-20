package cn.simon.cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题Demo
 * 演示ABA发生过程
 * 及其解决过程
 */
public class CASDemo06 {
    static AtomicInteger atomicInteger = new AtomicInteger(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);
    public static void main(String[] args) {

//        Thread a = new Thread(()->{
//            System.out.println(atomicInteger.compareAndSet(100, 101));
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(atomicInteger.compareAndSet(101, 100));
//        });
//        Thread b = new Thread(()->{
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            if (atomicInteger.compareAndSet(100, 101)){
//                System.out.println("发生了ABA问题且修改成功数据为" + atomicInteger.get());
//            }
//        });
//        a.start();
//        b.start();


        Thread a = new Thread(()->{
            try {
                Thread.sleep(10);
                //让b线程拿到初始版本号
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //开始aba
            System.out.println(atomicStampedReference.compareAndSet(100, 101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(atomicStampedReference.compareAndSet(101, 100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1));
        });
        Thread b = new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (atomicStampedReference.compareAndSet(100, 101,stamp,stamp+1)){
                System.out.println("发生了ABA问题且修改成功数据为" + atomicInteger.get());
            }else{
                System.out.println("ABA问题被杜绝");
            }
        });
        a.start();
        b.start();
    }
}
