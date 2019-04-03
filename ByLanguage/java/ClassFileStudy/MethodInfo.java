import java.io.*;
import java.util.*;
import java.nio.*;

class MethodInfo extends FieldInfo {
    public static class Parser {
        public MethodInfo parse(ByteBuffer buffer) {
            MethodInfo f = new MethodInfo();

            f.accessFlags = buffer.getShort();
            f.nameIndex = buffer.getShort();
            f.descriptorIndex = buffer.getShort();
            f.attributesCount = buffer.getShort();
            f.attributes = new AttributeInfo[f.attributesCount];
            for (short i = 0; i < f.attributesCount; i++) {
                f.attributes[i] = new AttributeInfo.Parser().parse(buffer);
            }

            return f;
        }
    }
}
