package com.tq.microservice.gateway;

import com.tq.microservice.gateway.nameresolver.ConsulNameResolver;
import com.tq.microservice.gateway.servicebuilder.ProxyServiceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import com.tq.utility.Util;
import io.grpc.NameResolver;

import java.lang.reflect.Constructor;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.google.gson.Gson;

@Controller
public class DefaultController {

  @Autowired
  GatewayConfig gatewayConfig;

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public String handleException(Exception e) {
    e.printStackTrace(System.err);
    return e.getMessage();
  }

  @RequestMapping("/{service}/{method}")
  @ResponseBody
  public String handle(@PathVariable("service") String service,
                       @PathVariable("method") String method,
                       @RequestParam Map<String, String> body) throws Exception {

    ProxyServiceBuilder.Config proxyServiceBuilderConfig = new ProxyServiceBuilder.Config();
    proxyServiceBuilderConfig.setProtocolDirectory(gatewayConfig.getProtocolDirectory());
    proxyServiceBuilderConfig.setBuildDirectory(gatewayConfig.getBuildDirectory());
    proxyServiceBuilderConfig.setProtoCompilerPath(gatewayConfig.getProtoCompilerPath());
    proxyServiceBuilderConfig.setProtoIncludeDirectory(gatewayConfig.getProtoIncludeDirectory());
    proxyServiceBuilderConfig.setGrpcPluginPath(gatewayConfig.getGrpcPluginPath());
    proxyServiceBuilderConfig.setJavaCompilerPath(gatewayConfig.getJavaCompilerPath());
    proxyServiceBuilderConfig.setJarPath(gatewayConfig.getJarPath());
    proxyServiceBuilderConfig.setClassPath(gatewayConfig.getClassPath());

    
    String className = service;
    String javaMethodName = Util.pascalCaseToCamelCase(method);
    ClassLoader classLoader = new ProxyServiceBuilder().build(proxyServiceBuilderConfig);
            
    Method javaMethod = Stream.of(classLoader.loadClass(className).getMethods())
      .filter(m -> m.getName().equals(javaMethodName))
      .collect(Collectors.toList())
      .get(0);

    String inputType = javaMethod.getParameterTypes()[0].getName();
    String outputType = ((ParameterizedType) javaMethod.getGenericParameterTypes()[1]).getActualTypeArguments()[0].getTypeName();
    String inputPojoClassName = Util.toPojoClassName(inputType);
    String inputCodecClassName = Util.toCodecClassName(inputType);
    String outputPojoClassName = Util.toPojoClassName(outputType);
    String outputCodecClassName = Util.toCodecClassName(outputType);

    Object inputObject = classLoader.loadClass(inputPojoClassName).newInstance();
    org.apache.commons.beanutils.BeanUtils.populate(inputObject, body);
    Object grpcInputObject = classLoader.loadClass(inputCodecClassName)
      .getMethod("toProto", classLoader.loadClass(inputPojoClassName))
      .invoke(null, inputObject);

    Constructor constructor = classLoader.loadClass(service).getConstructor(NameResolver.Factory.class);

    ManagedChannel channel = ManagedChannelBuilder.forTarget(service)
      .nameResolverFactory(new ConsulNameResolver.Factory())
      .usePlaintext()
      .build();
    
    Object blockingStub = classLoader.loadClass(service + "Grpc")
      .getMethod("newBlockingStub", io.grpc.Channel.class)
      .invoke(null, channel);

    Object grpcOutput = blockingStub.getClass()
      .getMethod(javaMethodName, classLoader.loadClass(inputType))
      .invoke(blockingStub, grpcInputObject);
    
    Object pojoOutputObject = classLoader.loadClass(outputCodecClassName)
      .getMethod("toPojo", classLoader.loadClass(outputType))
      .invoke(null, grpcOutput);

    return new Gson().toJson(pojoOutputObject);
  }
}
