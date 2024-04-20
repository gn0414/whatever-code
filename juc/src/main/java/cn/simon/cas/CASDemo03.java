package cn.simon.cas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用演示demo
 *
 */


@Getter
@ToString
@AllArgsConstructor
class User{
    String userName;

    int age;
}

public class CASDemo03 {
    public static void main(String[] args) {
        AtomicReference<User> user = new AtomicReference<>();

        User z3 = new User("张三",19);

        User l4 = new User("李四",18);

        user.set(z3);

        System.out.println(user.compareAndSet(z3, l4));

        System.out.println(user.compareAndSet(z3, l4));

        System.out.println(user.get().toString());
    }
}
