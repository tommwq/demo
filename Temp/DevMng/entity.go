package main

type ConvertMaps_map struct {
	ID      string `xml:"id,attr"`
	MapID   string `xml:"mapid,attr"`
	Request struct {
		To string `xml:"to,attr"`
	} `xml:"request"`
	Response struct {
		To string `xml:"to,attr"`
	} `xml:"response"`
	Description string `xml:"desc,attr"`
}

type JZTradeXML struct {
	ConvertMaps struct {
		Map []ConvertMaps_map `xml:"map"`
	}
}
