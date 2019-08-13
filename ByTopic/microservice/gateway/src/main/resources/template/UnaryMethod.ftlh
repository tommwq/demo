  @Override
  public void ${methodName}(${inputType} request, io.grpc.stub.StreamObserver<${outputType}> responseStream) {
    ${outputType} realResponse = blockingStub.${methodName}(request);
    responseStream.onNext(realResponse);
    responseStream.onCompleted();
  }