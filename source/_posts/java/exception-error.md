---
title: 异常处理机制
p: java/exception-error
date: 2018-05-31 07:24:15
tags:
  - java
  - 异常处理机制

---

### Java 中的异常处理机制

在 Java 中，Exception 和 Error 都继承自 Throwable，而只有 Throwable 类型的实例才可以被抛出或者捕获，它是异常处理机制的基本组成类型。

Exception 和 Error 提现了 Java 设计者对不同异常情况的分类，Exception 是程序正常运行中，可以预料的意外情况，可能并且应该被捕获，进行相应处理。

Error 指在正常情况下，不大可能出现的情况，Error 会导致程序（如 JVM）处于非正常状态，不可恢复。所以无需捕获，如 `OutOfMemoryError`  之类。

Exception 分为可检查异常和不检查异常，可检查异常在代码里必须显示的进行捕获处理，是编译器检查的一部分；不检查异常是所谓的运行时异常，类似 `NullPointerException`, `ArrayIndexOutOfBoundsException`等，通常是可以编码避免的逻辑错误，是否捕获，不会在编译期强求。

#### Throwable Exception Error 的设计和分类

首先，需要理解 Java 中已有的异常类型和异常体系

![java exception 体系](/images/java-exception1.jpeg)

> java.lang.NoClassDefFoundError 和 java.lang.ClassNotFoundException
> - JVM在编译的时候能找到调用方法或静态变量所在的类，但在运行的时候找不到此类而引发的错误。Thrown if the Java Virtual Machine or a ClassLoader instance tries to load in the definition of a class (as part of a normal method call or as part of creating a new instance using the new expression) and no definition of the class could be found. The searched-for class definition existed when the currently executing class was compiled, but the definition can no longer be found. 出现这个异常，并不是说这个.class类文件不存在，而是类加载器试图加载类的定义时却找不到这个类的定义，实际上.class文件是存在的。
> - Ref: https://blog.csdn.net/u013065023/article/details/71171373

#### Java 中 操作 Trrowable 的元素和实践