import java.util.*;
import java.nio.*;

public class ConstantInfoParser {
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
