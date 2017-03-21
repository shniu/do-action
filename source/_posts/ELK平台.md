---
title: ELK平台
date: 2017-03-17 09:54:21
tags:
  - elk
  - 集中式日志管理
---

elk是一款开源的集中式日志管理分析平台，并且拥有实时日志分析的能力；

### Logstash

回答第一个问题Logstash是什么？

{% blockquote %}
Logstash is an open source data collection engine with real-time pipelining capabilities. Logstash can dynamically unify data from disparate sources and normalize the data into destinations of your choice. Cleanse and democratize all your data for diverse advanced downstream analytics and visualization use cases.

上面这段话，已经很清楚的描述了Logstash的定位了，大概是说 Logstash是一款开源的实时管道数据收集引擎，它可以将分散在不同地方的数据动态实时的汇总在一起，然后把标准化后的数据放在你想放的人和地方；清洗和标准化所有你为下游的高阶应用提供的数据，如数据分析、可视化；


{% endblockquote %}

```shell
# 启动logstash
cd logstash-5.2.2
bin/logstash -f ~/logstash.conf
```

Logstash的配置文件

```
input { 
    stdin { } 
}

filter {

}

output {
  stdout { codec => rubydebug }
}
```


```shell
# 测试配置文件
bin/logstash -f first-pipeline.conf --config.test_and_exit

# 自动装载配置文件，会自动load
bin/logstash -f first-pipeline.conf --config.reload.automatic
```


#### Logstash是如何工作的

Logstash处理包括三个步骤：inputs -> filters -> outputs, inputs生产事件，filters处理它们，outputs把这些处理后的东西输出到任何其他地方；Inputs and outputs support codecs that enable you to encode or decode the data as it enters or exits the pipeline without having to use a separate filter.

- inputs

常用的一些inputs，如 `file`, `syslog`, `redis`, `beats`

- 
- 

#### 执行模型



[持久化队列](https://www.elastic.co/guide/en/logstash/current/persistent-queues.html) 属于优化层面的

#### Logstash的配置

Logstash有两类配置文件：处理流程管道配置文件和控制logstash启动关闭的配置文件；

[Logstash 的系统配置](https://www.elastic.co/guide/en/logstash/current/logstash-settings-file.html)

[Logstash的命令行](https://www.elastic.co/guide/en/logstash/current/running-logstash-command-line.html)

[作为服务运行](https://www.elastic.co/guide/en/logstash/current/running-logstash.html)

[Running logstash on Docker](https://www.elastic.co/guide/en/logstash/current/docker.html)

[Logstash logging](https://www.elastic.co/guide/en/logstash/current/logging.html#logging)

查看日志的级别信息
http://localhost:9600/_node/logging?pretty

[关闭Logstash](https://www.elastic.co/guide/en/logstash/current/shutdown.html)


[Logstash processing config file](https://www.elastic.co/guide/en/logstash/current/configuration-file-structure.html)

### Filebeat

```shell
./filebeat -e -c filebeat.yml -d "publish"
```


### Elasticsearch

{% blockquote %}

{% endblockquote %}

```shell
# start elasticsearch
cd elasticsearch-5.2.2
bin/elasticsearch
```



