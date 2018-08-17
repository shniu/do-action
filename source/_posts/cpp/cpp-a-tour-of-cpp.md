---
title: C/C++ - A tour of c++
p: cpp/cpp-intro1
date: 2018-08-16 16:01:41
tags:
  - c/c++
---

> The first thing we do, let’s kill all the language lawyers.

## C++基础

#### 类型和变量

每一个名称和表达式都有一个类型，这个类型决定了能在它之上执行什么操作；变量声明表示向程序中引入一个名称，并且为这个名称实体指定一个类型：

- 类型定义了一组可能的值和对象上的一组操作
- 对象是一些持有某种类型的值的内存
- 值是根据类型解释的一组字节数据
- 变量是一个命名对象

C++ 中提供了各种基础类型，如：

```cpp
bool
int
double
char   // 1 个 byte，an 8-bits
unsigned
```
每个基本类型直接对应于硬件设施，并且具有固定大小，其确定可存储在其中的值的范围. 但是在不同平台下，每种类型的长度可能有所不同，可以使用sizeof来获取类型的长度。如：

```cpp
sizeof(char)  // 1
sizeof(int)   // 4
```

#### Constant

C++ 支持两种声明不可变常量的方式：

- const
- constexpr
constexpr 对应的值必须能在编译时期确定，如果在编译时期确定不了就会报错。

#### 指针，数组和引用

##### 引用

引用是某一个变量或对象的别名，对引用的操作与对其所绑定的变量或对象的操作完全等价。

> 1. &不是求地址运算符，而是起标志作用
> 2. 引用的类型必须和其所绑定的变量的类型相同
> 3. 声明引用的同时必须对其初始化，否则系统会报错
> 4. 引用相当于变量或对象的别名，因此不能再将已有的引用名作为其他变量或对象的名字或别名
> 5. 引用不是定义一个新的变量或对象，因此内存不会为引用开辟新的空间存储这个引用
> 6. 对数组的引用, `类型 (&引用名)[数组中元素数量]=数组名`
> 7. 对指针的引用, `类型 *&引用名=指针名`

```cpp
#include<iostream>
using namespace std;
int main(){
    int a=10;
    int *ptr=&a;
    int *&new_ptr=ptr;
    cout<<&ptr<<" "<<&new_ptr<<endl;
    return 0; 
}
```

**引用的用处**

- 引用作为函数的参数

> 1. 使用引用传递函数的参数，在内存中并没有产生实参的副本，它是直接对实参操作；而使用一般变量传递函数的参数，当发生函数调用时，需要给形参分配存储单元，形参变量是实参变量的副本；如果传递的是对象，还将调用拷贝构造函数。因此，当参数传递的数据较大时，用引用比用一般变量传递参数的效率和所占空间都好。
> 2. 使用指针作为函数的参数虽然也能达到与使用引用的效果，但是，在被调函数中同样要给形参分配存储单元，且需要重复使用\"*指针变量名\"的形式进行运算，这很容易产生错误且程序的阅读性较差；另一方面，在主调函数的调用点处，必须用变量的地址作为实参。而引用更容易使用，更清晰。
> 3. 如果既要利用引用提高程序的效率，又要保护传递给函数的数据不在函数中被改变，就应使用常引用。
> 4. 如果在编程过程中既希望通过让引用作为函数的参数来提高函数的编程效率，又希望保护传递的参数使其在函数中不被改变，则此时应当使用对常量的引用作为函数的参数
> 5. 数组的引用作为函数的参数：C++的数组类型是带有长度信息的，引用传递时如果指明的是数组则必须指定数组的长度

```cpp
#include<iostream>
using namespace std;
void func(int(&a)[5]){//数组引用作为函数的参数，必须指明数组的长度 
//函数体 
}
int main(){
    int number[5]={0,1,2,3,4};
    func(number); 
    return 0; 
 }
```

- 常引用

```cpp
const 类型 &引用名=目标变量名

// --
#include<iostream>
#include<string> 
using namespace std;
string func1(){
    string temp="This is func1";
    return temp;
}
void func2(const string &str){   // 这里如果不加 const 会报错，因为不能将 const 常量赋值给变量的引用
    cout<<str<<endl;
}
int main(){
    func2(func1());
    func2("Tomwenxing");
    return 0;
}
```

- 引用作为函数的返回值

> 1. 不能返回局部变量的引用
> 2. 引用作为函数的返回值时，必须在定义函数时在函数名前将&
> 3. 用引用作函数的返回值的最大的好处是在内存中不产生返回值的副本
> 4. 不能返回函数内部通过new分配的内存的引用
> 5. 当返回类成员的引用时，最好是const引用。这样可以避免在无意的情况下破坏该类的成员
> 6. 可以用函数返回的引用作为赋值表达式中的左值

```cpp
#include<iostream>
using namespace std;
int value[10];
int error=-1;
int &func(int n){
    if(n>=0&&n<=9)
        return value[n];//返回的引用所绑定的变量一定是全局变量，不能是函数中定义的局部变量 
    else
        return error;
}

int main(){
    func(0)=10;
    func(4)=12;
    cout<<value[0]<<endl;
    cout<<value[4]<<endl;
    return 0; 
}
```

- 用引用实现多态

引用是除了指针外另一个可以产生多态效果的手段。也就是说一个基类的引用可以用来绑定其派生类的实例

```cpp
class Father;//基类（父类）
class Son：public Father{.....}//Son是Father的派生类
Son son;//son是类Son的一个实例
Father &ptr=son;//用派生类的对象初始化基类对象的使用
```

1.在引用的使用中，单纯给某个变量去别名是毫无意义的，引用的目的主要用于在函数参数的传递中，解决大块数据或对象的传递效率和空间不如意的问题

2.用引用传递函数的参数，能保证参数在传递的过程中不产生副本，从而提高传递效率，同时通过const的使用，还可以保证参数在传递过程中的安全性

3.引用本身是目标变量或对象的别名，对引用的操作本质上就是对目标变量或对象的操作。因此能使用引用时尽量使用引用而非指针

#### 建议

- You don’t hav e to know every detail of C++ to write good programs.
- Focus on programming techniques, not on language features.
- For the final word on language definition issues, see the ISO C++ standard
- A function should perform a single logical operation
- Keep functions short
- Use overloading when functions perform conceptually the same task on different types
- If a function may have to be evaluated at compile time, declare it constexpr
- Avoid ‘‘magic constants;’’ use symbolic constants
- Declare one name (only) per declaration
- Keep common and local names short, and keep uncommon and nonlocal names longer.
- Avoid similar-looking names.
- Prefer the {}-initializer syntax for declarations with a named type; 
- Prefer the = syntax for the initialization in declarations using auto;
- Avoid uninitialized variables; 
- Keep scopes small;
- Keep use of pointers simple and straightforward;
- Use nullptr rather than 0 or NULL;
- Don’t declare a variable until you have a value to initialize it with; 
- Don’t say in comments what can be clearly stated in code
- State intent in comments
- Avoid complicated expressions
- Avoid narrowing conversions


## 自定义类型

#### 结构体

构建自定义类型的第一种方式是组织需要的元素到一个数据结构中，称为结构体：

```cpp
struct Vector {
  int sz; // number of elements
  double∗ elem; // pointer to elements
};

// then
Vector v;

// We use . (dot) to access struct members through a name (and through a reference) and −> to
// access struct members through a pointer
void f(Vector v, Vector& rv, Vector∗ pv)
{
  int i1 = v.sz; // access through name
  int i2 = rv.sz; // access through reference
  int i4 = pv−>sz; // access through pointer
}
```
