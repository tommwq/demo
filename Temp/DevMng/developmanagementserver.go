package main

import (
	"github.com/gin-gonic/gin"
)

type DevelopManagementServer struct {
	engine *gin.Engine
}

func NewServer() *DevelopManagementServer {
	server := &DevelopManagementServer{
		engine: gin.Default(),
	}
	server.init()
	return server
}

func (r *DevelopManagementServer) init() {
	r.engine.GET("/ping", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "pong",
		})
	})

	r.engine.Static("/assets", "./assets")
	r.engine.StaticFile("/", "./assets/index.html")
}

func (r DevelopManagementServer) Serve() {
	r.engine.Run()
}
