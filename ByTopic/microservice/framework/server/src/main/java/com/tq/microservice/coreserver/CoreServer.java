package com.tq.microservice.coreserver;

import com.tq.microservice.App;
import com.tq.microservice.annotation.Executable;

@Executable
public class CoreServer {
  public void run() {
    System.out.println("*************\nok\n*************\n");
  }

  public static void main(String... args) {
      App.run();
  }
}
