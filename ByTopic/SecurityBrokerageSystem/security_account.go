package main

import (
	"time"
)

// 证券代码
type SecurityCode struct {
	Code       string
	ExchangeID string
}

// 证券
type Security struct {
	SecurityID string
	Code       SecurityCode
	Name       string
}

// 证券总账户
type SecurityAccount struct {
	SecurityAccountID string
	Ledgers           []SecurityLedger
}

// 证券子账户条目
type SecurityLedgerEntry struct {
	Sequence   uint64
	SecurityID string
	Time       time.Time
	Count      uint64
	Reason     string
}

const (
	ACCOUNT_TYPE_UNKNOWN = iota
	ACCOUNT_TYPE_RMB_NORMAL_STOCK
	ACCOUNT_TYPE_RMB_SPECIAL_STOCK
	ACCOUNT_TYPE_NEEQ
	ACCOUNT_TYPE_CLOSE_END_FUND
	ACCOUNT_TYPE_OPEN_END_FUND
)

const (
	ACCOUNT_OPEN_APPLY_A_STOCK = iota
	ACCOUNT_OPEN_APPLY_SHANGHAI_B_STOCK
	ACCOUNT_OPEN_APPLY_SHENZHEN_B_STOCK
	ACCOUNT_OPEN_APPLY_SHANGHAI_CLOSE_END_FUND
	ACCOUNT_OPEN_APPLY_SHENZHEN_CLOSE_END_FUND
	ACCOUNT_OPEN_APPLY_SHANGHAI_DERIVATIVE
	ACCOUNT_OPEN_APPLY_SHENZHEN_DERIVATIVE
	ACCOUNT_OPEN_APPLY_NEEQ
	ACCOUNT_OPEN_APPLY_OTHER
)

// 证券子账户
type SecurityLedger struct {
	securityLedgerID string
	entries          []SecurityLedgerEntry
	balance          map[string]uint64
	accountType      int
}

func MakeSecurityLedger(id string, accountType int) SecurityLedger {
	return SecurityLedger{
		securityLedgerID: id,
		entries:          make([]SecurityLedgerEntry, 0),
		balance:          make(map[string]uint64),
		accountType:      accountType,
	}
}

func (r SecurityLedger) ID() string {
	return r.securityLedgerID
}

// AddEntry增加账户条目
func (r *SecurityLedger) AddEntry(entry SecurityLedgerEntry) {
	r.entries = append(r.entries, entry)
}

// Settle清算账目
func (r *SecurityLedger) Settle() {
	balance := make(map[string]uint64)
	for _, entry := range r.entries {
		balance[entry.SecurityID] += entry.Count
	}
	r.balance = balance
}

// GetBalance获取Settle清算结果
func (r SecurityLedger) GetBalance() map[string]uint64 {
	return r.balance
}
