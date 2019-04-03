import java.nio.*;

public class ConstantInfoBodyParser_Invalid implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        throw new RuntimeException("invalid tag: " + record.tag + ", position: " + buffer.position());
    }
}
