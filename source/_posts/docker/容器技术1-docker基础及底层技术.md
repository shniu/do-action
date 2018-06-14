---
title: 容器技术1-docker基础及底层技术
p: docker/容器技术1-docker基础及底层技术
date: 2017-10-20 17:01:08
tags:
  - docker
---

### Docker 基础

#### 基础命令

```
# 启动一个容器
$ docker run -d -p 80:80 -v host_dir:container_dir --name web syph/web:1.0

# 进入docker容器内部
$ docker exec -it container_name /bin/bash

# 删除多个容器
$ sudo docker rm $(docker ps -a -q)
```

### QA

> Ignoring unsupported options: build in v1.13.0-rc4

[https://github.com/docker/docker/issues/29940](https://github.com/docker/docker/issues/29940)
[https://github.com/docker/docker/issues/29133#issuecomment-270901505](https://github.com/docker/docker/issues/29133#issuecomment-270901505)

```
The docker stack deploy feature, by design does not perform builds. 
Reason for this is that a build is performed on the host that the command is run from, so the image will only be available on that node. 
When deploying that image in a Swarm, the service cannot be started on other nodes. 
To deploy services, make sure the image you're deploying is pushed to a registry (or for testing; make sure the image is available on every node in the swarm)
```


> Inconsistent 'image' behavior on update  docker unable to pin image

[https://github.com/docker/docker/issues/29295](https://github.com/docker/docker/issues/29295)

```
有了这个warnning也没关系，可以正常跑服务
```

### Ref

- [Docker 底层技术](https://www.jianshu.com/p/7a1ce51a0eba)
- [https://docs.docker.com/engine/getstarted/step_four/](https://docs.docker.com/engine/getstarted/step_four/)
- [Docker技术架构详细分析 Docker模块分析](http://www.dockerinfo.net/2117.html)
- [Docker Registry HTTP API V2翻译](http://blog.leanote.com/post/dongkui0712@foxmail.com/api-v2)
- [使用官方 docker registry 搭建私有镜像仓库及部署 web ui](http://blog.csdn.net/mideagroup/article/details/52052618)
- [专家深入剖析Docker容器常见攻击手法与防护对策](http://www.dockerinfo.net/4471.html)
- [Docker for Devs — 如何将应用容器化](http://www.dockerinfo.net/4193.html)
- [Docker Swarm架构、特性与基本实践](http://www.dockerinfo.net/4374.html)
- [创建docker swarm应用](http://www.dockerinfo.net/4334.html)
- [https://docs.docker.com/engine/swarm/stack-deploy/](https://docs.docker.com/engine/swarm/stack-deploy/)这篇文章讲了部署到swarm的全过程
- [https://blog.couchbase.com/deploy-docker-compose-services-swarm/](https://blog.couchbase.com/deploy-docker-compose-services-swarm/)  Deploy Docker Compose Services to Swarm
- [https://technologyconversations.com/2017/01/23/using-docker-stack-and-compose-yaml-files-to-deploy-swarm-services/](https://technologyconversations.com/2017/01/23/using-docker-stack-and-compose-yaml-files-to-deploy-swarm-services/)
- [Docker从入门到实践](https://yeasy.gitbooks.io/docker_practice/content/compose/introduction.html)


### docker 技术

Container (容器)是一种轻量级的虚拟化技术，它不需要模拟硬件创建虚拟机。在 Linux 系统里面，使用到 Linux kernel 的 cgroups，namespace(ipc，network， user，pid，mount），capability 等用于隔离运行环境和资源限制的技术。

- [DOCKER基础技术：LINUX NAMESPACE](https://coolshell.cn/articles/17010.html)