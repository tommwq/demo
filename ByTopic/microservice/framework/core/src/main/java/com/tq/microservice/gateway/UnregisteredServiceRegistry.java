package com.tq.microservice.gateway;

import com.tq.microservice.gateway.GatewayConfig;
import com.tq.microservice.gateway.nameresolver.RegistryNameResolver;
import com.tq.utility.NetUtil.SocketAddressParser;
import io.grpc.BindableService;
import io.grpc.HandlerRegistry;
import io.grpc.NameResolver;
import io.grpc.ServerMethodDefinition;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nullable;

/**
 * Lookup unregistered services, load and create transmite proxy for them.
 */
public class UnregisteredServiceRegistry extends HandlerRegistry {

  // TODO consider concurrency

  private ClassLoader classLoader;
  private String registryServiceAddress;
  public UnregisteredServiceRegistry(ClassLoader aClassLoader, String aAddress) {
    classLoader = aClassLoader;
    registryServiceAddress = aAddress;
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
      Constructor constructor = classLoader.loadClass(serviceClassName).getConstructor(NameResolver.Factory.class);
      SocketAddressParser parser = new SocketAddressParser(registryServiceAddress);
      BindableService service = (BindableService) constructor.newInstance(new RegistryNameResolver.Factory(parser.address(), parser.port()));
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
    } catch (NoSuchMethodException e) {
      // TODO use logging
      e.printStackTrace(System.err);
    } catch (InvocationTargetException e) {
      // TODO use logging
      e.printStackTrace(System.err);
    }
    
    return null;
  }
}
