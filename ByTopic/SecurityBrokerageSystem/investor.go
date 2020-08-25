package main

import (
	"time"
)

type InvestorID struct {
	Name         string
	IDFileType   string
	IDFileNumber string
}

const (
	GENDER_UNKNOWN = iota
	GENDER_MALE
	GENDER_FEMALE
)

type Investor struct {
	InvestorID      InvestorID
	Citizenship     string
	People          string
	Education       int
	MajorIDFile     IDFile
	MinorIDFIle     IDFile
	BirthDay        time.Time
	Gender          int
	OrgnizationType string
	LegalPerson     string
	Telephone       string
	MobilePhone     string
	Address         string
	PostCode        string
	EMail           string
	Career          int
	AccountOpenDate time.Time
	AccountOpenType string
}
