import java.nio.*;

public class ConstantInfoBodyParser_MethodType implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        short descriptorIndex = buffer.getShort();
        record.index1 = descriptorIndex;
        return 2;
    }
}
