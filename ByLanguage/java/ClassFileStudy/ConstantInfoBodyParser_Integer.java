import java.nio.*;

public class ConstantInfoBodyParser_Integer implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        byte[] bytes = new byte[4];
        buffer.get(bytes);
        record.bytes = bytes;
        return 4;
    }
}
