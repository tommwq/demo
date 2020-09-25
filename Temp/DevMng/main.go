package main

import (
	"encoding/json"
	"io/ioutil"
	"log"
	"os"
)

func readConfig() (Config, error) {
	config := Config{}

	configFileName := "./config.json"
	if len(os.Args) > 1 {
		configFileName = os.Args[1]
	}

	data, err := ioutil.ReadFile(configFileName)
	if err != nil {
		return config, err
	}

	err = json.Unmarshal(data, &config)
	return config, err
}

func main() {
	config, err := readConfig()
	if err != nil {
		log.Fatal(err)
	}

	server, err := NewServer(config)
	if err != nil {
		log.Fatal(err)
	}
	server.Serve()
}