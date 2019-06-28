package main

import (
	"unicode/utf8"
)

func main() {
	s := "Hello, 世界"
	println("len(s)返回：", len(s))
	println("s中的字符数：", utf8.RuneCountInString(s))

	n := 0
	for i, c := range s {
		println("编码后字符的迁移量：", i, "字符对应的Unicode编号：", c)
		n++
	}
	println("s中的字符数：", n)
}
