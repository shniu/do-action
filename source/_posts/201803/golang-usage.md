---
title: Golang
p: 201803/golang-usage
date: 2018-03-25 08:09:05
tags:
  - Golang
---

## Golang 关键字

- var和const ：变量和常量的声明
- go : 用于并行
go 关键字用来创建 goroutine (协程)，是实现并发的关键。

```
//go 关键字放在方法调用前新建一个 goroutine 并让他执行方法体
go GetThingDone(param1, param2);

//上例的变种，新建一个匿名方法并执行
go func(param1, param2) {
}(val1, val2)

//直接新建一个 goroutine 并在 goroutine 中执行代码块
go {
    //do someting...
}
```

## Golang flag 解析入参

flag 是 go 标准库提供的解析命令行参数的包。

使用 flag.String(), Bool(), Int() 等函数注册 flag

```go
import "flag"
// 声明了一个整数flag，解析结果保存在*int指针ip里
var ip = flag.Int("flagname", 1234, "help message for flagname")

// 下面的方式也是可以的
var flagvar int
func init() {
	flag.IntVar(&flagvar, "flagname", 1234, "help message for flagname")
}
```

#### 自定义 flag

需要实现 `flag.Value` interface:

```
type Value interface {
     String() string
     Set(string) error
 }
```

**Example**

```
type percentage float32
 func (p *percentage) Set(s string) error {
     v, err := strconv.ParseFloat(s, 32)
     *p = percentage(v)
     return err
 }
 func (p *percentage) String() string {
     return fmt.Sprintf("%f", *p)
 }

var pop percentage
flag.Var(&pop, "pop", "popularity")

```

在注册完成之后，最后调用 `flag.Parse()`

#### 命令行

```
-flag
-flag=x
-flag x  // 只有非bool类型的flag可以
```

可以使用1个或2个'-'号，效果是一样的。最后一种格式不能用于 bool 类型的 flag。


#### 来个简单的例子：

```go
package main
import (
    "fmt"
    "flag"
)

func main(){
    data_path := flag.String("D","/home/manu/sample/","DB data path")
    log_file := flag.String("l","/home/manu/sample.log","log file")
    nowait_flag :=flag.Bool("W",false,"do not wait until operation completes")

    flag.Parse()

    var cmd string = flag.Arg(0);

    fmt.Printf("action   : %s\n",cmd)
    fmt.Printf("data path: %s\n",*data_path)
    fmt.Printf("log file : %s\n",*log_file)
    fmt.Printf("nowait     : %v\n",*nowait_flag)

    fmt.Printf("-------------------------------------------------------\n")

    fmt.Printf("there are %d non-flag input param\n", flag.NArg())
    for i,param := range flag.Args() {
        fmt.Printf("#%d    :%s\n",i,param)
    }
}
```

## 参考资料

- [Go 标准库](https://studygolang.com/pkgdoc)

