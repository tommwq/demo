import java.nio.*;

public class ConstantInfoBodyParser_MethodRef implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        short index1 = buffer.getShort();
        short index2 = buffer.getShort();
        record.index1 = index1;
        record.index2 = index2;
        return 4;
    }
}
