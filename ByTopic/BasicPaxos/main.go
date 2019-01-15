package main

import (
	"flag"
	"log"
)

func main() {
	var (
		configFilename string
		id             string
	)

	flag.StringVar(&configFilename, "config", "config.json", "config filename")
	flag.StringVar(&id, "id", "", "node id")
	flag.Parse()

	config, err := readConfig(configFilename)
	if err != nil {
		log.Fatal(err)
	}

	_ = config
	
	// manager := NewNodeManager()
	
	// n1 := manager.NewNode("a", &Acceptor{})
	// n1.Start()
	// manager.NewNode("a", &Acceptor{}).Start()
	// manager.NewNode("a", &Acceptor{}).Start()

	// p1 := &Proposer{
	// 	Number: 1,
	// }
	// manager.NewNode("p", p1).Start()

	// p2 := &Proposer{
	// 	Number: 2,
	// }
	// manager.NewNode("p", p2).Start()

	// go p1.Propose("hello")
	// go p2.Propose("world")
	// n1.Wait()
}
