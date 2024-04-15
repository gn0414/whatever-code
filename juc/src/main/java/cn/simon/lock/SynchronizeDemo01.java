package cn.simon.lock;


/**
 * @author simon
 * 我简单的提及以下我们的掌握要点
 * 第一点
 * 首先明确锁对象
 * 那么什么是锁对象呢,简单来说我们的synchronize锁对象
 * 其实就是我们可能锁的是实例对象或者是类对象
 * 我们清楚我们的每一个类其实都是一个类对象
 * 第二点
 * 锁的范围
 * 能锁区块就不锁方法，能锁对象就不锁类
 * 第三点
 * 静态方法锁的是类对象
 * 成员方法锁的是实例对象
 *
 */
public class SynchronizeDemo01 {
    static class Phone{
        public synchronized void sendEmail(){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("send email");
        }

        public synchronized void sendPhone(){
            System.out.println("send phone");
        }

        public static synchronized void sendStaticEmail(){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("send email");
        }

        public static synchronized void sendStaticPhone(){
            System.out.println("send phone");
        }

    }
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone.sendStaticEmail();
        phone.sendPhone();
    }
}
