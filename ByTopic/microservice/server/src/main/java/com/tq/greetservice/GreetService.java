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

      private ArrayList<GreetRequest> buffer = new ArrayList();
      
      @Override
      public void onNext(GreetRequest request) {
        buffer.add(request);
      }

      @Override
      public void onError(Throwable t) {
        outputStream.onCompleted();
      }

      @Override
      public void onCompleted() {
        for (GreetRequest request: buffer) {
          outputStream.onNext(GreetReply.newBuilder().setMessage("Greet2 " + request.getName()).build());      
        }

        buffer.clear();
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
      public void onError(Throwable t) {
        outputStream.onCompleted();
      }

      @Override
      public void onCompleted() {
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
