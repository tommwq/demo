package util

import (
	"runtime"
	"sync"
	"time"
)

type RoutineRecord struct {
	ID        int64
	StartTime time.Time
	StopTime  time.Time
	File      string
	Line      int
	Function  string
}

type RoutineRecordTable struct {
	idGenerator IDGenerator
	records     map[int64]*RoutineRecord
	rwlock      sync.RWMutex
}

func (r *RoutineRecordTable) Enter() int64 {
	id, _ := r.idGenerator.NextID()
	pc, file, line, ok := runtime.Caller(1)
	var funcname string
	if ok {
		funcname = runtime.FuncForPC(pc).Name()
	}

	record := &RoutineRecord{
		ID:        id,
		StartTime: time.Now(),
		File:      file,
		Line:      line,
		Function:  funcname,
	}
	r.enter(record)
	return record.ID
}

func (r *RoutineRecordTable) Leave(id int64) {
	now := time.Now()
	if record, ok := r.get(id); ok {
		r.leave(record, now)
	}
}

func (r *RoutineRecordTable) GetCopy() map[int64]RoutineRecord {
	result := make(map[int64]RoutineRecord)

	r.rwlock.RLock()
	for k, v := range r.records {
		result[k] = *v
	}
	r.rwlock.RUnlock()

	return result
}

func (r *RoutineRecordTable) Check(now time.Time, timeout time.Duration) map[int64]RoutineRecord {
	records := make(map[int64]RoutineRecord)

	for id, record := range r.GetCopy() {
		if !record.StopTime.IsZero() {
			continue
		}
		if record.StartTime.Add(timeout).Before(now) {
			records[id] = record
		}
	}

	return records
}

func (r *RoutineRecordTable) enter(record *RoutineRecord) {
	r.rwlock.Lock()
	r.records[record.ID] = record
	r.rwlock.Unlock()
}

func (r *RoutineRecordTable) leave(record *RoutineRecord, t time.Time) {
	r.rwlock.Lock()
	defer r.rwlock.Unlock()

	if record, ok := r.records[record.ID]; ok {
		record.StopTime = t
	}
}

func (r *RoutineRecordTable) get(id int64) (*RoutineRecord, bool) {
	r.rwlock.Lock()
	defer r.rwlock.Unlock()

	record, ok := r.records[id]
	return record, ok
}

func NewRoutineRecordTable() *RoutineRecordTable {
	return &RoutineRecordTable{
		idGenerator: NewSimpleIDGenerator(),
		records:     make(map[int64]*RoutineRecord),
	}
}

/*
func main() {

	rrt := NewRoutineRecordTable()

	for i := 0; i < 300; i++ {
		go func() {
			id := rrt.Enter()
			defer rrt.Leave(id)

			time.Sleep(time.Second)
		}()
	}

	result := rrt.Check(time.Now(), time.Microsecond)
	for _, v := range result {
		fmt.Println(v)
	}
}
*/
