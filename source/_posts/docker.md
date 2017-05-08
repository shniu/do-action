---
title: docker in action
date: 2017-04-06 17:59:52
tags:
---

### Docker 入门

```
# 启动一个容器
docker run -d -p 80:80 -v host_dir:container_dir --name web syph/web:1.0

# 进入docker容器内部
docker exec -it container_name /bin/bash
```



### Question to be answer 

- image 在swarm cluster中共享的问题

- 配置文件动态配置到image中，并在每次更新配置文件只需要重启container即可，而不需要每次都build新的image


### Basic operator

[https://docs.docker.com/engine/getstarted/step_four/](https://docs.docker.com/engine/getstarted/step_four/)

[Docker技术架构详细分析 Docker模块分析](http://www.dockerinfo.net/2117.html)

[Docker Registry HTTP API V2翻译](http://blog.leanote.com/post/dongkui0712@foxmail.com/api-v2)

[使用官方 docker registry 搭建私有镜像仓库及部署 web ui](http://blog.csdn.net/mideagroup/article/details/52052618)

[专家深入剖析Docker容器常见攻击手法与防护对策](http://www.dockerinfo.net/4471.html)

[Docker for Devs — 如何将应用容器化](http://www.dockerinfo.net/4193.html)

[Docker Swarm架构、特性与基本实践](http://www.dockerinfo.net/4374.html)

[创建docker swarm应用](http://www.dockerinfo.net/4334.html)


### Deploy a stack to a swarm


[https://docs.docker.com/engine/swarm/stack-deploy/](https://docs.docker.com/engine/swarm/stack-deploy/)这篇文章讲了部署到swarm的全过程

[https://blog.couchbase.com/deploy-docker-compose-services-swarm/](https://blog.couchbase.com/deploy-docker-compose-services-swarm/)  Deploy Docker Compose Services to Swarm

[https://technologyconversations.com/2017/01/23/using-docker-stack-and-compose-yaml-files-to-deploy-swarm-services/](https://technologyconversations.com/2017/01/23/using-docker-stack-and-compose-yaml-files-to-deploy-swarm-services/)


### QA

>Ignoring unsupported options: build in v1.13.0-rc4

[https://github.com/docker/docker/issues/29940](https://github.com/docker/docker/issues/29940)
[https://github.com/docker/docker/issues/29133#issuecomment-270901505](https://github.com/docker/docker/issues/29133#issuecomment-270901505)

```
The docker stack deploy feature, by design does not perform builds. 
Reason for this is that a build is performed on the host that the command is run from, so the image will only be available on that node. 
When deploying that image in a Swarm, the service cannot be started on other nodes. 
To deploy services, make sure the image you're deploying is pushed to a registry (or for testing; make sure the image is available on every node in the swarm)
```


>Inconsistent 'image' behavior on update  docker unable to pin image

[https://github.com/docker/docker/issues/29295](https://github.com/docker/docker/issues/29295)

```
有了这个warnning也没关系，可以正常跑服务
```
