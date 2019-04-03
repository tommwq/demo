import java.nio.*;

public class ConstantInfoBodyParser_String implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        short index2 = buffer.getShort();
        record.index2 = index2;
        return 2;
    }
}
