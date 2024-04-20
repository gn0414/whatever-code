package cn.simon.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * 在大数据量的情况下
 * 我们想要实现一个商品点赞计数
 * 例如我们的直播过程中点击屏幕会一直计数的需求
 * 我们可以知道使用AtomicLong来实现这样的一个需求（保证了数据的准确性）
 * 但是我们的jdk8出来的原子增强类更有说法
 * 它在大数据情况下提供更大的吞吐量，缺点是可能会耗费更多空间
 *
 * 阿里开发嵩山版明确说明若是count++操作，如果是jdk8的版本，推荐使用LongAdder减少乐观锁尝试次数
 */
public class LongAddrDemo06 {

    //这里提及到了还有LongAccumulator这样一个类
    //区别很简单，LongAdder只是一个初始为0的累加器，只能做加法
    //而LongAccumulator不仅初始值可以自己设置，还可以做其他运算例如加减乘除

    public static void main(String[] args) {

        LongAdder longAdder = new LongAdder();

        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        long sum = longAdder.sum();
        System.out.println(sum);

        LongAccumulator longAccumulator = new LongAccumulator(Long::sum,1);

        longAccumulator.accumulate(1);//2

        longAccumulator.accumulate(3);//5

        System.out.println(longAccumulator.get());

    }
}
