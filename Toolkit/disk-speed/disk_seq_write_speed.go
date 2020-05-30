package main

import (
	"flag"
	"log"
	"math"
	"os"
	"time"

	"github.com/tommwq/goutil"
)

func main() {

	var (
		blockSize int
		totalSize int
		times     int
	)

	flag.IntVar(&blockSize, "block", int(10*goutil.KB), "块大小")
	flag.IntVar(&totalSize, "total", int(256*goutil.MB), "写入大小")
	flag.IntVar(&times, "times", 3, "测试次数")
	flag.Parse()

	filename := "a.txt"
	file, err := os.OpenFile(filename, os.O_WRONLY|os.O_CREATE, 0755)
	if err != nil {
		log.Fatal(err)
	}
	defer os.Remove(filename)
	defer file.Close()

	block := make([]byte, blockSize)
	totalSeconds := 0.0

	loop := int(math.Ceil(float64(totalSize) / float64(blockSize)))
	remainTimes := times
	for remainTimes > 0 {

		err = file.Truncate(0)
		if err != nil {
			log.Fatal(err)
		}

		_, err = file.Seek(int64(0), os.SEEK_SET)
		if err != nil {
			log.Fatal(err)
		}

		startTime := time.Now()
		for i := 0; i < loop; i++ {
			_, err = file.Write(block)
			if err != nil {
				log.Fatal(err)
			}
		}
		stopTime := time.Now()

		seconds := stopTime.Sub(startTime).Seconds()
		totalSeconds = totalSeconds + seconds
		speed := float64(totalSize) / 1024.0 / 1024.0 / seconds

		log.Printf("速度: %.2f MB/s 写入数据: %s 块大小: %s 时间: %.2f秒\n",
			speed, goutil.PrettyVolume(uint64(totalSize)), goutil.PrettyVolume(uint64(blockSize)), seconds)

		remainTimes = remainTimes - 1
	}

	log.Println("总计:")
	speed := float64(totalSize*times) / 1024.0 / 1024.0 / totalSeconds
	log.Printf("速度: %.2f MB/s 写入数据: %s 块大小: %s 时间: %.2f秒\n",
		speed, goutil.PrettyVolume(uint64(totalSize*times)), goutil.PrettyVolume(uint64(blockSize)), totalSeconds)

}
