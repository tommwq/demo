//
// 计算源代码文件行数。
//


package main

import (
	"container/list"
	"flag"
	"log"
	"os"
	"path/filepath"
	"sort"
	"strings"
)

func main() {
	log.SetFlags(log.Llongfile | log.LstdFlags)

	var directory string
	var suffixParam string

	// -path src -suffix c,go,css
	flag.StringVar(&directory, "path", ".", "path to src")
	flag.StringVar(&suffixParam, "suffix", "go,c", "src file suffixes")
	flag.Parse()

	log.Println(directory, suffixParam)

	suffixes := strings.Split(suffixParam, ",")
	sort.Strings(suffixes)
	sourceFiles, err := GetSourceFileList(directory, suffixes)
	if err != nil {
		log.Fatalln("错误：%v", err)
	}

	log.Println("文件数量：%v", sourceFiles.Len())
	for e := sourceFiles.Front(); e != nil; e = e.Next() {
		log.Println(e)
	}

	/*
		srcMap := GetParsedFilesByConf(allFiles, conf)
		for k, v := range srcMap{
			fmt.Println(k, "file: ", len(v))
		}
		res := Parse(srcMap, conf)
		total := 0
		for k, v := range res{
			total += v.COMMENT + v.CODE
			fmt.Println("===========================================================")
			fmt.Println(k, "file file line: ", v.COMMENT + v.CODE)
			fmt.Println(k, "file comment line: ", v.COMMENT)
			fmt.Println(k, "file code line: ", v.CODE)
			fmt.Println("===========================================================")
		}
		fmt.Println("file total line: ", total)
	*/
}

/*
type SRCNUM struct {
	CODE    int
	COMMENT int
}

//example:map["go" or "css"]123
func Parse(files map[string][]string, conf CONF) (parseResult map[string]SRCNUM) {
	parseResult = map[string]SRCNUM{}

	for k, v := range files {
		te, tc := 0, 0
		for _, v2 := range v {
			e, c := ComputeLine(v2, conf)
			te += e
			tc += c
		}
		parseResult[k] = SRCNUM{te, tc}
	}
	return
}

func GetComment(ext string, conf CONF) (comment string) {
	for _, v := range conf.FILES {
		if v.EXT == ext {
			comment = v.COMMENT
			break
		}
	}
	return
}

func ComputeLine(path string, conf CONF) (code, comment int) {
	f, err := os.Open(path)
	if nil != err {
		log.Println(err)
		return
	}
	defer f.Close()
	ext := filepath.Ext(path)
	strComment := GetComment(ext, conf)

	scanner := bufio.NewScanner(f)
	for scanner.Scan() {
		line := scanner.Text()
		if strings.HasPrefix(strings.TrimSpace(line), strComment) {
			comment += 1
			continue
		}
		code += 1
	}
	return
}
*/
/*
parse named conf file to get extention,
and comment into return value @conf .

func ParseConf() (conf CONF, err error) {
	confPath, _ := os.Getwd()
	confPath += "/conf.xml"
	file, err := os.Open(confPath)
	if nil != err {
		log.Println(err)
		return
	}
	defer file.Close()

	data, err := ioutil.ReadAll(file)
	if err != nil {
		log.Println(err)
		return
	}

	conf = CONF{}
	err = xml.Unmarshal(data, &conf)
	if nil != err {
		log.Println(err)
		return
	}
	//log.Println(conf)

	return
}

func GetParsedFilesByConf(lst list.List, conf CONF) (result map[string][]string) {
	result = map[string][]string{}

	for e := lst.Front(); nil != e; e = e.Next() {
		ext := filepath.Ext(e.Value.(string))
		for _, file := range conf.FILES {
			if ext == file.EXT {
				result[ext] = append(result[ext], e.Value.(string))
				break
			}
		}
	}

	return
}
*/

// put all file names of the project into @lst
func GetSourceFileList(directory string, suffixes []string) (*list.List, error) {
	fullPath, err := filepath.Abs(directory)
	if err != nil {
		return nil, err
	}

	log.Println("统计目录[%v]下的代码。", fullPath)

	files := list.New()
	err = filepath.Walk(fullPath, func(path string, info os.FileInfo, err error) error {
		if err != nil {
			return err
		}
		if info.IsDir() {
			return nil
		}
		if !IsSourceFile(path, suffixes) {
			return nil
		}

		files.PushBack(path)
		return nil
	})

	return files, err
}

// judge if a file is source file
func IsSourceFile(path string, suffixes []string) bool {
	suffix := filepath.Ext(path)
	if sort.SearchStrings(suffixes, suffix) == len(suffixes) {
		return false
	}

	return true
}
