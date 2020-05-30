import java.nio.*;

public class ConstantInfoBodyParser_MethodHandle implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        byte referenceKind = buffer.get();
        short referenceIndex = buffer.getShort();
        record.referenceKind = referenceKind;
        record.index1 = referenceIndex;
        return 3;
    }
}
