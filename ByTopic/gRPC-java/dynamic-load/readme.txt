测试Java动态加载gRPC协议。

和C++不同，Java的protobuf包没有包含ProtobufCompiler，因此必须通过调用本地protoc的方式编译pb文件。


步骤：
1. 调用protoc从proto文件生成java文件。
2. 调用javac编译java文件。
3. 加载class文件。
4. 使用proxy建立rpc服务。
5. 返回空数据。
