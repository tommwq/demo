package main

import (
	"time"
)

func main() {
	ledger := MakeSecurityLedger("LEDGER 1", ACCOUNT_TYPE_RMB_NORMAL_STOCK)
	entries := []SecurityLedgerEntry{
		SecurityLedgerEntry{
			Sequence:   1,
			SecurityID: "1",
			Time:       time.Now(),
			Count:      100,
			Reason:     "",
		},
		SecurityLedgerEntry{
			Sequence:   1,
			SecurityID: "1",
			Time:       time.Now(),
			Count:      200,
			Reason:     "",
		},
		SecurityLedgerEntry{
			Sequence:   1,
			SecurityID: "1",
			Time:       time.Now(),
			Count:      300,
			Reason:     "",
		},
	}

	for _, entry := range entries {
		ledger.AddEntry(entry)
	}

	ledger.Settle()
	for security, count := range ledger.GetBalance() {
		println(security, count)
	}
}
