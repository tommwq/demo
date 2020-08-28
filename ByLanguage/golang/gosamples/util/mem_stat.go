package util

import (
	"fmt"
	"runtime"
)

// DisplayMemStats 将MemStats对象转换为易读的字符串
func DisplayMemStats(m *runtime.MemStats) string {
	return fmt.Sprintf(
		`
Alloc: %v,
TotalAlloc: %v,
Sys: %v,
Lookups: %v,
Mallocs: %v,
Frees: %v,
HeapAlloc: %v,
HeapSys: %v,
HeapIdle: %v,
HeapInuse: %v,
HeapReleased: %v,
HeapObjects: %v,
StackInuse: %v,
StackSys: %v,
MSpanInuse: %v,
MSpanSys: %v,
MCacheInuse: %v,
MCacheSys: %v,
BuckHashSys: %v,
GCSys: %v,
OtherSys: %v,
NextGC: %v,
LastGC: %v,
PauseTotalNs: %v,
NumGC: %v,
GCCPUFraction: %v,
EnableGC: %v,
DebugGC: %v
`,

		m.Alloc,
		m.TotalAlloc,
		m.Sys,
		m.Lookups,
		m.Mallocs,
		m.Frees,
		m.HeapAlloc,
		m.HeapSys,
		m.HeapIdle,
		m.HeapInuse,
		m.HeapReleased,
		m.HeapObjects,
		m.StackInuse,
		m.StackSys,
		m.MSpanInuse,
		m.MSpanSys,
		m.MCacheInuse,
		m.MCacheSys,
		m.BuckHashSys,
		m.GCSys,
		m.OtherSys,
		m.NextGC,
		m.LastGC,
		m.PauseTotalNs,
		m.NumGC,
		m.GCCPUFraction,
		m.EnableGC,
		m.DebugGC)

}

// MemStatsDiff 显示两个MemStats的差异
func MemStatsDiff(l, r *runtime.MemStats) *runtime.MemStats {
	m := &runtime.MemStats{}
	m.Alloc = l.Alloc - r.Alloc
	m.TotalAlloc = l.TotalAlloc - r.TotalAlloc
	m.Sys = l.Sys - r.Sys
	m.Lookups = l.Lookups - r.Lookups
	m.Mallocs = l.Mallocs - r.Mallocs
	m.Frees = l.Frees - r.Frees
	m.HeapAlloc = l.HeapAlloc - r.HeapAlloc
	m.HeapSys = l.HeapSys - r.HeapSys
	m.HeapIdle = l.HeapIdle - r.HeapIdle
	m.HeapInuse = l.HeapInuse - r.HeapInuse
	m.HeapReleased = l.HeapReleased - r.HeapReleased
	m.HeapObjects = l.HeapObjects - r.HeapObjects
	m.StackInuse = l.StackInuse - r.StackInuse
	m.StackSys = l.StackSys - r.StackSys
	m.MSpanInuse = l.MSpanInuse - r.MSpanInuse
	m.MSpanSys = l.MSpanSys - r.MSpanSys
	m.MCacheInuse = l.MCacheInuse - r.MCacheInuse
	m.MCacheSys = l.MCacheSys - r.MCacheSys
	m.BuckHashSys = l.BuckHashSys - r.BuckHashSys
	m.GCSys = l.GCSys - r.GCSys
	m.OtherSys = l.OtherSys - r.OtherSys
	m.NextGC = l.NextGC - r.NextGC
	m.LastGC = l.LastGC - r.LastGC
	m.PauseTotalNs = l.PauseTotalNs - r.PauseTotalNs
	m.NumGC = l.NumGC - r.NumGC
	m.GCCPUFraction = l.GCCPUFraction - r.GCCPUFraction
	return m
}
