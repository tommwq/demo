import java.io.*;
import java.util.*;
import java.nio.*;

// https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html

class AccessFlags {
    public static int Public = 1;
    public static int Final = 0x10;
    public static int Super = 0x20;
    public static int Interface = 0x200;
    public static int Abstract = 0x400;
    public static int Synthetic = 0x1000;
    public static int Annotation = 0x2000;
    public static int Enum = 0x4000;
}

class ConstantInfoRecord {
    public byte tag;
    public short index1;  // class_index, descriptor_index, reference_index, bootstrap_method_attr_index
    public short index2;  // name_and_type_index, string_index, name_index
    public byte[] bytes = new byte[0];
    public byte referenceKind;

    public String toString() {
        return String.format("tag: %d\nindex1: %d\nindex2: %d\nreferencekind: %d\nbytes: %s\n", tag, index1, index2, referenceKind, new String(bytes));
    }
}

interface ConstantInfoBodyParser {
    int parse(ByteBuffer buffer, ConstantInfoRecord record);
}

class ConstantInfoBodyParser_Invalid implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        throw new RuntimeException("invalid tag: " + record.tag + ", position: " + buffer.position());
    }
}

class ConstantInfoBodyParser_MethodRef implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        short index1 = buffer.getShort();
        short index2 = buffer.getShort();
        record.index1 = index1;
        record.index2 = index2;
        return 4;
    }
}

class ConstantInfoBodyParser_UTF8 implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        short length = buffer.getShort();
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        record.bytes = bytes;
        return 2 + bytes.length;
    }
}

class ConstantInfoBodyParser_Integer implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        byte[] bytes = new byte[4];
        buffer.get(bytes);
        record.bytes = bytes;
        return 4;
    }
}

class ConstantInfoBodyParser_Float extends ConstantInfoBodyParser_Integer {}
    
class ConstantInfoBodyParser_Long implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        byte[] bytes = new byte[8];
        buffer.get(bytes);
        record.bytes = bytes;
        return 8;
    }
}

class ConstantInfoBodyParser_Double extends ConstantInfoBodyParser_Long {}

class ConstantInfoBodyParser_InvokeDynamic implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        short bootstrapMethodAttrIndex = buffer.getShort();
        short nameAndTypeIndex = buffer.getShort();
        record.index1 = bootstrapMethodAttrIndex;
        record.index2 = nameAndTypeIndex;
        return 4;
    }
}

class ConstantInfoBodyParser_NameAndType implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        short index2 = buffer.getShort(); // name_index
        short index1 = buffer.getShort();
        record.index1 = index1;
        record.index2 = index2;
        return 4;
    }
}

class ConstantInfoBodyParser_FieldRef extends ConstantInfoBodyParser_MethodRef {}
class ConstantInfoBodyParser_InterfaceMethodRef extends ConstantInfoBodyParser_MethodRef {}
class ConstantInfoBodyParser_Class extends ConstantInfoBodyParser_String {}

class ConstantInfoBodyParser_String implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        short index2 = buffer.getShort();
        record.index2 = index2;
        return 2;
    }
}

class ConstantInfoBodyParser_MethodHandle implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        byte referenceKind = buffer.get();
        short referenceIndex = buffer.getShort();
        record.referenceKind = referenceKind;
        record.index1 = referenceIndex;
        return 3;
    }
}

class ConstantInfoBodyParser_MethodType implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        short descriptorIndex = buffer.getShort();
        record.index1 = descriptorIndex;
        return 2;
    }
}

class ConstantInfoParser {
    private static Map<Integer, ConstantInfoBodyParser> bodyParsers = new HashMap<>();

    static {
        bodyParsers.put(1, new ConstantInfoBodyParser_UTF8());
        bodyParsers.put(3, new ConstantInfoBodyParser_Integer());
        bodyParsers.put(4, new ConstantInfoBodyParser_Float());
        bodyParsers.put(5, new ConstantInfoBodyParser_Long());
        bodyParsers.put(6, new ConstantInfoBodyParser_Double());
        bodyParsers.put(7, new ConstantInfoBodyParser_Class());
        bodyParsers.put(8, new ConstantInfoBodyParser_String());
        bodyParsers.put(9, new ConstantInfoBodyParser_FieldRef());
        bodyParsers.put(10, new ConstantInfoBodyParser_MethodRef());
        bodyParsers.put(11, new ConstantInfoBodyParser_InterfaceMethodRef());
        bodyParsers.put(12, new ConstantInfoBodyParser_NameAndType());
        bodyParsers.put(15, new ConstantInfoBodyParser_MethodHandle());
        bodyParsers.put(16, new ConstantInfoBodyParser_MethodType());
        bodyParsers.put(18, new ConstantInfoBodyParser_InvokeDynamic());
    }

    private ConstantInfoRecord record;
    
    public int parse(ByteBuffer buffer) {
        ConstantInfoBodyParser parser = new ConstantInfoBodyParser_Invalid();
        record = new ConstantInfoRecord();
        record.tag = buffer.get();
        int tag = record.tag;
            
        if (bodyParsers.containsKey(tag)) {
            parser = bodyParsers.get(tag);
        }
        
        return 1 + parser.parse(buffer, record);
    }

    public ConstantInfoRecord result() {
        return record;
    }
}

class AttributeInfo {
    private AttributeInfo(){}
    
    public short attributeNameIndex;
    public int attributeLength;
    public byte[] info;
    
    public static class Parser {
        public AttributeInfo parse(ByteBuffer buffer) {
            AttributeInfo a = new AttributeInfo();
            a.attributeNameIndex = buffer.getShort();
            a.attributeLength = buffer.getInt();
            a.info = new byte[a.attributeLength];
            buffer.get(a.info);
            return a;
        }
    }
}

class FieldInfo {
    protected FieldInfo(){}
    
    public short accessFlags;
    public short nameIndex;
    public short descriptorIndex;
    public short attributesCount;
    public AttributeInfo attributes[];

    public static class Parser {
        public FieldInfo parse(ByteBuffer buffer) {
            FieldInfo f = new FieldInfo();

            f.accessFlags = buffer.getShort();
            f.nameIndex = buffer.getShort();
            f.descriptorIndex = buffer.getShort();
            f.attributesCount = buffer.getShort();
            f.attributes = new AttributeInfo[f.attributesCount];
            for (short i = 0; i < f.attributesCount; i++) {
                f.attributes[i] = new AttributeInfo.Parser().parse(buffer);
            }

            return f;
        }
    }
}

class MethodInfo extends FieldInfo {
    public static class Parser {
        public MethodInfo parse(ByteBuffer buffer) {
            MethodInfo f = new MethodInfo();

            f.accessFlags = buffer.getShort();
            f.nameIndex = buffer.getShort();
            f.descriptorIndex = buffer.getShort();
            f.attributesCount = buffer.getShort();
            f.attributes = new AttributeInfo[f.attributesCount];
            for (short i = 0; i < f.attributesCount; i++) {
                f.attributes[i] = new AttributeInfo.Parser().parse(buffer);
            }

            return f;
        }
    }
}
    
public class PrintClassFile {
    public static void main(String... args) throws Exception {
        String classFilename = "PrintClassFile.class";

        FileInputStream inputStream = new FileInputStream(classFilename);
        int fileSize = inputStream.available();
        byte[] rawData = new byte[fileSize];
        if (inputStream.read(rawData) != fileSize) {
            throw new RuntimeException("read file error");
        }
        inputStream.close();

        ByteBuffer buffer = ByteBuffer.wrap(rawData)
            .order(ByteOrder.BIG_ENDIAN);

        final String CRLF = "\r\n";
        int magic;
        short minorVersion;
        short majorVersion;
        short accessFlags;
        short thisClass;
        short superClass;
        ConstantInfoRecord[] constants = new ConstantInfoRecord[0];
        short[] interfaces = new short[0];
        FieldInfo[] fields = new FieldInfo[0];
        MethodInfo[] methods = new MethodInfo[0];
        AttributeInfo[] attributes = new AttributeInfo[0];

        magic = buffer.getInt();
        minorVersion = buffer.getShort();
        majorVersion = buffer.getShort();
        int constantPoolCount = buffer.getShort();

        ConstantInfoParser parser = new ConstantInfoParser();
        constants = new ConstantInfoRecord[constantPoolCount];
        for (int i = 0; i < constantPoolCount - 1; i++) {
            parser.parse(buffer);
            // System.out.println((i + 1) + "/" + (constantPoolCount - 1));
            // System.out.println(parser.result());
            constants[i] = parser.result();
        }

        accessFlags = buffer.getShort();
        thisClass = buffer.getShort();
        superClass = buffer.getShort();
        int interfacesCount = buffer.getShort();
        interfaces = new short[interfacesCount];
        for (int i = 0; i < interfacesCount; i++) {
            interfaces[i] = buffer.getShort();
        }
        
        int fieldsCount = buffer.getShort();
        fields = new FieldInfo[fieldsCount];
        for (int i = 0; i < fieldsCount; i++) {
            fields[i] = new FieldInfo.Parser().parse(buffer);
        }

        int methodsCount = buffer.getShort();
        methods = new MethodInfo[methodsCount];
        for (int i = 0; i < methodsCount; i++) {
            methods[i] = new MethodInfo.Parser().parse(buffer);
        }

        int attributesCount = buffer.getShort();
        attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            attributes[i] = new AttributeInfo.Parser().parse(buffer);
        }

        // StringBuilder stringBuilder = new StringBuilder();
        // stringBuilder.append("magic: ")
        //     .append(String.format("%X", magic))
        //     .append(CRLF)
        //     .append("minor: ")
        //     .append(minorVersion)
        //     .append(CRLF)
        //     .append("major: ")
        //     .append(majorVersion)
        //     .append(CRLF);        
        // System.out.printf(stringBuilder.toString());

        Arrays.stream(constants)
            .filter((x) -> x != null && x.tag == 1 && x.bytes != null)
            .forEach((x) -> System.out.println(new String(x.bytes)));

        System.out.println("file size: " + fileSize + " position: " + buffer.position());

        // ConstantInfoRecord record1 = constants[thisClass];
        // ConstantInfoRecord record2 = constants[record1.index2];
        // ConstantInfoRecord record2 = constants[record1.index2];
        
        // System.out.println(record1 + "" + record2);
    }
}
