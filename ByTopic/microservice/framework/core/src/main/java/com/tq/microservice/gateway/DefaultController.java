package com.tq.gateway;

import com.tq.gateway.service.builder.ProxyServiceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

import com.tq.utility.Utils;
import io.grpc.BindableService;
import io.grpc.HandlerRegistry;
import io.grpc.NameResolver;
import io.grpc.ServerMethodDefinition;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nullable;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolver.Factory;
import io.grpc.StatusRuntimeException;

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
    String javaMethodName = Utils.pascalCaseToCamelCase(method);
    ClassLoader classLoader = new ProxyServiceBuilder().build(proxyServiceBuilderConfig);
            
    Method javaMethod = Stream.of(classLoader.loadClass(className).getMethods())
      .filter(m -> m.getName().equals(javaMethodName))
      .collect(Collectors.toList())
      .get(0);

    String inputType = javaMethod.getParameterTypes()[0].getName();
    String outputType = ((ParameterizedType) javaMethod.getGenericParameterTypes()[1]).getActualTypeArguments()[0].getTypeName();
    String inputPojoClassName = Utils.toPojoClassName(inputType);
    String inputCodecClassName = Utils.toCodecClassName(inputType);
    String outputPojoClassName = Utils.toPojoClassName(outputType);
    String outputCodecClassName = Utils.toCodecClassName(outputType);

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
