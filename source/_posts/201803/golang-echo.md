---
title: Echo golang 下的高性能可扩展极简 Web 框架
p: 201803/golang-echo.md
date: 2018-03-03 08:42:04
tags:
  - goloang
  - Echo
  - Web Framework
---

## 快速开始

### 安装

```
go get -u github.com/labstack/echo/...
```

### 创建一个 Hello world! 服务

创建 `server.go` 文件，代码如下：

```go
package main

import (
  "net/http",
  "github.com/labstack/echo"
)

func main() {
    e := echo.New()
    e.GET("/", func(c echo.Context) error {
        return c.String(http.StatusOK, "Hello, World!")
    })

    e.Logger.Fatal(e.Start(":1323"))
}
```

启动服务

```shell
go run server.go
```

在浏览器中打开 `http://localhost:1323` 就可以看到 `Hello, World!`

### 路由

```go
e.POST("/users", saveUser)
e.GET("/users/:id", getUser)
e.PUT("/users/:id", updateUser)
e.DELETE("/users/:id", deleteUser)
```

### 路径参数

```go
// e.GET("/users/:id", getUser)
func getUser(c echo.Context) error {
  	// User ID from path `users/:id`
  	id := c.Param("id")
	return c.String(http.StatusOK, id)
}
```

### 查询参数

例如，`/show?team=x-men&member=wolverine`

```go
//e.GET("/show", show)
func show(c echo.Context) error {
	// Get team and member from the query string
	team := c.QueryParam("team")
	member := c.QueryParam("member")
	return c.String(http.StatusOK, "team:" + team + ", member:" + member)
}
```
在浏览器中访问 `http://localhost:1323/show?team=x-men&member=wolverine` 就能看到结果

### `application/x-www-form-urlencoded` 表单提交

例如，`POST` `/save`
参数：name=""&email=""

```go
// e.POST("/save", save)
func save(c echo.Context) error {
	// Get name and email
	name := c.FormValue("name")
	email := c.FormValue("email")
	return c.String(http.StatusOK, "name:" + name + ", email:" + email)
}
```
运行如下命令：
```shell
curl -F "name=Joe Smith" -F "email=joe@labstack.com" http://localhost:1323/save
// => name:Joe Smith, email:joe@labstack.com
```

### `multipart/form-data` 表单提交

例如，`POST` `/save`
参数：name=""&avatar=File()

```go
func save(c echo.Context) error {
	// Get name
	name := c.FormValue("name")
	// Get avatar
  	avatar, err := c.FormFile("avatar")
  	if err != nil {
 		return err
 	}

 	// Source
 	src, err := avatar.Open()
 	if err != nil {
 		return err
 	}
 	defer src.Close()

 	// Destination
 	dst, err := os.Create(avatar.Filename)
 	if err != nil {
 		return err
 	}
 	defer dst.Close()

 	// Copy
 	if _, err = io.Copy(dst, src); err != nil {
  		return err
  	}

	return c.HTML(http.StatusOK, "<b>Thank you! " + name + "</b>")
}
```

运行如下命令：
```shell
curl -F "name=Joe Smith" -F "avatar=@/path/to/your/avatar.png" http://localhost:1323/save
// => <b>Thank you! Joe Smith</b>
```

### 请求处理

- 基于 `Content-Type` 的请求头，将 `json`, `xml`, `form`, `query` 的负荷绑定到 Go struct 中
- 把返回的状态码也渲染到以 `json`, `xml` 返回的响应中

```go
type User struct {
	Name  string `json:"name" xml:"name" form:"name" query:"name"`
	Email string `json:"email" xml:"email" form:"email" query:"email"`
}

e.POST("/users", func(c echo.Context) error {
	u := new(User)
	if err := c.Bind(u); err != nil {
		return err
	}
	return c.JSON(http.StatusCreated, u)
	// or
	// return c.XML(http.StatusCreated, u)
})
```

### 静态内容

```go
e.Static("/static", "static")
```
[更多看这里](https://echo.labstack.com/guide/static-files)
[模版渲染](https://echo.labstack.com/guide/templates)

### 中间件

```go
// Root level middleware
e.Use(middleware.Logger())
e.Use(middleware.Recover())

// Group level middleware
g := e.Group("/admin")
g.Use(middleware.BasicAuth(func(username, password string, c echo.Context) (bool, error) {
  if username == "joe" && password == "secret" {
    return true, nil
  }
  return false, nil
}))

// Route level middleware
track := func(next echo.HandlerFunc) echo.HandlerFunc {
	return func(c echo.Context) error {
		println("request to /users")
		return next(c)
	}
}
e.GET("/users", func(c echo.Context) error {
	return c.String(http.StatusOK, "/users")
}, track)
```

[更多看这里](https://echo.labstack.com/middleware)

