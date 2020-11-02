module devmng

go 1.14

require (
	db v0.0.0
	github.com/denisenkom/go-mssqldb v0.0.0-20200910202707-1e08a3fab204
	github.com/gin-gonic/gin v1.6.3
	golang.org/x/crypto v0.0.0-20190325154230-a5d413f7728c
	golang.org/x/text v0.3.3
	gorm.io/driver/mysql v1.0.1
	gorm.io/gorm v1.20.2
)

replace db => ./db
