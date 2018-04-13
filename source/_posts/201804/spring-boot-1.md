---
title: Spring boot 系列 - 入门
p: 201804/spring-boot-1
date: 2018-04-09 15:05:48
tags:
  - spring
---

{% blockquote %}
将书越读越薄，将知识越学越广阔
{% endblockquote %}

{% fullimage /images/spring-boot1.jpg, my-heaven, spring-boot %}

Spring Boot(英文中是“引导”的意思)，是用来简化Spring应用的搭建到开发的过程。应用开箱即用，只要通过 “just run”（可能是 java -jar 或 tomcat 或 maven插件run 或 shell脚本），就可以启动项目。二者，Spring Boot 只要很少的Spring配置文件（例如那些xml，property）。 因为“习惯优先于配置”的原则，使得Spring Boot在快速开发应用和微服务架构实践中得到广泛应用。


## 快速入门

- [Spring Boot 之 HelloWorld详解](http://www.spring4all.com/article/266)
spring boot 入门级的使用介绍
- [Spring Boot 配置文件详解：自定义属性、随机数、多环境配置等](http://www.spring4all.com/article/248)
- [Spring Boot快速入门](http://blog.didispace.com/spring-boot-learning-1/)
- [Java 8 简明教程](http://blog.didispace.com/books/java8-tutorial/ch1.html)
- [Thinking in Java (Java 编程思想)](http://blog.didispace.com/books/think-in-java/)
- [Spring-Boot-Reference-Guide](http://blog.didispace.com/books/spring-boot-reference/)
- [spring-framework-4-reference](http://blog.didispace.com/books/spring-framework-4-reference/)
- [Spring MVC 4.2.4.RELEASE 中文文档 ](http://blog.didispace.com/books/spring-mvc-4-tutorial/)
- [Spring boot learing](https://gitee.com/didispace/SpringBoot-Learning)
- [Spring Boot构建RESTful API与单元测试](http://blog.didispace.com/springbootrestfulapi/)
- [Spring Boot中增强对MongoDB的配置（连接池等）](http://blog.didispace.com/springbootmongodb-plus/)
- [Spring boot 系列文章](http://blog.didispace.com/tags/Spring-Boot/)
- [Spring Boot基础教程](http://blog.didispace.com/Spring-Boot基础教程/)
- [Spring Boot 2.0 新特性学习](http://blog.didispace.com/Spring-Boot-2-0-feature/)
- [Spring Cloud基础教程](http://blog.didispace.com/Spring-Cloud基础教程/)
- [云原生应用的12要素](http://blog.didispace.com/12factor-zh-cn/)
这是一个非常好的总结，有必要看一下
- [spring cloud](http://blog.didispace.com/categories/Spring-Cloud/)
- [spring for all 社区](http://www.spring4all.com)
- [Spring Boot应用Docker化](http://doc.spring4all.com/spring-guildes/spring-boot-with-docker.html)
- [论系统架构设计中缓存的重要性](http://www.spring4all.com/article/653)
解耦是计算机系统架构设计中最常用的手段，那么缓存就必将在计算机系统中无处不在。使用缓存将大大提高我们系统的性能。而缓存用空间换时间的方式解决问题，而空间不可能无限使用，使用缓存我们将考虑如何存储，如何使用，如何提高命中率，如何确定有效的更新策略、更有如何保证数据一致性、自身可用性可维护性等等。[论系统架构设计中缓存的重要性](http://www.spring4all.com/question/177)



### 关于 Spring 的一些注解
- `@Controller`：修饰 class，用来创建处理 http 请求的对象
- `@RestController`：Spring4 之后加入的注解，原来在 `@Controller` 中返回 json 需要 `@ResponseBody` 来配合，如果直接用 @RestController 替代 @Controller 就不需要再配置 @ResponseBody，默认返回 json 格式。
- `@RequestMapping`：配置 url 映射

### Spring boot 配置的优先级

1. 命令行参数
2. java:comp/env 里的 JNDI 属性
3. JVM 系统属性
4. 操作系统环境变量
5. RandomValuePropertySource 属性类生成的 `random.*` 属性
6. 应用以外的 application.properties（或 yml）文件
7. 打包在应用内的 application.properties（或 yml）文件
8. 在应用 @Configuration 配置类中，用 @PropertySource 注解声明的属性文件
9. SpringApplication.setDefaultProperties 声明的默认属性

命令行参数优先级最高。这个可以根据这个优先级，可以在测试或生产环境中快速地修改配置参数值，而不需要重新打包和部署应用。

[Spring boot 的配置项](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)




#### 其他资源
- [js函数式编程](http://blog.didispace.com/books/mostly-adequate-guide-chinese/)
- [http2](http://blog.didispace.com/books/http2-explained-chinese/)
- [程序员的自我修养](http://blog.didispace.com/books/a-programmer-prepares/)
- [阿里云Redis开发规范](http://blog.didispace.com/阿里云Redis开发规范/)




