// goroutine_leak_finder.go
// 帮助查找goroutine泄露的工具。
//
// tommwq

// 修改记录
// 2017年10月24日 建立程序。
// 2017年10月25日 修改排序错误的问题；优化输出格式。

// 用法：
// 编写go程序时，在程序中引入net/http/pprof包，并启动http服务。将http服务器地址和端口作为命令行参数传递给本程序：
//   GoroutineLeakFinder -server 127.0.0.1:6060

package main

import (
	"bytes"
	"encoding/json"
	"flag"
	"fmt"
	"io/ioutil"
	"net/http"
	"os"
	"regexp"
	"sort"
	"strconv"
	"strings"
	"sync"
)

type ErrInvalidArgument struct {
	v []interface{}
}

func (r ErrInvalidArgument) Error() string {
	return fmt.Sprintf("invalid argument error: %v", r.v)
}

func NewErrInvalidArgument(v ...interface{}) error {
	return ErrInvalidArgument{v: v}
}

func main() {
	var server string
	flag.StringVar(&server, "server", "127.0.0.1:6060", "HTTP server address of net/http/pprof")
	flag.Parse()

	url := fmt.Sprintf("http://%v/debug/pprof/goroutine?debug=2", server)
	if response, err := http.Get(url); err != nil {
		onError(err)
	} else {
		defer response.Body.Close()
		if data, err := ioutil.ReadAll(response.Body); err != nil {
			onError(err)
		} else {
			analysisGoroutineStats(parseGoroutineProfile(string(data)))
		}
	}
}

func onError(err error, additions ...interface{}) {
	fmt.Println(err)
	for _, x := range additions {
		fmt.Print(x)
		fmt.Print(" ")
	}
	fmt.Println("")
	os.Exit(-1)
}

// GoroutineStats 记录http://localhost:6060/debug/pprof/goroutine?debug=2返回的goroutine信息
type GoroutineStats struct {
	ID         int
	State      string
	Function   string
	SourceFile string
	SourceLine int
}

func (r *GoroutineStats) String() string {
	structName := "GoroutineStats"
	buffer := bytes.NewBuffer(make([]byte, 0))

	if data, err := json.Marshal(r); err != nil {
		return structName
	} else if err := json.Indent(buffer, data, "", "    "); err != nil {
		return structName
	} else {
		return buffer.String()
	}
}

type CountResult struct {
	Count      int
	Function   string
	SourceFile string
	SourceLine int
}

func (r *CountResult) String() string {
	buffer := bytes.NewBuffer(make([]byte, 0))
	data, _ := json.Marshal(r)
	json.Indent(buffer, data, "", "    ")
	return buffer.String()
}

type CountResultArray []*CountResult

func (r CountResultArray) Len() int {
	return len(r)
}

func (r CountResultArray) Less(i, j int) bool {
	return r[i].Count > r[j].Count
}

func (r CountResultArray) Swap(i, j int) {
	t := r[j]
	r[j] = r[i]
	r[i] = t
}

func analysisGoroutineStats(statsList []*GoroutineStats) {

	resultTable := make(map[string]*CountResult)
	totalCount := 0

	for _, stats := range statsList {
		totalCount++

		function := stats.Function
		if cr, ok := resultTable[function]; !ok {
			resultTable[function] = &CountResult{
				Count:      1,
				Function:   function,
				SourceFile: stats.SourceFile,
				SourceLine: stats.SourceLine,
			}
			continue
		} else {
			cr.Count++
		}
	}

	results := make([]*CountResult, 0, len(resultTable))
	for _, c := range resultTable {
		results = append(results, c)
	}

	sort.Sort(CountResultArray(results))
	limit := 8
	fmt.Println("TOTAL GOROUTINE:", totalCount)
	format := "%2v %-8v %-v\n            %v:%v\n"
	fmt.Printf(format, "#", "Count", "Function", "File", "Line")
	for i, c := range results {
		if i >= limit {
			break
		}
		fmt.Printf(format, i+1, c.Count, c.Function, c.SourceFile, c.SourceLine)
	}
}

func parseGoroutineProfile(content string) []*GoroutineStats {
	blocks := strings.Split(content, "\n\n")

	statsList := make([]*GoroutineStats, 0, len(blocks))
	queue := make(chan *GoroutineStats)
	done := make(chan bool)

	go func() {
		for stats := range queue {
			statsList = append(statsList, stats)
		}
		done <- true
	}()

	wg := sync.WaitGroup{}
	for _, block := range blocks {
		wg.Add(1)
		go func(block string) {
			defer wg.Done()
			if stats, err := parseGoroutineProfileBlock(block); err != nil {
				onError(err)
			} else {
				queue <- stats
			}

		}(block)
	}
	wg.Wait()
	close(queue)

	<-done
	return statsList
}

func parseGoroutineProfileBlock(block string) (*GoroutineStats, error) {
	var (
		pieces []string
		err    error
		stats  GoroutineStats
		line   string
	)

	reFirstLine := regexp.MustCompile(`goroutine (\d*) (.*):`)
	reSecondLine := regexp.MustCompile(`(.*)\x28.*\x29$`)
	reThirdLine := regexp.MustCompile(`(.*):(\d*).*`)

	lines := strings.Split(block, "\n")
	if len(lines) < 3 {
		return parseGoroutineProfileBlockSpecifical(block)
	}

	line = strings.Trim(lines[0], " \r\n\t")
	pieces = reFirstLine.FindStringSubmatch(line)
	if len(pieces) < 3 {
		return nil, NewErrInvalidArgument(pieces, line)
	}
	if stats.ID, err = strconv.Atoi(pieces[1]); err != nil {
		return nil, err
	}
	stats.State = pieces[2]

	line = strings.Trim(lines[1], " \r\n\t")
	pieces = reSecondLine.FindStringSubmatch(line)
	if len(pieces) < 2 {
		return nil, NewErrInvalidArgument(pieces, line)
	}
	stats.Function = pieces[1]

	line = strings.Trim(lines[2], " \r\n\t")
	pieces = reThirdLine.FindStringSubmatch(line)
	if len(pieces) < 3 {
		return nil, NewErrInvalidArgument(pieces, line)
	}

	stats.SourceFile = strings.Trim(pieces[1], " \t\r\n")
	if stats.SourceLine, err = strconv.Atoi(pieces[2]); err != nil {
		return nil, err
	}

	return &stats, nil
}

func parseGoroutineProfileBlockSpecifical(block string) (*GoroutineStats, error) {
	re := regexp.MustCompile(`goroutine (\d*) (.*): (.*?\x28.*?\x29)\t(.*?\x28.*?\x29):(\d*)`)
	pieces := re.FindStringSubmatch(block)
	fmt.Println(pieces)
	panic("dd")
}
