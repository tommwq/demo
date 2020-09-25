package main

import (
	"github.com/gin-gonic/gin"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/schema"
)

type DevelopManagementServer struct {
	engine *gin.Engine
	config Config
	db     *gorm.DB
}

func NewServer(config Config) (*DevelopManagementServer, error) {
	// TODO close db
	db, err := gorm.Open(mysql.Open(config.Database.DSN()), &gorm.Config{
		NamingStrategy: schema.NamingStrategy{
			TablePrefix:   "",
			SingularTable: true,
		},
	})
	if err != nil {
		return nil, err
	}

	server := &DevelopManagementServer{
		engine: gin.Default(),
		config: config,
		db:     db,
	}
	server.init()
	return server, nil
}

func (r *DevelopManagementServer) success(c *gin.Context, payload interface{}) {
	c.JSON(200, gin.H{
		"error":   "",
		"payload": payload,
	})
}

func (r *DevelopManagementServer) failure(c *gin.Context, err error) {
	c.JSON(200, gin.H{
		"error":   err.Error(),
		"payload": "",
	})
}

func (r *DevelopManagementServer) listLBMTableMapping() []LBMTableMapping {
	rows := make([]LBMTableMapping, 0, 8192)
	r.db.Find(&rows)
	return rows
}

func (r *DevelopManagementServer) init() {
	r.engine.GET("/api/LBMTableMapping", func(c *gin.Context) {
		r.success(c, r.listLBMTableMapping())
	})

	r.engine.GET("/", func(c *gin.Context) {
		c.Redirect(302, "/assets/index.html")
	})

	r.engine.Static("/assets", "./assets")
}

func (r DevelopManagementServer) Serve() {
	r.engine.Run()
}
