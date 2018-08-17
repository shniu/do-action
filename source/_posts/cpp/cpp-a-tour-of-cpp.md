---
title: C/C++ - A tour of c++
p: cpp/cpp-intro1
date: 2018-08-16 16:01:41
tags:
  - c/c++
---

> The first thing we do, let’s kill all the language lawyers.

## C++基础

#### 类型和变量

每一个名称和表达式都有一个类型，这个类型决定了能在它之上执行什么操作；变量声明表示向程序中引入一个名称，并且为这个名称实体指定一个类型：

- 类型定义了一组可能的值和对象上的一组操作
- 对象是一些持有某种类型的值的内存
- 值是根据类型解释的一组字节数据
- 变量是一个命名对象

C++ 中提供了各种基础类型，如：

```cpp
bool
int
double
char   // 1 个 byte，an 8-bits
unsigned
```
每个基本类型直接对应于硬件设施，并且具有固定大小，其确定可存储在其中的值的范围. 但是在不同平台下，每种类型的长度可能有所不同，可以使用sizeof来获取类型的长度。如：

```cpp
sizeof(char)  // 1
sizeof(int)   // 4
```
