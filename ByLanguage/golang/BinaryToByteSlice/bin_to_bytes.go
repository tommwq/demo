// read in a binary file, print as a byte slice in Go lang.
//
// tommwq
// 2017-06-26
//
//  usage:
//
//  ./btb -file <FILE> -var <VAR_NAME>
//
//  output:
//
//  <VAR_NAME> := []byte{1, 2, ....}
//

package main

import (
	"flag"
	"fmt"
	"io/ioutil"
	"log"
)

func main() {
	var file string
	var symbol string

	flag.StringVar(&file, "file", "a.out", "binary file to read")
	flag.StringVar(&symbol, "var", "a_out", "go variable name")
	flag.Parse()

	data, err := ioutil.ReadFile(file)
	if err != nil {
		log.Fatal(err)
	}

	fmt.Printf("%v := []byte {", symbol)
	for i, b := range data {
		if i%10 == 0 {
			fmt.Println()
		}
		fmt.Printf("% 4d, ", int(b))
	}

	fmt.Println()
	fmt.Printf("}")
	fmt.Println()

}
