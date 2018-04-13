---
title: 监控平台
p: 201804/monitor-platform
date: 2018-04-09 09:55:31
tags:
  - Prometheus
  - 监控平台
---

## Prometheus

- [Prometheus 实战](https://songjiayang.gitbooks.io/prometheus/content/)

- [使用 Prometheus 监控 Docker 容器](https://segmentfault.com/a/1190000002527178) 


## 主机监控指标

这里的主机值对物理机或虚拟机的监控，下面是一些重要的监控指标：

- 系统平均负载监控项

对 load average 的理解参看 [理解Linux系统中的load average](http://blog.51cto.com/8088809/1623552)

> 通常我们先看15分钟load，如果load很高，再看1分钟和5分钟负载，查看是否有下降趋势。1分钟负载值 > 1，那么我们不用担心，但是如果15分钟负载都超过1，我们要赶紧看看发生了什么事情。所以我们要根据实际情况查看这三个值。
> 0.7 < load < 1: 此时是不错的状态，如果进来更多的汽车，你的马路仍然可以应付。
load = 1: 你的马路即将拥堵，而且没有更多的资源额外的任务，赶紧看看发生了什么吧。
load > 5: 非常严重拥堵，我们的马路非常繁忙，每辆车都无法很快的运行

**使用 Prometheus 统计平均负载**
```shell
node_load1{instance="xxx"} // 1分钟负载
node_load5{instance="xxx"} // 5分钟负载
node_load15{instance="xxx"} // 15分钟负载
```

## Spring boot and prometheus

- [Exposing Metrics of a Spring Boot Application for Prometheus](https://reflectoring.io/monitoring-spring-boot-with-prometheus/)
- [自定义Metrics：让Prometheus监控你的应用程序（Spring版)](http://yunlzheng.github.io/2018/01/24/use-prometheus-monitor-your-spring-boot-application/)

## Docker hosts and containers monitoring

- [Docker hosts and containers monitoring with Prometheus, Grafana, cAdvisor, NodeExporter and AlertManager](https://github.com/stefanprodan/dockprom)
使用起来简单方便，使用及参考价值很大

## Mongodb monitor

- [A Prometheus exporter for MongoDB including sharding, replication and storage engines](https://github.com/percona/mongodb_exporter)

