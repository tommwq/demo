  @Override
  public io.grpc.stub.StreamObserver<${inputType}> ${methodName}(io.grpc.stub.StreamObserver<${outputType}> responseStream) {
    return new io.grpc.stub.StreamObserver<${inputType}>() {
      @Override
      public void onNext(${inputType} value) {
      
      }

      @Override
      public void onError(Throwable t) {
      
      }
      
      @Override
      public void onCompleted() {
      
      }
    };
  }