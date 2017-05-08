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

#### input filter

##### grok

grok用来解析任意的文本，然后生成结构化的数据；

### Filebeat

```shell
./filebeat -e -c filebeat.yml -d "publish"
```


### Elasticsearch

{% blockquote %}

{% endblockquote %}

#### Elasticsearch架构

[Elasticsearch架构](http://www.tuicool.com/articles/ruQBB3E)

- elasticsearch中的服务发现

在分布式系统中要解决的第一个问题就是节点之间互相发现及选主的过程，像Zookeeper，就是解决了这个问题；elasticsearch是一个分布式的弹性伸缩的全文搜索及分析引擎，但es的分布式节点服务发现使用了自身的ZenDiscovery，大致过程如下：

>节点启动时先ping节点，ping是一个rpc的命令，如果discovery.zen.ping.unicast.hosts中设置了值，则ping设置中的主机，否则ping localhost；
ping返回的response，会包含被ping主机的节点信息以及被ping认为的master节点；
选举开始，先从各节点认为的master中选举一个做master，按照字母顺序选；
如果没有认为的master存在，就从所有节点中选一个，选的方式也是按照字母顺序，但是如果设置了discovery.zen.minimum_master_nodes，那就必须节点总数达到最小值的限制才能开始选举，否则一直等待；
最后肯定能选个master出来，如果只有一个节点，就选自己；
如果当前节点是master，等待节点个数大于minimum_master_nodes，然后就对外提供服务；
如果当前节点不是master，尝试加入master

- 弹性伸缩的elastic

体现在如下两个方面：
1 服务发现机制让节点很容易加入和退出
2 富的设置以及allocation API

Elasticsearch 节点启动的时候只需要配置discovery.zen.ping.unicast.hosts，这里不需要列举集群中所有的节点，只要知道其中一个即可。当然为了避免重启集群时正好配置的节点挂掉，最好多配置几个节点。节点退出时只需要调用 API 将该节点从集群中排除，系统会自动迁移该节点上的数据，然后关闭该节点即可。当然最好也将不可用的已知节点从其他节点的配置中去除，避免下次启动时出错。

分片（Shard）以及副本（Replica） 分布式存储系统为了解决单机容量以及容灾的问题，都需要有分片以及副本机制。Elasticsearch 没有采用节点级别的主从复制，而是基于分片；

- 
- 

```
# 查看es集群的健康状态
curl -i -X GET "http://192.168.1.100:9200/_cat/health?v" -u elastic:changeme

# 查看es集群的节点信息
curl -i -X GET "http://192.168.1.100:9200/_cat/nodes?v" -u elastic:changeme

# 查看es集群中所有的index
curl -i -X GET "http://192.168.1.100:9200/_cat/indices?v" -u elastic:changeme

curl -i -X GET "http://192.168.1.100:9200/logstash-decision-engine/_search?pretty" -u elastic:changeme -d '{"query":{"match_all":{}}, "size": 1}'

curl -i -X GET "http://192.168.1.100:9200/logstash-decision-engine/_search?pretty" -u elastic:changeme -d '{"query":{"match_all":{}}, "from": 10, "size": 10}'

curl -i -X GET "http://192.168.1.100:9200/logstash-decision-engine/_search?pretty" -u elastic:changeme -d '{"query":{"match_all":{}}, "from": 10, "size": 10, "sort": {"@timestamp": {"order": "desc"}}}'

curl -i -X GET "http://192.168.1.100:9200/logstash-decision-engine/_search?pretty" -u elastic:changeme -d '{"query":{"match":{"@timestamp": "2017-03-31T03:36:13.993Z"}}}'

# 返回所有包含failure的记录
curl -i -X GET "http://192.168.1.100:9200/logstash-decision-engine/_search?pretty" -u elastic:changeme -d '{"query":{"match":{"message": "failure"}}}'

# bool query, must  should   must_not
curl -i -X GET "http://192.168.1.100:9200/logstash-decision-engine/_search?pretty" -u elastic:changeme -d '{"query":{"bool": {"should": [{"match": {"message": "failure"}}]}}}'

# filters

# Aggregations
```


```shell
# start elasticsearch
cd elasticsearch-5.2.2
bin/elasticsearch
```


#### Elasticsearch Mapping

Mapping定义了type中的诸多字段的数据类型以及这些字段如何被Elasticsearch处理，比如一个字段是否可以查询以及如何分词等。默认情况不需要显式的定义mapping， 当新的type或者field引入时，Elasticsearch会自动创建并且注册有合理的默认值的mapping(毫无性能压力)， 只有要覆盖默认值时才必须要提供mapping定义。

- Mapping datatypes

string: text  keyword
numberic types: long, integer, short, byte, double, float
date types: date
boolean types: boolean
binary types: binary
range types: integer_range, float_range, long_range, double_range, date_range
array type
object type: a single json object
nested type: for arrays of JSON objects



