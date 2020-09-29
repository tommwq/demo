package main

import (
	"encoding/xml"
	"errors"
	"io"
	"os"
	"strings"

	"golang.org/x/text/encoding/simplifiedchinese"

	"github.com/gin-gonic/gin"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/schema"

	"db"
)

type HTTPServer struct {
	engine *gin.Engine
	config Config
	db     *gorm.DB
}

func NewServer(config Config) (*HTTPServer, error) {
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

	server := &HTTPServer{
		engine: gin.Default(),
		config: config,
		db:     db,
	}
	server.init()
	return server, nil
}

func (r *HTTPServer) success(c *gin.Context, payload interface{}) {
	c.JSON(200, gin.H{
		"error":   "",
		"payload": payload,
	})
}

func (r *HTTPServer) failure(c *gin.Context, err error) {
	c.JSON(200, gin.H{
		"error":   err.Error(),
		"payload": "",
	})
}

func (r *HTTPServer) listLBMTableMapping() []db.LBMTableMapping {
	rows := make([]db.LBMTableMapping, 0, 8192)
	r.db.Find(&rows)
	return rows
}

func (r *HTTPServer) getJZTradeXML() (JZTradeXML, error) {
	jztradeXML := JZTradeXML{}
	file, err := os.Open("./static/jztrade.xml")
	if err != nil {
		return jztradeXML, err
	}
	defer file.Close()

	xmlDecoder := xml.NewDecoder(file)
	xmlDecoder.CharsetReader = func(charset string, input io.Reader) (io.Reader, error) {
		lower := strings.ToLower(charset)
		if lower != "gbk" && lower != "gb2312" {
			return nil, errors.New("unsupported charset: " + charset)
		}
		charsetDecoder := simplifiedchinese.GBK.NewDecoder()
		return charsetDecoder.Reader(input), nil
	}

	err = xmlDecoder.Decode(&jztradeXML)
	return jztradeXML, err
}

func (r *HTTPServer) init() {
	r.engine.GET("/api/LBMTableMapping", func(c *gin.Context) {
		r.success(c, r.listLBMTableMapping())
	})

	r.engine.GET("/api/JZTradeXML", func(c *gin.Context) {
		if data, err := r.getJZTradeXML(); err != nil {
			r.failure(c, err)
		} else {
			r.success(c, data)
		}
	})

	r.engine.GET("/", func(c *gin.Context) {
		c.Redirect(302, "/static/index.html")
	})

	r.engine.Static("/static", "./static")
}

func (r HTTPServer) Serve() {
	r.engine.Run()
}
