---
title: Spring boot - Spring Security 框架
p: 201804/spring-boot-2
date: 2018-04-21 08:42:07
tags:
  - spring
  - spring-security
---

<img class="full-image" src="/images/spring-security-architecture.png" />

## Spring security 的核心组件

### 1. SecurityContextHolder

`SecurityContextHolder` 用于存储安全上下文（security context）的信息。当前操作的用户是谁，该用户是否已经被认证，他拥有哪些角色权限…这些都被保存在 `SecurityContextHolder` 中。`SecurityContextHolder` 默认使用 `ThreadLocal` 策略来存储认证信息。看到 `ThreadLocal` 也就意味着，这是一种与线程绑定的策略。Spring Security在用户登录时自动绑定认证信息到当前线程，在用户退出时，自动清除当前线程的认证信息。

##### 1.1 获取用户信息

```java
Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

if (principal instanceof UserDetails) {
    String username = ((UserDetails)principal).getUsername();
} else {
    String username = principal.toString();
}
```

因为身份信息是与线程绑定的，所以可以在程序的任何地方使用静态方法获取用户信息。getAuthentication()返回了认证信息，再次getPrincipal()返回了身份信息，UserDetails便是Spring对身份信息封装的一个接口。

##### 1.2 Authentication

```java
package org.springframework.security.core;// <1>

public interface Authentication extends Principal, Serializable { // <1>
    Collection<? extends GrantedAuthority> getAuthorities(); // <2>

    Object getCredentials();// <2>

    Object getDetails();// <2>

    Object getPrincipal();// <2>

    boolean isAuthenticated();// <2>

    void setAuthenticated(boolean var1) throws IllegalArgumentException;
}
```

### 2. Spring Security是如何完成身份认证的？

- 用户名和密码被过滤器获取到，封装成Authentication,通常情况下是UsernamePasswordAuthenticationToken这个实现类。
- AuthenticationManager 身份管理器负责验证这个Authentication。
- 认证成功后，AuthenticationManager身份管理器返回一个被填充满了信息的（包括上面提到的权限信息，身份信息，细节信息，但密码通常会被移除）Authentication实例。
- SecurityContextHolder安全上下文容器将第3步填充了信息的Authentication，通过SecurityContextHolder.getContext().setAuthentication(…)方法，设置到其中。

### 3. AuthenticationManager

AuthenticationManager（接口）是认证相关的核心接口，也是发起认证的出发点，因为在实际需求中，我们可能会允许用户使用用户名+密码登录，同时允许用户使用邮箱+密码，手机号码+密码登录，甚至，可能允许用户使用指纹登录, 所以说AuthenticationManager一般不直接认证，AuthenticationManager接口的常用实现类ProviderManager 内部会维护一个List<AuthenticationProvider>列表，存放多种认证方式，实际上这是委托者模式的应用（Delegate）。核心的认证入口始终只有一个：AuthenticationManager，不同的认证方式：用户名+密码（UsernamePasswordAuthenticationToken），邮箱+密码，手机号码+密码登录则对应了三个AuthenticationProvider。熟悉shiro的朋友可以把AuthenticationProvider理解成Realm。在默认策略下，只需要通过一个AuthenticationProvider的认证，即可被认为是登录成功。

### 4. 核心配置

#### 4.1 @EnableWebSecurity

```
@Import({ WebSecurityConfiguration.class, // <2>
      SpringWebMvcImportSelector.class }) // <1>
@EnableGlobalAuthentication // <3>
@Configuration
public @interface EnableWebSecurity {
   boolean debug() default false;
}
```

##### 4.1.1 WebSecurityConfiguration

##### 4.1.2 AuthenticationConfiguration

#### 4.2 常用配置

```
@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/resources/**", "/signup", "/about").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .failureForwardUrl("/login?error")
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .permitAll()
                .and()
            .httpBasic()
                .disable();
    }
}
```

### 5. 过滤器

```
Creating filter chain: org.springframework.security.web.util.matcher.AnyRequestMatcher@1,
[org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@60b85ba1,

// 请求来临时，创建SecurityContext安全上下文信息，请求结束时清空SecurityContextHolder
org.springframework.security.web.context.SecurityContextPersistenceFilter@33d05366,

// 用来给http响应添加一些Header,比如X-Frame-Options, X-XSS-Protection*，X-Content-Type-Options.
org.springframework.security.web.header.HeaderWriterFilter@57a4d5ee,

// 在spring4这个版本中被默认开启的一个过滤器，用于防止csrf攻击，了解前后端分离的人一定不会对这个攻击方式感到陌生，前后端使用json交互需要注意的一个问题
org.springframework.security.web.csrf.CsrfFilter@42deb43a,

// 处理注销
org.springframework.security.web.authentication.logout.LogoutFilter@3c1e3314,

// 表单提交了username和password，被封装成token进行一系列的认证，便是主要通过这个过滤器完成的，在表单认证的方法中，这是最最关键的过滤器
org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter@79ab3a71,

// 内部维护了一个RequestCache，用于缓存request请求
org.springframework.security.web.savedrequest.RequestCacheAwareFilter@7692cd34,

// 此过滤器对ServletRequest进行了一次包装，使得request具有更加丰富的API
org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@22ee2d0,

// 匿名身份过滤器，需要将它与UsernamePasswordAuthenticationFilter 放在一起比较理解，spring security为了兼容未登录的访问，也走了一套认证流程，只不过是一个匿名的身份。
org.springframework.security.web.authentication.AnonymousAuthenticationFilter@492fc69e,

// 和session相关的过滤器，内部维护了一个SessionAuthenticationStrategy，两者组合使用，常用来防止session-fixation protection attack，以及限制同一用户开启多个会话的数量
org.springframework.security.web.session.SessionManagementFilter@3a45c42a,

// 异常翻译过滤器，还是比较形象的，这个过滤器本身不处理异常，而是将认证过程中出现的异常交给内部维护的一些类去处理
org.springframework.security.web.access.ExceptionTranslationFilter@24b52d3e,

// 决定了访问特定路径应该具备的权限，访问的用户的角色，权限是什么？访问的路径需要什么样的角色和权限？这些判断和处理都是由该类进行的
org.springframework.security.web.access.intercept.FilterSecurityInterceptor@5aa360ea]
```


## Ref

- [Spring Security(一)--Architecture Overview](http://www.spring4all.com/article/443)
- [Spring Security(二)--Guides](http://www.spring4all.com/article/445)
- [Spring filters](http://www.spring4all.com/article/447)
- [Spring boot and spring security demo code](https://github.com/longfeizheng/logback)
很直得看，里面包含很多内容

- [Spring security auth2](https://github.com/longfeizheng/security-oauth2)
- [Spring Security源码分析十一：Spring Security OAuth2整合JWT](https://longfeizheng.github.io/2018/01/23/Spring-Security源码分析十一-Spring-Security-OAuth2整合JWT/)

- [Spring Security – Roles and Privileges](http://www.baeldung.com/role-and-privilege-for-spring-security-registration)
- [使用Spring Security、Spring Data Jpa实现的RBAC权限控制](https://blog.csdn.net/u012556150/article/details/51441480)
- [pring Security 4 Role Based Login Example](http://websystique.com/spring-security/spring-security-4-role-based-login-example/)
- [User-Role-Permission security pattern (RBAC) in Spring Security 4](https://note.youdao.com/group/#/74782256/(folder/193918284//full:note/194276525)?noPush=true)
- [REST Security with JWT using Java and Spring Security](https://www.toptal.com/java/rest-security-with-jwt-spring-security-and-java)


