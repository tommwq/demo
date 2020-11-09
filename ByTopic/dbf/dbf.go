package main

import (
	"encoding/binary"
	"errors"
	"fmt"
	"io/ioutil"
	"strconv"
	"time"

	"golang.org/x/text/encoding"
	"golang.org/x/text/encoding/simplifiedchinese"
)

type DBFFileType IntegerValue
type DBFTableFlag IntegerValue
type DBFFieldFlag IntegerValue

func registerDBFFileType(number uint8, description string) {
	DBFFileTypeTable[number] = DBFFileType{number, description}
}

func registerDBFTableFlag(number uint8, description string) {
	DBFTableFlagTable[number] = DBFTableFlag{number, description}
}

func registerDBFFieldFlag(number uint8, description string) {
	DBFFieldFlagTable[number] = DBFFieldFlag{number, description}
}

func registerDBFFieldReader(flag string, reader DBFFieldReader) {
	DBFFieldReaderTable[flag] = reader
}

func init() {

	registerDBFFileType(0x02, "FoxBASE")
	registerDBFFileType(0x03, "FoxBASE+/Dbase III plus, no memo")
	registerDBFFileType(0x30, "Visual FoxPro")
	registerDBFFileType(0x31, "Visual FoxPro, autoincrement enabled")
	registerDBFFileType(0x32, "Visual FoxPro with field type Varchar or Varbinary")
	registerDBFFileType(0x43, "dBASE IV SQL table files, no memo")
	registerDBFFileType(0x63, "dBASE IV SQL system files, no memo")
	registerDBFFileType(0x83, "FoxBASE+/dBASE III PLUS, with memo")
	registerDBFFileType(0x8B, "dBASE IV with memo")
	registerDBFFileType(0xCB, "dBASE IV SQL table files, with memo")
	registerDBFFileType(0xF5, "FoxPro 2.x (or earlier) with memo")
	registerDBFFileType(0xE5, "HiPer-Six format with SMT memo file")
	registerDBFFileType(0xFB, "FoxBASE")

	registerDBFTableFlag(0x01, "file has a structural .cdx")
	registerDBFTableFlag(0x02, "file has a Memo field")
	registerDBFTableFlag(0x04, "file has a database (.dbc)")

	registerDBFFieldFlag(0x01, "System Column (not visible to user)")
	registerDBFFieldFlag(0x02, "Column can store null values")
	registerDBFFieldFlag(0x04, "Binary column (for CHAR and MEMO only)")
	registerDBFFieldFlag(0x06, "(0x02+0x04) When a field is NULL and binary (Integer, Currency, and Character/Memo fields)")
	registerDBFFieldFlag(0x0C, "Column is autoincrementing")

	registerDBFFieldReader("C", dbfCharacterFieldReader{})
	registerDBFFieldReader("Y", dbfCurrencyFieldReader{})
	registerDBFFieldReader("N", dbfNumericFieldReader{})
	registerDBFFieldReader("F", dbfFloatFieldReader{})
	registerDBFFieldReader("D", dbfDateFieldReader{})
	registerDBFFieldReader("T", dbfDateTimeFieldReader{})
	registerDBFFieldReader("B", dbfDoubleFieldReader{})
	registerDBFFieldReader("I", dbfIntegerFieldReader{})
	registerDBFFieldReader("L", dbfLogicalFieldReader{})
	registerDBFFieldReader("M", dbfMemoFieldReader{})
	registerDBFFieldReader("G", dbfGeneralFieldReader{})
	registerDBFFieldReader("P", dbfPictureFieldReader{})
	registerDBFFieldReader("+", dbfAutoincrementFieldReader{})
	registerDBFFieldReader("@", dbfTimestampFieldReader{})
	registerDBFFieldReader("V", dbfVarcharFieldReader{})
}

var (
	DBFFileTypeTable    = make(map[uint8]DBFFileType)
	DBFTableFlagTable   = make(map[uint8]DBFTableFlag)
	DBFFieldFlagTable   = make(map[uint8]DBFFieldFlag)
	DBFFieldReaderTable = make(map[string]DBFFieldReader)

	ErrMeetHeaderTerminator = errors.New("meet header terminator")
	ErrInvalidDBFFieldType  = errors.New("invalid dbf field type")
	DBFFieldFlagUnknown     = DBFFieldFlag{0, "unknown dbf field flag"}
)

type DBFField interface {
	Name() string
	Length() uint8
	FieldType() string
	Read(reader Reader, decoder *encoding.Decoder) (interface{}, error)
}

type DBFFieldReader interface {
	ReadField(Reader, DBFField, *encoding.Decoder) (interface{}, error)
}

type dbfCharacterFieldReader struct{}

func (r dbfCharacterFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	return reader.ReadString(uint(field.Length()), decoder)

}

type dbfCurrencyFieldReader struct{}

// 按照int64读取，然后除以10000。
func (r dbfCurrencyFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	if value, err := reader.ReadInt64(); err != nil {
		return 0.0, err
	} else {
		return float64(value) / 10000.0, nil
	}
}

type dbfNumericFieldReader struct{}

// Numeric是用ascii表示的数字
func (r dbfNumericFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	if value, err := reader.ReadUTF8String(uint(field.Length())); err != nil {
		return 0.0, nil
	} else {
		return strconv.ParseFloat(value, 64)
	}
}

type dbfFloatFieldReader struct{}

func (r dbfFloatFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	return reader.ReadFloat32()
}

type dbfDateFieldReader struct{}

// 64位，32位年，16位月，16位日。
func (r dbfDateFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	if value, err := reader.ReadUint64(); err != nil {
		return time.Now(), err
	} else {
		return time.Date(int(value>>32), time.Month(int(value>>16)%65536), int(value%65536), 0, 0, 0, 0, time.Local), nil
	}
}

type dbfDateTimeFieldReader struct{}

func (r dbfDateTimeFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	return reader.ReadByteArray(uint(field.Length()))
}

type dbfDoubleFieldReader struct{}

func (r dbfDoubleFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	return reader.ReadFloat64()
}

type dbfIntegerFieldReader struct{}

func (r dbfIntegerFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	return reader.ReadInt32()
}

type dbfLogicalFieldReader struct{}

func (r dbfLogicalFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	if value, err := reader.ReadUTF8String(1); err != nil {
		return false, err
	} else {
		return value == "1" || value == "T" || value == "t" || value == "Y" || value == "y", nil
	}
}

type dbfMemoFieldReader struct{}

func (r dbfMemoFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	return reader.ReadByteArray(uint(field.Length()))
}

type dbfGeneralFieldReader struct{}

func (r dbfGeneralFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	return reader.ReadByteArray(uint(field.Length()))
}

type dbfPictureFieldReader struct{}

func (r dbfPictureFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	return reader.ReadByteArray(uint(field.Length()))
}

type dbfAutoincrementFieldReader struct{}

func (r dbfAutoincrementFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	return reader.ReadByteArray(uint(field.Length()))
}

type dbfTimestampFieldReader struct{}

func (r dbfTimestampFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	return reader.ReadByteArray(uint(field.Length()))
}

type dbfVarcharFieldReader struct{}

func (r dbfVarcharFieldReader) ReadField(reader Reader, field DBFField, decoder *encoding.Decoder) (interface{}, error) {
	return reader.ReadByteArray(uint(field.Length()))
}

type dbfField struct {
	name      string
	length    uint8
	fieldType string
	reader    DBFFieldReader
}

func (r dbfField) Name() string {
	return r.name
}

func (r dbfField) FieldType() string {
	return r.fieldType
}

func (r dbfField) Length() uint8 {
	return r.length
}

func (r dbfField) Read(reader Reader, decoder *encoding.Decoder) (interface{}, error) {
	return r.reader.ReadField(reader, r, decoder)
}

func ParseDBFField(name string, fieldType string, length uint8) (DBFField, error) {
	reader, ok := DBFFieldReaderTable[fieldType]
	if !ok {
		return nil, ErrInvalidDBFFieldType
	}

	return dbfField{name, length, fieldType, reader}, nil
}

type DBFFieldSubrecord struct {
	Name      string
	Type      DBFField
	Length    uint
	FieldFlag DBFFieldFlag
}

type DBFFileHeader struct {
	FileType          DBFFileType
	LastUpdateDate    time.Time
	RecordCount       uint32
	FirstRecordOffset uint16
	RecordLength      uint16
	TableFlags        []DBFTableFlag
	CodePageMark      uint8
	FieldSubrecords   []DBFFieldSubrecord
}

type DBFFile struct {
	FileName string
	buffer   []byte
	Header   DBFFileHeader
}

func ParseDBFFileHeader(reader Reader) (DBFFileHeader, error) {
	header := DBFFileHeader{}
	var err error

	var fileType uint8
	if fileType, err = reader.ReadUint8(); err != nil {
		return header, err
	}
	header.FileType = DBFFileTypeTable[fileType]

	var year, month, day uint8
	if year, err = reader.ReadUint8(); err != nil {
		return header, err
	}

	if month, err = reader.ReadUint8(); err != nil {
		return header, err
	}

	if day, err = reader.ReadUint8(); err != nil {
		return header, err
	}

	header.LastUpdateDate = time.Date(int(year)+2000, time.Month(month), int(day), 0, 0, 0, 0, time.Local)

	if header.RecordCount, err = reader.ReadUint32(); err != nil {
		return header, err
	}

	if header.FirstRecordOffset, err = reader.ReadUint16(); err != nil {
		return header, err
	}

	if header.RecordLength, err = reader.ReadUint16(); err != nil {
		return header, err
	}

	// skip reserved bytes
	_, _ = reader.ReadByteArray(16)

	var tableFlags uint8
	if tableFlags, err = reader.ReadUint8(); err != nil {
		return header, err
	}

	header.TableFlags = make([]DBFTableFlag, 0, 3)
	for value, flag := range DBFTableFlagTable {
		if (value & tableFlags) > 0 {
			header.TableFlags = append(header.TableFlags, flag)
		}
	}

	if header.CodePageMark, err = reader.ReadUint8(); err != nil {
		return header, err
	}

	// skip reserved
	_, _ = reader.ReadUint16()

	header.FieldSubrecords = make([]DBFFieldSubrecord, 0)
	var subrecord DBFFieldSubrecord
	for {
		subrecord, err = ParseDBFFieldSubrecord(reader)
		if err == ErrMeetHeaderTerminator {
			break
		} else if err != nil {
			return header, err
		} else {
			header.FieldSubrecords = append(header.FieldSubrecords, subrecord)
		}
	}

	reader.ReadUint8() // eat header terminator

	// for Visual Fox Pro, eat header tailer
	if 0x30 <= fileType && fileType <= 0x32 {
		reader.ReadByteArray(263)
	}

	return header, nil
}

func NewDBFFile(fileName string) (*DBFFile, error) {
	buffer, err := ioutil.ReadFile(fileName)
	if err != nil {
		return nil, err
	}

	return &DBFFile{
		FileName: fileName,
		buffer:   buffer,
	}, nil
}

func (r *DBFFile) Parse() error {
	var err error
	reader := NewReader(r.buffer, binary.LittleEndian)
	r.Header, err = ParseDBFFileHeader(reader)
	return err
}

func (r DBFFile) Fields() []DBFField {
	fields := make([]DBFField, 0, len(r.Header.FieldSubrecords))
	for _, subrecord := range r.Header.FieldSubrecords {
		fields = append(fields, subrecord.Type)
	}
	return fields
}

func (r DBFFile) Records() []map[string]interface{} {
	decoder := simplifiedchinese.GBK.NewDecoder()
	fields := r.Fields()
	count := int(r.Header.RecordCount)
	length := int(r.Header.RecordLength)
	records := make([]map[string]interface{}, 0)

	for i := 0; i < count; i++ {
		record := make(map[string]interface{})
		offset := int(r.Header.FirstRecordOffset) + i*length
		reader := NewReader(r.buffer[offset:offset+length], binary.LittleEndian)
		for _, field := range fields {
			value, err := field.Read(reader, decoder)
			fmt.Printf("%v %v\n", field.Name(), value)
			if err != nil {
				panic(err)
			}

			record[field.Name()] = value
		}
		records = append(records, record)
	}

	return records
}

func ParseDBFFieldSubrecord(reader Reader) (DBFFieldSubrecord, error) {
	record := DBFFieldSubrecord{}

	if flag, err := reader.ReadUint8(); err != nil {
		return record, err
	} else {
		reader.UnreadUint8()
		if flag == 0x0d {
			return record, ErrMeetHeaderTerminator
		}
	}

	var err error
	if record.Name, err = reader.ReadUTF8String(11); err != nil {
		return record, err
	}

	var fieldType string
	if fieldType, err = reader.ReadUTF8String(1); err != nil {
		return record, err
	}

	// ignore
	reader.ReadUint32()
	var length uint8
	if length, err = reader.ReadUint8(); err != nil {
		return record, err
	}
	record.Length = uint(length)

	if record.Type, err = ParseDBFField(record.Name, fieldType, length); err != nil {
		return record, err
	}

	reader.ReadUint8() // number of decimal places
	record.FieldFlag = DBFFieldFlagUnknown
	if flag, err := reader.ReadUint8(); err != nil {
		return record, err
	} else {
		for value, fieldFlag := range DBFFieldFlagTable {
			if value == flag {
				record.FieldFlag = fieldFlag
			}
		}
	}
	reader.ReadUint32() // autoincrement next value
	reader.ReadUint8()  // autoincrement step value
	reader.ReadUint64() // reserved

	return record, nil
}

func test(dbfFileName string) {
	dbfFile, err := NewDBFFile(dbfFileName)
	if err != nil {
		panic(err)
	}

	err = dbfFile.Parse()
	if err != nil {
		panic(err)
	}

	for _, subrecord := range dbfFile.Header.FieldSubrecords {
		fmt.Printf("%v %v %v %v\n", subrecord.Name, subrecord.Length, subrecord.Type.Name(), subrecord.Type.Length())
	}

	for number, record := range dbfFile.Records() {
		fmt.Printf("%v %v\n", number, record)
	}
}

func main() {
	dbfFileName := "D:/workspace/temporary/20200317_org_test/NQXX.DBF"
	test(dbfFileName)
	println("ok")
}
