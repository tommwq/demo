import java.nio.*;

public class ConstantInfoBodyParser_InvokeDynamic implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        short bootstrapMethodAttrIndex = buffer.getShort();
        short nameAndTypeIndex = buffer.getShort();
        record.index1 = bootstrapMethodAttrIndex;
        record.index2 = nameAndTypeIndex;
        return 4;
    }
}
