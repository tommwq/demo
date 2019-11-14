package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.tq.startup.tabbedactivity.DataBinderMapperImpl());
  }
}
