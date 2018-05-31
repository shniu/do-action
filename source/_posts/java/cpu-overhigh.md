---
title: Java 线上应用 CPU 使用率过高导致 502 504
p: java/cpu-overhigh
date: 2018-05-28 07:14:46
tags:
  - java
---

### Java 应用 CPU 使用率过高分析

- 使用 Top 查看哪个进程的 CPU 使用率过高, 进程中哪个线程使用率高

```
$ top
$ ps aux | grep <PID>
$ top -H -p PID命令查看哪个线程占用高

// 查找进程下哪个线程占用率高
$ ps -mp PID -o THREAD,tid,time
// or
$ ps -mp 2633 -o THREAD,tid,time | sort -rn

// 线程 id 转换成 16 进制
$ printf "%X\n" thread_id

// 或者使用 jps 来查看 java 的进程 id
$ jps
```

top 命令中需要关注的指标：

1. load average：此值反映了任务队列的平均长度；如果此值超过了CPU数量，则表示当前CPU数量不足以处理任务，负载过高
2. %us：用户CPU时间百分比；如果此值过高，可能是代码中存在死循环、或是频繁GC等
3. %sy：系统CPU时间百分比；如果此值过高，可能是系统线程竞争激烈，上下文切换过多，应当减少线程数
4. %wa：等待输入、输出CPU时间百分比；如果此值过高，说明系统IO速度过慢，CPU大部分时间都在等待IO完成
5. %hi：硬件中断CPU百分比；当硬件中断发生时，CPU会优先去处理硬件中断；比如，网卡接收数据会产生硬件中断
6. swap used：被使用的swap；此值过高代表系统因为内存不足在进行频繁的换入、换出操作，这样会影响效率，应增大内存量
7. %CPU：进程使用CPU的百分比；此值高表示CPU在进行无阻塞运算等

- 导出栈信息

```
$ jstack <pid> | grep <线程id的16进制表示> -A 30

// 导出栈信息
$ jstack <pid> | jstack.log
```

### Ref

- [线上java程序CPU占用过高问题排查](https://blog.csdn.net/u010862794/article/details/78020231?locationNum=4&fps=1)

```
# top
PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND        6764 root      20   0 2428m 1.1g  11m S 190.0 28.3  36:38.55 java 
// pid为6764的java进程CPU利用持续占用过高,达到了190%。内存占用率为28.3%

# ps -mp 6764 -o THREAD,tid,time
USER     %CPU PRI SCNT WCHAN  USER SYSTEM   TID     TIME
root     71.7   -    - -         -      -     - 00:36:52
root      0.0  19    - futex_    -      -  6764 00:00:00
root      0.0  19    - poll_s    -      -  6765 00:00:01
root     44.6  19    - futex_    -      -  6766 00:23:32
root     44.6  19    - futex_    -      -  6767 00:23:32
root      1.2  19    - futex_    -      -  6768 00:00:38
// 6766和6767两个线程占用CPU大约有半个小时，每个线程的CPU利用率约为45%

# printf "%x\n" 6766
1a6e
// 转16进制

# jstack 6764 | grep 1a6e
"GC task thread#0 (ParallelGC)" prio=10 tid=0x00007ffeb8016800 nid=0x1a6e runnable
"GC task thread#0 (ParallelGC)" prio=10 tid=0x00007ffeb8016800 nid=0x1a6e runnable 
"GC task thread#1 (ParallelGC)" prio=10 tid=0x00007ffeb8016800 nid=0x1a6e runnable  
"VM Periodic Task Thread" prio=10 tid=0x00007ffeb8016800 nid=0x3700 waiting on condition 
// jstack命令打印线程堆栈信息, 这些都是GC的线程, 很有可能就是内存不够导致GC不断执行

# jstat -gcutil 6764 2000 10
// 可以看出内存的年轻代和年老带的利用率都达到了惊人的100%。FGC的次数也特别多，并且在不断飙升。可以推断出 
程序肯定是在哪里的实现有问题，需要重点查看大对象或者异常多的对象信息。

# jmap -dump:format=b,file=dump.bin 6764
// 使用jmap命令导出heapdump文件，然后拿到本地使用jvisualvm.exe分析

# jstack -l 6764 >> jstack.out

// 从 heatmap 中去分析具体是程序的哪个地方导致内存占用过多，GC时间过长，CPU占用过高
```
