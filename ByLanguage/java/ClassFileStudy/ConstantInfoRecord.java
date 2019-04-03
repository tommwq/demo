public class ConstantInfoRecord {
    public byte tag;
    public short index1;  // class_index, descriptor_index, reference_index, bootstrap_method_attr_index
    public short index2;  // name_and_type_index, string_index, name_index
    public byte[] bytes = new byte[0];
    public byte referenceKind;

    public String toString() {
        return String.format("tag: %d\nindex1: %d\nindex2: %d\nreferencekind: %d\nbytes: %s\n", tag, index1, index2, referenceKind, new String(bytes));
    }
}
