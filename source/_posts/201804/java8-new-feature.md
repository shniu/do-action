---
title: Java 8
p: 201804/java8-new-feature
date: 2018-04-13 12:55:53
tags:
  - java
  - java8
---

## java 8 带来的变革


## 行为参数化


## Lambda 表达式

## 行为参数化、lambda、方法引用的例子

```java
package com.digcredit.blockchain.rmladmin.ground;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by shniu on 2018/4/13.
 */
public class Java8NewFeature {

    public static void main(String[] args) {
        List<String> str = Arrays.asList("bbb", "abc", "ccc");
        System.out.println(str);
        // 方法 1 传递代码
        str.sort(new MyComparator());
        System.out.println(str);

        // 方法2 匿名类
        List<String> str2 = Arrays.asList("bbb", "abc", "ccc");
        System.out.println(str2);
        str2.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println(str2);

        // 方法3 使用 lambda
        List<String> str3 = Arrays.asList("bbb", "abc", "ccc");
        System.out.println(str3);
        str3.sort((String s1, String s2) -> s1.compareTo(s2));
        // or
        str3.sort((s1, s2) -> s1.compareTo(s2));
        // or
        str3.sort(Comparator.comparing((s) -> s));
        System.out.println(str3);

        // 方法4 方法引用
        List<String> str4 = Arrays.asList("bbb", "abc", "ccc");
        System.out.println(str4);
        str4.sort(Comparator.comparing(String::toString));
        System.out.println(str4);
    }

}

class MyComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
}

```


## Ref

- java 8 in action


