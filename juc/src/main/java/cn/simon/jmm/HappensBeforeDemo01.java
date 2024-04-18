package cn.simon.jmm;

import sun.misc.Unsafe;

/**
 * 我们可以从这个简单的例子里面分析happens-before原则
 * 我们下面的代码不符合happensbefore中
 * 次序规则（两个线程）
 * 锁定规则（未加同步锁）
 * volatile规则（未加volatile）
 * 传递规则（两个线程）
 * 后续均为线程和对象规则无关，不加累述
 * 解决方法
 * 同步锁(能解决但是读加锁性能太差)
 * volatile + 锁（符合使用场景，较优的方案）
 * volatile保证读操作的可见性（也就是写了会知道），写操作加锁保证原子性
 */

//    public synchronized int getValue(){
//        return value;
//    }
//
//    public synchronized int setValue(){
//        return ++value;
//    }

public class HappensBeforeDemo01 {
    private volatile int value = 0;

    public int getValue(){
        return value;
    }

    public synchronized int setValue(){
        return ++value;
    }

}
