package com.tq.gateway;

import io.grpc.BindableService;
import io.grpc.HandlerRegistry;
import io.grpc.ServerMethodDefinition;
import javax.annotation.Nullable;

/**
 * Lookup unregistered services, load and create transmite proxy for them.
 */
public class UnregisteredServiceRegistry extends HandlerRegistry {

  // TODO consider concurrency

  private ClassLoader classLoader;
  public UnregisteredServiceRegistry(ClassLoader aClassLoader) {
    classLoader = aClassLoader;
  }
  
  @Override
  @Nullable
  public ServerMethodDefinition<?,?> lookupMethod(String methodName, @Nullable String authority) {

    int index = methodName.indexOf("/");
    if (index == -1) {
      return null;
    }

    String fullClassName = methodName.substring(0, index);
    String classMethodName = methodName.substring(index + 1);
    String packageName = "";
    String className = fullClassName;
    index = fullClassName.lastIndexOf(".");
    if (index != -1){
      packageName = fullClassName.substring(0, index);
      className = fullClassName.substring(index + 1);
    }

    // TODO lookup service instances.

   try {
      String serviceClassName = packageName + "." + className;
      BindableService service = (BindableService) classLoader.loadClass(serviceClassName).newInstance();
      return service.bindService().getMethod(methodName);
    } catch (ClassNotFoundException e) {
      // TODO use logging
      e.printStackTrace(System.err);
    } catch (InstantiationException e) {
      // TODO use logging
      e.printStackTrace(System.err);
    } catch (IllegalAccessException e) {
      // TODO use logging
      e.printStackTrace(System.err);
    }
    
    return null;
  }
}
