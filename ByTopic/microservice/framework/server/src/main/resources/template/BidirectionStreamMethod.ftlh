  @Override
  public io.grpc.stub.StreamObserver<${inputType}> ${methodName}(io.grpc.stub.StreamObserver<${outputType}> outputStream) {
    return stub.${methodName}(outputStream);
  }