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

	sshClientConfig := &ssh.ClientConfig{
		Config: ssh.Config{
			Ciphers: []string{"3des-cbc"},
		},
		User: r.UserName,
		Auth: []ssh.AuthMethod{
			ssh.Password(r.Password),
		},
		HostKeyCallback: ssh.FixedHostKey(hostKey),
	}
	client, err := ssh.Dial("tcp", fmt.Sprintf("%s:%d", r.IP, r.SSHPort), sshClientConfig)
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

func main() {
	machine := Machine{
		IP:       "172.24.148.14",
		UserName: "root",
		Password: "gxADMIN0833!@",
		SSHPort:  22,
		ECDSA:    "ssh-rsa AAAAB3NzaC1yc2EAAAABIwAAAQEA22i3nFRUyEAOsE3jjo3KMPri5pKvcDb/8pZE7i5s5D292xuVBbpZ4pjHjca8uiBtx7tM+tArp5nDtEbQVwFemFpeufBngQEBiVunXAv9+HPaQjb2SonUwfFSDZh1j48IJOWdvkrJyN0ODixM6b2pSlTtzb1BdGzz83NVGQBKqstiN+YQIZeN6zclml8xRMNDzy4YMyCQ32vLmJEdG5OnoLY5rckolxjoNnVrNf1/7Lxb+s/7kc4TZvALOBncWilAK1abeP+yBRF8ocIW0u/JoUR9FEkBwiK0IMNyoWOksctAlKRg+Dxo914KjxI0M8ePUHIChSSA1HbiluDFqakWnw==",
	}

	command := "rm -rf /var/lib/jenkins/.m2/repository/com/guosen/zebra/openacct/OpenAcct-api"

	stdout, stderr, err := machine.RunCommand(command)
	if err != nil {
		fmt.Println(stdout)
		fmt.Println(stderr)
		fmt.Println(err)
	}
}
