module devmng

go 1.14

require (
	db v0.0.0
	github.com/gin-gonic/gin v1.9.0
	golang.org/x/crypto v0.5.0
	golang.org/x/text v0.7.0
	gorm.io/driver/mysql v1.0.1
	gorm.io/gorm v1.20.2
)

replace db => ./db
