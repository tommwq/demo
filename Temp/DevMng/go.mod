module devmng

go 1.14

require (
	db v0.0.0
	github.com/gin-gonic/gin v1.6.3
	golang.org/x/crypto v0.0.0-20210921155107-089bfa567519
	golang.org/x/text v0.3.8
	gorm.io/driver/mysql v1.0.1
	gorm.io/gorm v1.20.2
)

replace db => ./db
