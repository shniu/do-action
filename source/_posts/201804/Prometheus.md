---
title: Prometheus 监控系统
p: 201804/Prometheus
date: 2018-04-07 22:21:47
tags:
  - prometheus
  - 系统监控
---

## 介绍

Prometheus 是一个最初在 SoundCloud 上构建的开源系统监控和警报工具包。自2012年成立以来，许多公司和组织都采用 Prometheus，该项目拥有非常活跃的开发人员和用户社区。它现在是一个独立的开源项目，并且独立于任何公司。 为了强调这一点，并澄清项目的治理结构，Prometheus 于2016年加入云本地计算基金会，作为 Kubernetes 之后的第二个托管项目。

#### 特性

- 一个多维数据模型，具有由度量名称和键/值对标识的时间序列数据
- 一种灵活的查询语言来利用这种维度
- 不依赖分布式存储; 单个服务器节点是自治的
- 时间序列收集通过 HTTP 上拉式模型进行
- 推送时间序列通过中间网关支持
- 通过服务发现或静态配置来发现目标
- 多种模式的图形和仪表盘支持

#### 组件

- the main Prometheus server which scrapes and stores time series data
- client libraries for instrumenting application code
- a push gateway for supporting short-lived jobs
- special-purpose exporters for services like HAProxy, StatsD, Graphite, etc.
- an alertmanager to handle alerts
- various support tools

![prometheus](https://prometheus.io/assets/architecture.svg)

Prometheus 直接或通过中介推送网关从短期工作中提取仪器化工作的指标。它在本地存储所有抓取的样本，并对这些数据运行规则，以聚合和记录现有数据的新时间序列或生成警报。 Grafana 或其他 API 消费者可用于可视化收集的数据。

## 开始使用 Prometheus

Prometheus 是一个监控平台，通过在这些目标上抓取指标 HTTP 端点来收集受监控目标的指标。本指南将向您展示如何使用 Prometheus 安装，配置和监控我们的第一个资源。您将下载，安装并运行 Prometheus。您还将下载并安装导出器，这些工具可在主机和服务上显示时间序列数据。 我们的第一个出口商将是节点出口商，它公开了 CPU，内存和磁盘等主机级度量。

#### 下载

[下载地址](https://prometheus.io/download/)


## Grafana

下载安装 Grafana，[地址](http://docs.grafana.org/guides/getting_started/)

```shell

# http://docs.grafana.org/installation/debian/
# add deb https://packagecloud.io/grafana/stable/debian/ jessie main to /etc/apt/sources.list
deb https://packagecloud.io/grafana/stable/debian/ jessie main

curl https://packagecloud.io/gpg.key | sudo apt-key add -

sudo apt-get update
sudo apt-get install grafana

http://docs.grafana.org/installation/debian/

# 配置文件位置
/etc/grafana/grafana.ini

# 配置：http://docs.grafana.org/installation/configuration/

trusty
```

## 参考资料

- [基于Prometheus的分布式在线服务监控实践](https://zhuanlan.zhihu.com/p/24811652?refer=cloudsre)
- [为普罗米修斯添加Nginx基本认证](https://blog.csdn.net/sinkou/article/details/75303997)
- [SRE工程实践——基于时间序列存储数据的报警](http://blog.shurenyun.com/shurenyun-sre-256/)



