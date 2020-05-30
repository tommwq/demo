// array_and_slice.go
// 展示Go语言数组用法。
// 20171107 tommwq

// TODO 使用unsafe探查array和slice底层结构。

package main

import (
	"fmt"
	"reflect"
	"strings"
	"unsafe"
)

func main() {

	createArray()
	accessArray()
	typeOfArray()
	compareArray()
	arrayAsMapKey()
	iterateArray()
	sizeOfArray()

	createSlice()
	lengthAndCapacityOfSlice()
	typeOfSlice()
	zeroValueOfSlice()
	iterateSlice()
	sliceOfSlice()
	slicingSlice()
	sizeOfSlice()
}

func sizeOfSlice() {
	fmt.Println("size of slice")
	fmt.Println(unsafe.Sizeof([]int{1}))
}

func sizeOfArray() {
	fmt.Println("size of array")
	fmt.Println(unsafe.Sizeof([1]int{1}))
}

func typeOfSlice() {
	fmt.Println("type of slice")
	fmt.Println(reflect.TypeOf([]int{1, 2, 3}))
}

func lengthAndCapacityOfSlice() {
	fmt.Println("length and capacity of slice")

	s1 := []int{1, 2, 3}
	fmt.Println("len", len(s1), "cap", cap(s1))

	s2 := make([]int, 3, 5)
	fmt.Println("len", len(s2), "cap", cap(s2))
}

func createSlice() {

	s1 := make([]int, 3, 5)
	s2 := s1[:2]
	s3 := []int{1, 2, 3}

	fmt.Println("create slice")
	fmt.Println(s1, s2, s3)
}

func typeOfArray() {
	var arr1 [2]int
	var arr2 [2]int
	var arr3 [3]int

	arr1[0] = 1
	arr2 = arr1

	fmt.Println("type of array")
	fmt.Println("arr2", arr2, "type(arr2)", reflect.TypeOf(arr2), "type(arr3)", reflect.TypeOf(arr3))
	// arr3 = arr2 // cannot use arr2 (type [2]int) as type [3]int in assignment
}

func createArray() {
	fmt.Println("create array")

	// 建立数组
	var arr1 [2]int
	fmt.Println(arr1)

	// 初始化数组
	arr2 := [2]int{1, 2}
	fmt.Println(arr2)

	// 初始化数组，将arr3[1]初始化为1
	arr3 := [2]int{1: 1}
	fmt.Println(arr3)

	// 初始化数组，自动决定数组长度
	arr4 := [...]int{1, 2, 3}
	fmt.Println(arr4)

	// 不能使用make建立数组
	// var arr5 [2]int = make([]int, 2) // cannot use make([]int, 2) (type []int) as type [2]int in assignment
	// arr6 := make([2]int, 2) // cannot make type [2]int

	// 数组的长度是不可变的
	// append(arr4, 4) // first argument to append must be slice; have [3]int
}

func compareArray() {
	fmt.Println("compare array")
	// 数组比较
	var arr1 [2]int
	arr2 := [2]int{1, 2}
	arr3 := [2]int{1: 1}

	fmt.Println(arr1, arr2, arr3, arr1 == arr2, arr1 == arr3)
}

func arrayAsMapKey() {
	fmt.Println("array as map key")
	var arr1 [2]int
	var arr2 = [2]int{1, 0}

	// 数组作为键
	m := make(map[[2]int]string)
	m[arr1] = "arr1"
	fmt.Println(m[arr2])

}

func iterateArray() {
	fmt.Println("iterate array")
	// 数组迭代
	arr5 := [...]int{1, 2, 3, 4, 5}
	for index, value := range arr5 {
		// 迭代时获取的是副本
		fmt.Println(index, value, &index, &value)
	}
}

func accessArray() {
	fmt.Println("access array")
	var arr [2]int
	// 数组元素赋值
	arr[0] = 1
	fmt.Println(arr, arr[1])
}

func zeroValueOfSlice() {
	fmt.Println("slice zero value is nil")
	// nil切片
	var z []int
	fmt.Println(z, len(z), cap(z))
}

func iterateSlice() {
	fmt.Println("iterator slice")
	// 切片迭代
	slice := []int{2, 3, 5, 7, 11, 13}
	for index, value := range slice {
		fmt.Printf("slice[%d] == %d\n", index, value)
	}
}

func sliceOfSlice() {
	fmt.Println("slice of slice")
	var printBoard func([][]string)

	printBoard = func(s [][]string) {
		for i := 0; i < len(s); i++ {
			fmt.Printf("%s\n", strings.Join(s[i], " "))
		}
	}

	game := [][]string{
		{"_", "_", "_"},
		{"_", "_", "_"},
		{"_", "_", "_"},
	}
	printBoard(game)

	game[0][0] = "X"
	game[2][2] = "O"
	game[2][0] = "X"
	game[1][0] = "O"
	game[0][2] = "X"
	printBoard(game)

}

func slicingSlice() {
	fmt.Println("slicing a slice")
	s := []int{2, 3, 5, 7, 11, 13}

	fmt.Println("s ==", s)
	fmt.Println("s[1:4] ==", s[1:4])

	fmt.Println("s[:3] ==", s[:3])

	fmt.Println("s[4:] ==", s[4:])
}
