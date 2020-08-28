// l1_cache.go
// 测试L1缓存对Go程序性能的影响。
//
// tommwq
// 2017年06月19日

package main

var (
	arr [100][100]int
	row = 100
	col = 100
)


func loop1(arr [100][100]int, row, col int) {
	for i := 0; i < row; i++ {
		for j := 0; j < row; j++ {
			arr[i][j] = 1
		}
	}
}

func loop2(arr [100][100]int, row, col int) {
	for i := 0; i < row; i++ {
		for j := 0; j < row; j++ {
			arr[j][i] = 1
		}
	}
}
