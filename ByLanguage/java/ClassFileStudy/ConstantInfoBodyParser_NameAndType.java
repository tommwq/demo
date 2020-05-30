import java.nio.*;

public class ConstantInfoBodyParser_NameAndType implements ConstantInfoBodyParser {
    public int parse(ByteBuffer buffer, ConstantInfoRecord record) {
        short index2 = buffer.getShort(); // name_index
        short index1 = buffer.getShort();
        record.index1 = index1;
        record.index2 = index2;
        return 4;
    }
}
