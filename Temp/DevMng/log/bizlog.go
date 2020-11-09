package log

import (
	"encoding/json"
	"time"
)

type BizLog struct {
	Time        string      `json:"time"`
	Service     string      `json:"service"`
	Method      string      `json:"method"`
	Request     interface{} `json:"req"`
	Rsponse     interface{} `json:"rsp"`
	RPCContext  interface{} `json:"rpcContext"`
	Attachments interface{} `json:"attachments"`
}

func (r BizLog) IsRequest() bool {
	return len(r.Request) > 0
}

func (r BizLog) IsResponse() bool {
	return len(r.Response) > 0
}

func (r BizLog) ResultMSG() string {
	result, ok := r.Response["result"]
	if !ok {
		return ""
	}
	if len(result) == 0 {
		return ""
	}
	message, ok := result[0]["msg"]
	if !ok {
		return ""
	}
	return message
}

func (r BizLog) ResultCode() string {
	result, ok := r.Response["result"]
	if !ok {
		return ""
	}
	if len(result) == 0 {
		return ""
	}
	code, ok := result[0]["code"]
	if !ok {
		return ""
	}
	return code
}

func (r BizLog) ResultData() map[string]string {
	data, ok := r.Response["data"]
	if !ok {
		return make(map[string]string)
	}
	if len(data) == 0 {
		return make(map[string]string)
	}
	return data[0]
}

func (r BizLog) Context(key string) *string {
	m, ok := r.RPCContext.(map[string]string)
	if !ok {
		return nil
	}
	v, ok := m[key]
	if !ok {
		return nil
	}
	return &v
}

func (r BizLog) Attachment(key string) *string {
	m, ok := r.Attachments.(map[string]string)
	if !ok {
		return nil
	}
	v, ok := m[key]
	if !ok {
		return nil
	}
	return &v
}

func (r BizLog) SoftName() *string {
	return r.Attachment("softName")
}

func (r BizLog) Session() *string {
	return r.Attachment("session")
}
func (r BizLog) SysVer() *string {
	return r.Attachment("sysVer")
}
func (r BizLog) UserCode() *string {
	return r.Attachment("userCode")
}
func (r BizLog) MAC() *string {
	return r.Attachment("mac")
}
func (r BizLog) Path() *string {
	return r.Attachment("path")
}
func (r BizLog) HWID() *string {
	return r.Attachment("hwID")
}
func (r BizLog) Mobile() *string {
	return r.Attachment("mobile")
}
func (r BizLog) IP() *string {
	return r.Attachment("ip")
}
func (r BizLog) NetAddr() *string {
	return r.Attachment("netAddr")
}
func (r BizLog) DeviceVers() *string {
	return r.Attachment("deviceVers")
}
func (r BizLog) TraceID() *string {
	return r.Context("traceId")
}
func (r BizLog) ParentID() *string {
	return r.Context("parentId")
}
