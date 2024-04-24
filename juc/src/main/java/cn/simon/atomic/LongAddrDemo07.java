package cn.simon.atomic;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

class ClickNum{
    int num = 0;

    public synchronized void clickBySync(){
        num++;
    }

    AtomicLong atomicLong = new AtomicLong(0);
    public void clickByAtomicLong(){
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();

    public void clickByLongAdder(){
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator(Long::sum,0);

    public void clickByLongAcc(){
        longAccumulator.accumulate(1);
    }


}
public class LongAddrDemo07 {


    public static final int _1W = 10000;

    public static final int SIZE = 50;

    public static void main(String[] args) throws InterruptedException {

        ClickNum clickNum = new ClickNum();
        long startTime;
        long endTime;

        CountDownLatch countDownLatch1 = new CountDownLatch(SIZE);
        CountDownLatch countDownLatch2 = new CountDownLatch(SIZE);
        CountDownLatch countDownLatch3 = new CountDownLatch(SIZE);
        CountDownLatch countDownLatch4 = new CountDownLatch(SIZE);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            new Thread(()->{
                for (int j = 0; j < 100*_1W; j++) {
                    clickNum.clickBySync();
                }
                countDownLatch1.countDown();
            }).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();

        System.out.println("cost time "+(endTime - startTime) + " num is "+clickNum.num);


        startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            new Thread(()->{
                for (int j = 0; j < 100*_1W; j++) {
                    clickNum.clickByAtomicLong();
                }
                countDownLatch2.countDown();
            }).start();
        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();

        System.out.println("cost time "+(endTime - startTime) + " num is "+clickNum.atomicLong.get());

        startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            new Thread(()->{
                for (int j = 0; j < 100*_1W; j++) {
                    clickNum.clickByLongAdder();
                }
                countDownLatch3.countDown();
            }).start();
        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();

        System.out.println("cost time "+(endTime - startTime) + " num is "+clickNum.longAdder.sum());

        startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            new Thread(()->{
                for (int j = 0; j < 100*_1W; j++) {
                    clickNum.clickByLongAcc();
                }
                countDownLatch4.countDown();
            }).start();
        }
        countDownLatch4.await();
        endTime = System.currentTimeMillis();



        System.out.println("cost time "+(endTime - startTime) + " num is "+clickNum.longAccumulator.get());
    }
}
