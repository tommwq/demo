package main

import (
	"io/ioutil"
		"encoding/json"
)

type config struct {
	Acceptor []string
	Proposer []string
	Learner []string
	role map[string]string // <id,role>
}

func readConfig(configFilename string) (*config, error) {
	contentData, err := ioutil.ReadFile(configFilename)
	if err != nil {
		return nil, err
	}

	config := config{
		role: make(map[string]string),
	}
	err = json.Unmarshal(contentData, &config)
	if err != nil {
		return nil, err
	}

	for _, id := range config.Acceptor {
		config.role[id] = "acceptor"
	}

	for _, id := range config.Proposer {
		config.role[id] = "proposer"
	}

	for _, id := range config.Learner {
		config.role[id] = "Learner"
	}

	return &config, nil
}

func (r config) getRole(id string) string {
	role, ok := r.role[id]
	if !ok {
		return ""
	}

	return role
}
