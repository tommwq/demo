
package com.tq.test;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.io.File;
import java.util.logging.Logger;
import com.tq.test.helloworld.HelloRequest;
import com.tq.test.helloworld.HelloReply;
import com.tq.test.helloworld.GreeterGrpc;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public class MyHandlerRegistry extends HandlerRegistry {

  private static final Logger logger = Logger.getLogger(MyHandlerRegistry.class.getName());
  
  @Nullable
  @Override
  public ServerMethodDefinition<?, ?> lookupMethod(String methodName, @Nullable String authority) {
    logger.info("lookupMethod: " + methodName);
    return null;
  }
}

