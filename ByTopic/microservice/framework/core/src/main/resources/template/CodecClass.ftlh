package ${package}.http.codec;

public class ${message}Codec {

    public static ${package}.${message} toProto(${package}.http.${message} pojo) {
        return ${package}.${message}.newBuilder()
            ${protoSetStat}
            .build();
    }

    public static ${package}.http.${message} toPojo(${package}.${message} proto) {
        ${package}.http.${message} pojo = new ${package}.http.${message}();
        ${pojoSetStat}
        return pojo;
    }
}