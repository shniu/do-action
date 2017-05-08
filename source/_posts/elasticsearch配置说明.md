---
title: elasticsearch配置说明
date: 2017-03-30 15:47:48
tags:
    - elk
    - elasticsearch
---

{% blockquote %}
持续运行的服务，需要我们持续不断的监控，需要对日志的实时分析
{% endblockquote %}

elasticsearch.yml 是elasticsearch的重要配置文件

```yml
# 配置es的集群名称，默认是elasticsearch，es会自动发现在同一网段下的es，如果在同一网段下有多个集群，就可以用这个属性来区分不同的集群
cluster.name: elasticsearch

# 节点名，默认随机指定一个name列表中名字，该列表在es的jar包中config文件夹里name.txt文件中，其中有很多作者添加的有趣名字
node.name: demo

# 
```


