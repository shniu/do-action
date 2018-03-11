---
title: Go 的 interface
p: 201803/go-interface.md
date: 2018-03-11 22:27:40
tags:
  - Golang
  - Interface
  - 抽象编程
---

接口类型是对其它类型行为的抽象和概括；因为接口类型不会和特定的实现细节绑定在一起，通过这种抽象的方式我们可以让我们的函数更加灵活和更具有适应能力。

很多面向对象的语言都有相似的接口概念，但 Go 语言中接口类型的独特之处在于它是满足隐式实现的。也就是说，我们没有必要对于给定的具体类型定义所有满足的接口类型；简单地拥有一些必需的方法就足够了。这种设计可以让你创建一个新的接口类型满足已经存在的具体类型却不会去改变这些类型的定义；当我们使用的类型来自于不受我们控制的包时这种设计尤其有用。

## 接口即合约

目前为止，我们看到的类型都是具体的类型。一个具体的类型可以准确的描述它所代表的值并且展示出对类型本身的一些操作方式就像数字类型的算术操作，切片类型的索引、附加和取范围操作。具体的类型还可以通过它的方法提供额外的行为操作。总的来说，当你拿到一个具体的类型时你就知道它的本身是什么和你可以用它来做什么。

在 Go 语言中还存在着另外一种类型：接口类型。接口类型是一种抽象的类型。它不会暴露出它所代表的对象的内部值的结构和这个对象支持的基础操作的集合；它们只会展示出它们自己的方法。也就是说当你有看到一个接口类型的值时，你不知道它是什么，唯一知道的就是可以通过它的方法来做什么。

我们一直使用两个相似的函数来进行字符串的格式化：`fmt.Printf` 它会把结果写到标准输出和 `fmt.Sprintf` 它会把结果以字符串的形式返回。这得益于使用接口，我们不必可悲的因为返回结果在使用方式上的一些浅显不同就必需把格式化这个最困难的过程复制一份。实际上，这两个函数都使用了另一个函数 `fmt.Fprintf` 来进行封装。`fmt.Fprintf` 这个函数对它的计算结果会被怎么使用是完全不知道的。

```go
package fmt

func Fprintf(w io.Writer, format string, args ...interface{}) (int, error)
func Printf(format string, args ...interface{}) (int, error) {
    return Fprintf(os.Stdout, format, args...)
}
func Sprintf(format string, args ...interface{}) string {
    var buf bytes.Buffer
    Fprintf(&buf, format, args...)
    return buf.String()
}
```

`Fprintf` 的前缀 F 表示文件(File)也表明格式化输出结果应该被写入第一个参数提供的文件中。在 Printf 函数中的第一个参数 `os.Stdout` 是 `*os.File` 类型；在 Sprintf 函数中的第一个参数 `&buf` 是一个指向可以写入字节的内存缓冲区，然而它 并不是一个文件类型尽管它在某种意义上和文件类型相似。

即使 Fprintf 函数中的第一个参数也不是一个文件类型。它是 `io.Writer` 类型这是一个接口类型定义如下：

```go
package io

// Writer is the interface that wraps the basic Write method.
type Writer interface {
    // Write writes len(p) bytes from p to the underlying data stream.
    // It returns the number of bytes written from p (0 <= n <= len(p))
    // and any error encountered that caused the write to stop early.
    // Write must return a non-nil error if it returns n < len(p).
    // Write must not modify the slice data, even temporarily.
    //
    // Implementations must not retain p.
    Write(p []byte) (n int, err error)
}
```

`io.Writer` 类型定义了函数Fprintf和这个函数调用者之间的约定。一方面这个约定需要调用者提供具体类型的值就像 `*os.File` 和 `*bytes.Buffer`，这些类型都有一个特定签名和行为的 Write 的函数。另一方面这个约定保证了 Fprintf 接受任何满足 `io.Writer` 接口的值都可以工作。Fprintf 函数可能没有假定写入的是一个文件或是一段内存，而是写入一个可以调用 Write 函数的值。

因为 fmt.Fprintf 函数没有对具体操作的值做任何假设而是仅仅通过 io.Writer 接口的约定来保证行为，所以第一个参数可以安全地传入一个任何具体类型的值只需要满足 `io.Writer` 接口。一个类型可以自由的使用另一个满足相同接口的类型来进行替换被称作可替换性(LSP 里氏替换)。这是一个面向对象的特征。
让我们通过一个新的类型来进行校验，下面 `*ByteCounter` 类型里的 Write 方法，仅仅在丢失写向它的字节前统计它们的长度。(在这个 += 赋值语句中，让 len(p) 的类型和 `*c` 的类型匹配的转换是必须的。)

```
type ByteCounter int

func (c *ByteCounter) Write(p []byte) (int, error) {
    *c += ByteCounter(len(p)) // convert int to ByteCounter
    return len(p), nil
}
```

因为 `*ByteCounter` 满足 io.Writer 的约定，我们可以把它传入 Fprintf 函数中；Fprintf 函数执行字符串格式化的过程不会去关注 ByteCounter 正确的累加结果的长度。

```go
var c ByteCounter
c.Write([]byte("hello"))
fmt.Println(c) // "5", = len("hello")
c = 0          // reset the counter
var name = "Dolly"
fmt.Fprintf(&c, "hello, %s", name)
fmt.Println(c) // "12", = len("hello, Dolly")
```

