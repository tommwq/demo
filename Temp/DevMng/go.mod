module devmng

go 1.14

require (
	db v0.0.0
	github.com/gin-gonic/gin v1.7.7
	golang.org/x/crypto v0.0.0-20200622213623-75b288015ac9
	golang.org/x/text v0.3.3
	gorm.io/driver/mysql v1.0.1
	gorm.io/gorm v1.20.2
)

replace db => ./db
