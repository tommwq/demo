import java.nio.*;

public class ConstantInfoBodyParser_Long implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        byte[] bytes = new byte[8];
        buffer.get(bytes);
        record.bytes = bytes;
        return 8;
    }
}
