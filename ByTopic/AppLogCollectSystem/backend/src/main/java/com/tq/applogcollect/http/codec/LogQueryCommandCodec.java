package com.tq.applogcollect.http.codec;

public class LogQueryCommandCodec {

  public static com.tq.applogcollect.AppLogCollectProto.LogQueryCommand toProto(com.tq.applogcollect.http.LogQueryCommand pojo) {
    return com.tq.applogcollect.AppLogCollectProto.LogQueryCommand.newBuilder()
      .setPackageName(pojo.getPackageName())
      .setSequence(pojo.getSequence())
      .setCount(pojo.getCount())
      .setIncludeSubPackage(pojo.getIncludeSubPackage())
      .build();
  }

  public static com.tq.applogcollect.http.LogQueryCommand toPojo(com.tq.applogcollect.AppLogCollectProto.LogQueryCommand proto) {
    com.tq.applogcollect.http.LogQueryCommand pojo = new com.tq.applogcollect.http.LogQueryCommand();
    pojo.setPackageName(proto.getPackageName());
    pojo.setSequence(proto.getSequence());
    pojo.setCount(proto.getCount());
    pojo.setIncludeSubPackage(proto.getIncludeSubPackage());
    return pojo;
  }
}
