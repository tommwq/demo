  @Override
  public void ${methodName}(${inputType} input, io.grpc.stub.StreamObserver<${outputType}> outputStream) {
    outputStream.onNext(blockingStub.${methodName}(input));
    outputStream.onCompleted();
  }