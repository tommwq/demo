import java.io.*;
import java.util.*;
import java.nio.*;
import java.util.stream.*;
import java.util.logging.*;

// https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html    
public class JavaClassFile {
    private int magic;
    private short minorVersion;
    private short majorVersion;
    private short accessFlags;
    private short thisClass;
    private short superClass;
    private List<ConstantInfoRecord> constants;
    private List<Short> interfaces;
    private List<FieldInfo> fields;
    private List<MethodInfo> methods;
    private List<AttributeInfo> attributes;

    // TODO
    private final Logger logger = Logger.getGlobal();

    private JavaClassFile(){}

    public short getMinorVersion() {
        return minorVersion;
    }

    public short getMajorVersion() {
        return majorVersion;
    }

    public String decodeMethodName(String encoded) {
        // TODO
        return encoded;
    }

    public void display() {
        logger.info("version: " + getMajorVersion() + "." + getMinorVersion());

        methods.stream()
            .forEach((x) -> {
                    String methodName = new String(constants.get(x.nameIndex).bytes);
                    logger.info(decodeMethodName(methodName));

                    for (AttributeInfo attr: x.attributes) {
                        String name = new String(constants.get(attr.attributeNameIndex).bytes);
                        logger.info(name);
                    }
                });

        // constants.stream()
            // .filter((x) -> x.tag == 7)
            // .forEach((x) -> {
            //         String className = new String(constants.get(x.index2).bytes);
            //         logger.info(className);
            //     });
    }

    public static class Builder {
        private final String CRLF = "\r\n";
        
        public JavaClassFile buildFromBuffer(byte[] rawData) {
            ByteBuffer buffer = ByteBuffer.wrap(rawData)
                .order(ByteOrder.BIG_ENDIAN);

            int magic;
            short minorVersion;
            short majorVersion;
            short accessFlags;
            short thisClass;
            short superClass;
            List<ConstantInfoRecord> constants = new ArrayList<>();
            List<Short> interfaces = new ArrayList<>();
            List<FieldInfo> fields = new ArrayList<>();
            List<MethodInfo> methods = new ArrayList<>();
            List<AttributeInfo> attributes = new ArrayList<>();

            magic = buffer.getInt();
            minorVersion = buffer.getShort();
            majorVersion = buffer.getShort();
            int constantPoolCount = buffer.getShort();

            ConstantInfoParser parser = new ConstantInfoParser();
            int realCount = constantPoolCount-1;
            for (int i = 0; i < realCount; i++) {
                parser.parse(buffer);
                constants.add(parser.result());
            }

            accessFlags = buffer.getShort();
            thisClass = buffer.getShort();
            superClass = buffer.getShort();
            int interfacesCount = buffer.getShort();
            for (int i = 0; i < interfacesCount; i++) {
                interfaces.add(buffer.getShort());
            }
        
            int fieldsCount = buffer.getShort();
            for (int i = 0; i < fieldsCount; i++) {
                fields.add(new FieldInfo.Parser().parse(buffer));
            }

            int methodsCount = buffer.getShort();
            for (int i = 0; i < methodsCount; i++) {
                methods.add(new MethodInfo.Parser().parse(buffer));
            }

            int attributesCount = buffer.getShort();
            for (int i = 0; i < attributesCount; i++) {
                attributes.add(new AttributeInfo.Parser().parse(buffer));
            }

            JavaClassFile jcf = new JavaClassFile();
            jcf.magic = magic;
            jcf.minorVersion = minorVersion;
            jcf.majorVersion = majorVersion;
            jcf.accessFlags = accessFlags;
            jcf.thisClass = thisClass;
            jcf.superClass = superClass;
            jcf.constants = constants;
            jcf.interfaces = interfaces;
            jcf.fields = fields;
            jcf.methods = methods;
            jcf.attributes = attributes;
            return jcf;
        }

        public JavaClassFile buildFromFile(String filename) throws IOException {
            try(FileInputStream inputStream = new FileInputStream(filename)) {
                int fileSize = inputStream.available();
                byte[] rawData = new byte[fileSize];
                if (inputStream.read(rawData) != fileSize) {
                    throw new IOException("read file error");
                }

                return buildFromBuffer(rawData);
            }
        }
    }   
}
