package cn.simon.atomic;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;


/**
 * 想要使用原子字段修改类型的FieldUpdate操作
 * 就要将变量修饰为public和volatile的
 *
 */
class BookAccount{
    String name = "CCB";
    public volatile int money = 0;

}

public class AtomicFieldUpdateDemo04 {

    public static void main(String[] args) throws InterruptedException {
        BookAccount bookAccount = new BookAccount();
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                boolean isSuccess;
                do {
                    AtomicIntegerFieldUpdater<BookAccount> accountAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BookAccount.class, "money");
                    int money = accountAtomicIntegerFieldUpdater.get(bookAccount);
                    isSuccess = accountAtomicIntegerFieldUpdater.compareAndSet(bookAccount, money, money + 1000);
                    if (isSuccess)countDownLatch.countDown();
                }while (!isSuccess);
            }).start();
        }
        countDownLatch.await();
        System.out.println(bookAccount.money);
    }
}
