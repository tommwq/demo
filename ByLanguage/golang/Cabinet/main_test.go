package lab

import (
	"log"
	"sync"
	"testing"
)

func TestMain(m *testing.M) {
	cabinet, err := NewCabinet(1024 * 1024)
	if err != nil {
		log.Fatal(err)
	}

	wg := &sync.WaitGroup{}

	wg.Add(1)
	go func(cabinet *Cabinet, wg *sync.WaitGroup) {
		writeTimes := 10000
		for i := 0; i < writeTimes; i++ {
			err := cabinet.Put(i)
			if err != nil {
				log.Fatal(err)
			}
			//time.Sleep(1)
		}
		wg.Done()
	}(cabinet, wg)

	readerCount := 100000
	for i := 0; i < readerCount; i++ {
		wg.Add(1)
		go func(cabinet *Cabinet,
			wg *sync.WaitGroup,
			id int) {
			_, index, err := cabinet.Get()
			if err == nil {
				cabinet.Release(index)
			}
			wg.Done()
		}(cabinet, wg, i)
	}

	wg.Wait()
}
