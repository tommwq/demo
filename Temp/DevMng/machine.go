package main

import (
	"bytes"
	"fmt"

	"golang.org/x/crypto/ssh"
)

// Machine表示一台服务器
type Machine struct {
	IP       string
	Name     string
	UserName string
	Password string
	SSHPort  int
	ECDSA    string
}

// 从ECDSA解析PublicKey
func (r Machine) ParseAuthorizedKey() (ssh.PublicKey, error) {
	key, _, _, _, err := ssh.ParseAuthorizedKey([]byte(r.ECDSA))
	return key, err
}

// 执行命令。返回stdout, stderr, error
func (r Machine) RunCommand(command string) (string, string, error) {
	stdout := ""
	stderr := ""

	hostKey, err := r.ParseAuthorizedKey()
	if err != nil {
		return stdout, stderr, err
	}

	sshConfig := &ssh.ClientConfig{
		User: r.UserName,
		Auth: []ssh.AuthMethod{
			ssh.Password(r.Password),
		},
		HostKeyCallback: ssh.FixedHostKey(hostKey),
	}
	client, err := ssh.Dial("tcp", fmt.Sprintf("%s:%d", r.IP, r.SSHPort), sshConfig)
	if err != nil {
		return stdout, stderr, err
	}

	session, err := client.NewSession()
	if err != nil {
		return stdout, stderr, err
	}
	defer session.Close()

	var x bytes.Buffer
	var y bytes.Buffer

	session.Stdout = &x
	session.Stderr = &y
	if err := session.Run(command); err != nil {
		return stdout, stderr, err
	}
	stdout = x.String()
	stderr = y.String()
	return stdout, stderr, nil
}

func (r Machine) ChangeSystemTime(hour, minute, second int) error {
	command := fmt.Sprintf("date -s %d:%d:%d", hour, minute, second)
	_, _, err := r.RunCommand(command)
	return err
}
