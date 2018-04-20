---
title: 单元测试
p: 201804/unit-test
date: 2018-04-18 13:24:41
tags:
  - unittest
  - 重构
---

## 单元测试

单元测试关注一个方法或一个类。它应该非常小，最多只有几行代码。人们在编写单元测试时会犯许多错误，所以这不是小事。因为它们非常小，所以它们应该在内存中运行，而且，一个单元测试应该在几毫秒内运行完成。任何用到外部依赖（数据库、WebService、文件系统、I/O）的测试都不是一个单元测试，那是其他的东西（“集成测试（integration test）”、“综合测试（integrated test）”、验收测试、端到端测试，等等）。

单元测试更偏技术，它们通常关注代码细节，甚或是编程语言特有的概念。在类似Java这样的静态编程语言和类似Ruby这样的动态编程语言中，单元测试看上去是不一样的。这就是为什么编写单元测试主要应该由程序员负责。

另一方面，测试人员更了解如何制定测试计划，根据特定分析，如等价类划分和边界值分析，定义有价值的值。因此，程序员需要从测试人员那里“窃取”这类知识，或者，他们可以和测试人员结对，一起讨论需要编写的测试，但是，之后应该由程序员实现它们。

## 什么时候适合写单元测试

团队可以在生产代码编写完成之后编写单元测试。我们称之为“测试延后（test after）”。但是那通常很困难，因为在编写生产代码时需要时刻考虑可测试性。如果我们选择了这种方法，则生产代码需要经过一个代码审核流程，以确保它是可测试的。只有在完成这项工作之后，程序员和测试人员才可以继续结对创建测试。

也有一种方法是“测试优先（test-first）”，我建议从同伴那里“窃取”了大量测试知识的程序员使用这种方法。使用这种方法时，我们先分析问题，然后编写一个单元测试，最简单的实现代码，一个单元测试，实现它，依此类推。当团队达到了这个水平，我会说，编写单元测试的程序员是半个测试人员了，因为“测试优先”方法需要大量的测试知识。在这种情况下，测试人员最终将专注于审核单元测试及编写验收测试。

测试优先方法定义了需要做什么。它定义了我们为解决特定问题而需要编写的代码，因为我们有一个测试形式的定义。只要运行测试，我们就很容易知道我们的功能是否有效。

采用这种方法可以获得更高的覆盖率，因为测试成了一等开发活动，而不是拖到最后。

此外，在编写这些测试并详细说明场景时，我们更深入地了解了问题空间，因为许多问题会被提出来。在测试延后方法中，这些讨论有时候都不会发生，开发人员按照自己的想法编写代码，而不是根据解决方案的需要。

单元测试是一种测试特定代码片段的方法，它可以确保该代码段可以正常运行并且契合软件拼图。有证据表明，借助单元测试，你可以检查超过90%的代码，而且，和QA的手动测试工具不同，恰当构建、可以自动测试的单元测试可以随着代码库一起演化，实时测试代码。

##  编写良好的单元测试

人们有不同的看法——方法、类、模块。第一个难点是让所有的团队成员对单元是什么有一个共识，并编写相应的测试。在我看来，单元非常小，就像发动机中的螺丝或者家具中的钉子那么大。所以，我建议定义单元是什么，并在测试审核阶段对此进行密切关注，确保其得到了执行。

单元测试混合了编程和测试。为了编写简单、快捷、可维护的测试，程序员需要知道许多测试概念。如：等价划分、边界值分析、测试覆盖、正向测试、逆向测试。

关于单元测试，还有另外一个经常被遗忘的部分，那就是分析。我们不能不经过思考就立即开始编写测试。我们需要分析问题，如果可能对它进行划分，然后再考虑我们需要编写的单元测试。这里有一个不错的建议，就是总是从系统输出开始，然后找出生成那个输出的可能输入。

在编写单元测试时，我们应该使用来自问题领域的词汇。测试应该体现一项特性存在于产品中的理由。一个了解业务领域但不了解编程的人也应该能够阅读你的测试。在那方面，和分析师结对非常有用。

## 测试要多细的问题

> 老板为我的代码付报酬，而不是测试，所以，我对此的价值观是——测试越少越好，少到你对你的代码质量达到了某种自信（我觉得这种的自信标准应该要高于业内的标准，当然，这种自信也可能是种自大）。如果我的编码生涯中不会犯这种典型的错误（如：在构造函数中设了个错误的值），那我就不会测试它。我倾向于去对那些有意义的错误做测试，所以，我对一些比较复杂的条件逻辑会异常地小心。当在一个团队中，我会非常小心的测试那些会让团队容易出错的代码。

## 哪些代码更适合写单元测试

1. 逻辑复杂的
2. 容易出错的
3. 不易理解的，即使是自己过段时间也会遗忘的，看不懂自己的代码，单元测试代码有助于理解代码的功能和需求
4. 公共代码。比如自定义的所有http请求都会经过的拦截器；工具类等。
5. 核心业务代码。一个产品里最核心最有业务价值的代码应该要有较高的单元测试覆盖率。


## Ref

- [谈谈单元测试](https://juejin.im/post/5924578fa0bb9f005f784c81)
- [Sample project using Spring Boot and MongoDB(Fongo) and test repository with NoSqlUnit](https://arthurportas.wordpress.com/2017/01/21/sample-project-using-spring-boot-and-mongodbfongo-and-test-repository-with-nosqlunit/)
- [Testing the MongoDB slice with Spring and Fongo](https://devops.datenkollektiv.de/testing-the-mongodb-slice-with-spring-and-fongo.html)
- [Mocking void methods with Mockito](http://www.baeldung.com/mockito-void-methods)
- [Tests integrados en Spring Boot con Fongo](https://www.paradigmadigital.com/dev/tests-integrados-spring-boot-fongo/)
- [Test First Approaches With Test Driven Development and Behavior Driven Development](https://www.infoq.com/news/2016/01/test-first-TDD-BDD)
- [writing good unit test](http://www.infoq.com/cn/news/2017/02/writing-good-unit-tests)
- [重新思考单元测试](https://blog.fundebug.com/2017/12/20/rethinking-unit-test/)
- [Spring Boot: Unit Testing and Mocking with Mockito and JUnit](https://dzone.com/articles/spring-boot-unit-testing-and-mocking-with-mockito)

- [Mockito document](http://static.javadoc.io/org.mockito/mockito-core/2.18.0/org/mockito/Mockito.html)
- [Spring test](https://docs.spring.io/spring/docs/4.3.16.RELEASE/spring-framework-reference/htmlsingle/#testing)
- [使用Mockito和SpringTest进行单元测试](http://sunxiang0918.cn/2016/03/28/%E4%BD%BF%E7%94%A8Mockito%E5%92%8CSpringTest%E8%BF%9B%E8%A1%8C%E5%8D%95%E5%85%83%E6%B5%8B%E8%AF%95/)

- [in28minutes mockito courses](https://in28minutes1.teachable.com/courses/257149/lectures/3994283)
