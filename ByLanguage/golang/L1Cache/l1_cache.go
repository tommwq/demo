// l1_cache.go
// 测试L1缓存对Go程序性能的影响。
//
// tommwq
// 2017年06月19日

package main

var (
	arr = make([][]int, row)
	row = 100
	col = 100
)

func init() {
	for i := 0; i < row; i++ {
		arr[i] = make([]int, col)
	}
}

func loop1(arr [][]int, row, col int) {
	for i := 0; i < row; i++ {
		for j := 0; j < row; j++ {
			arr[i][j] = 1
		}
	}
}

func loop2(arr [][]int, row, col int) {
	for i := 0; i < row; i++ {
		for j := 0; j < row; j++ {
			arr[j][i] = 1
		}
	}
}
