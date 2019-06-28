package main

import (
	"fmt"
	"strings"
)

type Role int

const (
	NullRole = Role(1 << iota)
	RoleProposer
	RoleAcceptor
	RoleLearner
)

func (r Role) String() string {
	roles := make([]string, 0, 3)

	if r.IsProposer() {
		roles = append(roles, "Proposer")
	}
	if r.IsAcceptor() {
		roles = append(roles, "Acceptor")
	}
	if r.IsLearner() {
		roles = append(roles, "Learner")
	}

	return fmt.Sprintf("Role<%v>", strings.Join(roles, ","))
}

func (r *Role) Add(role Role) {
	*r = Role(int(*r) | int(role))
}

func (r Role) IsAcceptor() bool {
	return (int(r) & int(RoleAcceptor)) > 0
}

func (r Role) IsProposer() bool {
	return (int(r) & int(RoleProposer)) > 0
}

func (r Role) IsLearner() bool {
	return (int(r) & int(RoleLearner)) > 0
}
