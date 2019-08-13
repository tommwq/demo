package com.tq.greetservice;

import com.tq.greetservice.GreetReply;
import com.tq.greetservice.GreetRequest;
import com.tq.greetservice.GreetServiceGrpc;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.ArrayList;

public class GreetService extends GreetServiceGrpc.GreetServiceImplBase {
  @Override
  public void greet1(GreetRequest request, StreamObserver<GreetReply> outputStream) {
    GreetReply reply = GreetReply.newBuilder()
      .setMessage("Greet1 " + request.getName())
      .build();

    outputStream.onNext(reply);
    outputStream.onCompleted();
  }

  @Override
  public StreamObserver<GreetRequest> greet2(StreamObserver<GreetReply> outputStream) {
    return new StreamObserver<GreetRequest>() {

      private GreetRequest last = null;
      
      @Override
      public void onNext(GreetRequest request) {
        last = request;
      }

      @Override
      public void onError(Throwable t) {}

      @Override
      public void onCompleted() {
        outputStream.onNext(GreetReply.newBuilder().setMessage("Greet2 " + last.getName()).build());
        outputStream.onCompleted();
      }
    };
  }

  @Override
  public StreamObserver<GreetRequest> greet3(StreamObserver<GreetReply> outputStream) {
    return new StreamObserver<GreetRequest>() {
      @Override
      public void onNext(GreetRequest request) {
        for (int i = 0; i < 3; i++) {
          outputStream.onNext(GreetReply.newBuilder().setMessage("Greet3 " + request.getName() + " " + i).build());      
        }
      }

      @Override
      public void onError(Throwable t) {}

      @Override
      public void onCompleted() {
        outputStream.onCompleted();
      }
    };
  }

  @Override
  public void greet4(GreetRequest request, StreamObserver<GreetReply> outputStream) {
    for (int i = 0; i < 3; i++) {
      outputStream.onNext(GreetReply.newBuilder().setMessage("Greet4 " + request.getName() + " " + i).build());
    }
    
    outputStream.onCompleted();
  }
}
