---
title: spring boot @Enable* 特性
p: 201805/spring-transactional
date: 2018-05-13 21:31:28
tags:
  - spring
  - springboot
---

### 首先来分析一下 Spring boot 的启动过程

- 启动代码
```
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```
一切显得这么简约．

分析过程看这里　[SpringBoot源码分析之SpringBoot的启动过程](http://fangjian0423.github.io/2017/04/30/springboot-startup-analysis/)

### Spring boot 内嵌 tomcat 优化

```
// 启动时对 JVM 配置
$ java -Xms4g -Xmx4g -Xmn768m -server -jar springboot-9-1.4.1.RELEASE.jar
// -Xms 最小内存
// -Xmx 最大使用内存
// -Xmn 年轻代大小, 推荐配置为整个堆的3/8	
// Ref: http://unixboy.iteye.com/blog/174173/

// 对 Tomcat 的配置优化，大多数是在线程数和并发连接上，这个和主机本身的配置有一定关系
 @SpringBootApplication
    public class AppApplication {
        public static void main(String args[]) {
            SpringApplication.run(AppApplication.class, args);
        }

        @Bean
        public EmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() throws IOException {
            TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
            tomcat.addAdditionalTomcatConnectors(httpConnector());
            return tomcat;
        }

        public Connector httpConnector() throws IOException {
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            Http11NioProtocol http11NioProtocol = (Http11NioProtocol) connector.getProtocolHandler();
            connector.setPort(8080);
            //设置最大线程数
            http11NioProtocol.setMaxThreads(100);
            //设置初始线程数  最小空闲线程数
            http11NioProtocol.setMinSpareThreads(20);
            //设置超时
            http11NioProtocol.setConnectionTimeout(5000);
            return connector;
        }

    }
// 
//  Spring Boot内嵌Tomcat启动
```


### Ref
- [SpringBoot自动化配置的注解开关原理](http://www.importnew.com/24168.html)
- [SpringBoot内部的一些自动化配置原理](http://fangjian0423.github.io/2016/06/12/springboot-autoconfig-analysis/)
- [Spring Boot内嵌Tomcat启动](http://www.jb51.net/article/111853.htm)
- [spring boot 自定义内嵌Tomcat](https://blog.csdn.net/mn960mn/article/details/51306140)
- [Tomcat配置和Tomcat替换](http://blog.longjiazuo.com/archives/1725)
- [SpringBoot的事务管理](http://fangjian0423.github.io/2016/10/07/springboot-transaction/)