package cn.simon.atomic;


import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

class MyVar{
    public volatile Boolean isInit = false;

    AtomicReferenceFieldUpdater<MyVar,Boolean> referenceFieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(MyVar.class, Boolean.class,"isInit");

    public void init() throws InterruptedException {
        if (referenceFieldUpdater.compareAndSet(this,Boolean.FALSE,Boolean.TRUE)) {
            System.out.println(Thread.currentThread().getName() + " start init ... after 3s");

            Thread.sleep(3000);

            System.out.println(Thread.currentThread().getName() + " init over ");
        }
    }
}
public class AtomicReferenceFieldUploadDemo05 {

    public static void main(String[] args) {
        MyVar myVar = new MyVar();
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {
                    myVar.init();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            },String.valueOf(i)).start();
        }
    }
}
