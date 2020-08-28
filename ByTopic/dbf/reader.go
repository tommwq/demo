package main

import (
	"bytes"
	"encoding/binary"
	"strings"

	"golang.org/x/text/encoding"
)

type Reader interface {
	ReadUint8() (uint8, error)
	ReadInt8() (int8, error)
	ReadUint16() (uint16, error)
	ReadInt16() (int16, error)
	ReadUint32() (uint32, error)
	ReadInt32() (int32, error)
	ReadUint64() (uint64, error)
	ReadInt64() (int64, error)
	ReadFloat32() (float32, error)
	ReadFloat64() (float64, error)
	ReadByteArray(length uint) ([]byte, error)
	ReadUTF8String(byteLength uint) (string, error)
	ReadString(byteLength uint, decoder *encoding.Decoder) (string, error)
	UnreadUint8() error
}

type reader struct {
	reader    *bytes.Buffer
	byteOrder binary.ByteOrder
}

func NewReader(buffer []byte, byteOrder binary.ByteOrder) Reader {
	return &reader{
		reader:    bytes.NewBuffer(buffer),
		byteOrder: byteOrder,
	}
}

func (r *reader) ReadUint8() (uint8, error) {
	var result uint8
	err := binary.Read(r.reader, r.byteOrder, &result)
	return result, err
}

func (r *reader) ReadInt8() (int8, error) {
	var result int8
	err := binary.Read(r.reader, r.byteOrder, &result)
	return result, err
}

func (r *reader) ReadUint16() (uint16, error) {
	var result uint16
	err := binary.Read(r.reader, r.byteOrder, &result)
	return result, err
}

func (r *reader) ReadInt16() (int16, error) {
	var result int16
	err := binary.Read(r.reader, r.byteOrder, &result)
	return result, err
}

func (r *reader) ReadUint32() (uint32, error) {
	var result uint32
	err := binary.Read(r.reader, r.byteOrder, &result)
	return result, err
}

func (r *reader) ReadInt32() (int32, error) {
	var result int32
	err := binary.Read(r.reader, r.byteOrder, &result)
	return result, err
}

func (r *reader) ReadUint64() (uint64, error) {
	var result uint64
	err := binary.Read(r.reader, r.byteOrder, &result)
	return result, err
}

func (r *reader) ReadInt64() (int64, error) {
	var result int64
	err := binary.Read(r.reader, r.byteOrder, &result)
	return result, err
}

func (r *reader) ReadFloat32() (float32, error) {
	var result float32
	err := binary.Read(r.reader, r.byteOrder, &result)
	return result, err
}

func (r *reader) ReadFloat64() (float64, error) {
	var result float64
	err := binary.Read(r.reader, r.byteOrder, &result)
	return result, err
}

func (r *reader) ReadByteArray(length uint) ([]byte, error) {
	result := make([]byte, length)
	_, err := r.reader.Read(result)
	return result, err
}

func (r *reader) UnreadUint8() error {
	return r.reader.UnreadByte()
}

func (r *reader) ReadUTF8String(byteLength uint) (string, error) {
	buffer, err := r.ReadByteArray(byteLength)
	if err != nil {
		return "", err
	}

	return strings.Trim(string(buffer), "\000 "), err
}

func (r *reader) ReadString(byteLength uint, decoder *encoding.Decoder) (string, error) {
	var (
		result string
		buffer []byte
		err    error
		utf8   []byte
	)
	buffer, err = r.ReadByteArray(byteLength)
	if err != nil {
		return result, err
	}

	utf8, err = decoder.Bytes(buffer)
	if err != nil {
		return result, err
	}

	err = binary.Read(bytes.NewReader(utf8), r.byteOrder, &result)
	return result, err
}
