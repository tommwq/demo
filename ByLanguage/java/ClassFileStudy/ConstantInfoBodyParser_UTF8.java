import java.nio.*;


public class ConstantInfoBodyParser_UTF8 implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        short length = buffer.getShort();
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        record.bytes = bytes;
        return 2 + bytes.length;
    }
}
