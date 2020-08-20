// goroutine_count.go
//
// 检查http://localhost:6060/debug/pprof/goroutine?debug=1中的goroutine profile: total是正在运行的goroutine数量，
// 还是从程序启动开始累计建立的goroutine数量。
//
// 注：也可以从http://localhost:6060/debug/pprof/获取goroutine数量。取得的值与上面的URL一样。
//
// tommwq
// 2017年10月24日
//

// 程序输出：
// goroutine profile: total 5
// goroutine profile: total 105
// goroutine profile: total 5

package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
	_ "net/http/pprof"
	"os"
	"strings"
	"sync"
	"time"
)

func main() {
	port := 6061
	go func() {
		if err := http.ListenAndServe(fmt.Sprintf(":%v", port), nil); err != nil {
			panic(err)
		}
	}()

	time.Sleep(time.Second)
	url := fmt.Sprintf("http://localhost:%v/debug/pprof/goroutine?debug=1", port)
	printGoroutineProfile(url)

	wg := sync.WaitGroup{}
	for i := 0; i < 100; i++ {
		wg.Add(1)
		go func() {
			time.Sleep(100 * time.Microsecond)
			wg.Done()
		}()
	}

	printGoroutineProfile(url)

	wg.Wait()
	printGoroutineProfile(url)
}

func printGoroutineProfile(url string) {

	if response, err := http.Get(url); err != nil {
		fmt.Println(err)
		os.Exit(-1)
	} else {
		defer response.Body.Close()
		if data, err := ioutil.ReadAll(response.Body); err != nil {
			fmt.Println(err)
			os.Exit(-1)
		} else {
			text := strings.Split(string(data), "\n")[0]
			fmt.Println(text)
		}

	}
}
