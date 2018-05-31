---
title:  深入 Java 平台 - 总览
p: java/deep-in-java
date: 2018-05-14 07:17:22
tags:
  - java
  - java平台
---

## 深入浅出 java

#### 对 java 平台的理解？

我们先从一个问题说起，这是一个开放的问题，不同人对 java 平台的理解也是不尽相同，可以从如下不同角度去思考：

- 重要特性之一：书写一次，到处运行
- 自动垃圾收集 GC，GC 的基本原理，如 SerialGC、Parallel GC、CMS、G1等
- JRE 与 JDK
- 对 Java 是否解释执行的理解，Java 编译期和运行时各自完成的事情是什么
- 剖析一下 Java 语言特性，如反射、注解、范型、Lambda 表达式、行为参数化等
- 基础类库的掌握程度，如集合、IO/NIO、网络、并发、安全等
- 对 JVM 的掌握及理解，如 JVM 的内存结构、JVM 的垃圾回收机制、JVM 的调优、JVM 在线上容易出现的问题、解决办法及分析过程
- Java 的类加载机制，如内置的 Bootstrap 加载机制、Application、Extension Class Loader，类加载的过程及每个过程的主要目的；如何自定义 class loader
- JDK 的工具，如编译器、运行时环境、安全工具、诊断工具、监控工具等
- Java 常用第三方类库，如Dubbo、Spark、Hadoop、ES、Maven、Spring生态、Guava、Apache commons、Jackson、Gson等
- JDK 8, JDK 9, JDK 10 的新特性

#### Exception 和 Error 有什么区别

- Java 平台的异常处理机制是如何设计的
- 在 Java 中异常处理机制的最佳实践
- 从性能角度看异常处理机制

#### final finally finalize 有什么不同

- 使用特性
- 从安全、性能、GC等方面分析他们
- 使用的注意事项

#### 强引用、软引用、弱引用、幻象引用的区别

- 理解原始数据类型和引用数据类型
- 不同的引用对对象可达性的影响，进而影响 GC
- 分析 JVM 中的引用情况以及引用队列

#### String StringBuffer StringBuilder 有什么区别

- 他们各自的应用场景
- 引申出基本的线程安全性设计与实现，基础编程实践
- JVM 对象缓存机制及如何良好使用
- String 相关类的演进

#### 动态代理的原理

- Java 反射机制
- 动态代理的原理，动态代理解决了什么问题，是否在实际的业务场景中使用到动态代理
- JDK 动态代理的设计和实现？
- Spring 中的动态代理，对AOP的理解

#### int 和 Integer 的区别

- 自动装箱/拆箱
- 分析一下缓存机制

#### Vector ArrayList LinkedList 有何区别

- JDK 集合框架
- 基础的排序算法
- JDK 中关于集合的类

#### JVM 问题排查，CPU使用过高
