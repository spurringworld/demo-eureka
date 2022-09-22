package main

import (
	"fmt"
	"io/ioutil"
	"net/http"

	"github.com/gin-gonic/gin"
)

type ScheduleResult struct {
	Subject       string
	ScheduleDate  string
	ScheduleTime  string
	ScheduleClass string
}

// 定义接收数据的结构体
type SchedulModel struct {
	Subject      string
	ScheduleDate string
}

func main() {
	r := gin.Default()
	r.GET("/healthy", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "up",
		})
	})

	r.POST("/logs", handlePostTraffic)
	r.POST("/schedule", handleSchedule)
	r.GET("/test", handleTest)
	r.GET("/hello", handleHello)
	r.GET("/headerinfo", handleReqHeader)
	r.Run(":8085")
}

func handleSchedule(c *gin.Context) {
	fmt.Println("------------")
	var req SchedulModel
	if err := c.ShouldBindJSON(&req); err != nil {
		// 返回错误信息，gin.H封装了生成json数据的工具
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	var result ScheduleResult
	result.Subject = req.Subject
	result.ScheduleClass = "三年二班"
	result.ScheduleDate = req.ScheduleDate
	result.ScheduleTime = "8:00 ~ 8:40"

	c.JSON(200, gin.H{
		"status": 200,
		"result": result,
	})

}

func handlePostTraffic(c *gin.Context) {
	fmt.Println("")
	fmt.Println("")
	fmt.Println(c.Request.RemoteAddr)
	fmt.Println("------------")
	for k, v := range c.Request.Header {
		fmt.Println(k, v)
	}

	body, err := ioutil.ReadAll(c.Request.Body)
	if err != nil {
		fmt.Println("Invalid request body")
	}
	fmt.Println("------------")
	fmt.Println("【body】:  " + string(body))

	c.JSON(200, gin.H{
		"message": "post!",
	})
}

func handleTest(c *gin.Context) {
	fmt.Println("------------")
	fmt.Println("")
	fmt.Println(`{ "message": "This is a test message! " }`)
	c.String(200, "{ \"message\": \"This is a test message! \" } \n")
}

func handleHello(c *gin.Context) {
	fmt.Println("------------")
	for k, v := range c.Request.Header {
		fmt.Println(k, v)
	}
	fmt.Println("Hello world ! ")
	c.String(200, "Hello world ! \n")
}

func handleReqHeader(c *gin.Context) {
	fmt.Println("------------")
	fmt.Println("")
	for k, v := range c.Request.Header {
		fmt.Println(k, v)
	}

	c.String(200, "got it! \n")
}
