package cn.simon.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * 前面我们已经演示过了
 * AtomicReference 和 AtomicStampedReference
 *
 * 接下来就只演示AtomicMarkableReference
 * 这个类带有标记位，只要有动过就会有标记
 * 干的是一次性的问题
 */
public class AtomicReferenceDemo03 {

    static AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference(100,false);
    public static void main(String[] args) {
        new Thread(()->{
            boolean isUse = atomicMarkableReference.isMarked();

            System.out.println("默认标识 " + isUse);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            atomicMarkableReference.compareAndSet(100,1000,isUse,!isUse);
        },"t1").start();

        new Thread(()->{
            boolean isUse = atomicMarkableReference.isMarked();

            System.out.println("默认标识 " + isUse);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //无法将marked从true改为false
            System.out.println(atomicMarkableReference.isMarked());
            boolean b = atomicMarkableReference.compareAndSet(100, 2000, true, false);

            if (!b) System.out.println("t2线程修改失败");

            System.out.println(atomicMarkableReference.isMarked());

            System.out.println(atomicMarkableReference.getReference());
        },"t2").start();
    }
}
