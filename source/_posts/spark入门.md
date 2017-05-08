---
title: spark入门
date: 2017-03-31 11:22:33
tags:
    - spark
---

### 先来认识一下spark-shell

```
cd spark
bin/spark-shell

# spark shell 是一个强大的交互式数据分析工具，提供了一个简单的方式来学习 API；
# spark最主要的抽象就是RDD（所以对RDD要有深刻的理解）

# 一下操作都在spark-shell中操作
# 使用spark-shell中实例化好的SparkContext创建RDD
val textFile = sc.textFile("README.md")

# RDD有两类重要的功能，actions（可以从RDD中返回值的能力）和transformations（可以从RDD中变换成新的RDD的能力）
textFile.count()    # action
val linesWithSpark = textFile.filter(line => line.contains("Spark"))  # transformation

textFile.filter(line => line.contains("Spark")).count()  # 组合在一起搞
```

### 独立的应用程序

```scala
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SimpleApp {
  def main(args: Array[String]) {
    val logFile = "YOUR_SPARK_HOME/README.md" // 应该是你系统上的某些文件
    val conf = new SparkConf().setAppName("Simple Application")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
  }
}
```

使用sbt打包后，提交给spark执行
