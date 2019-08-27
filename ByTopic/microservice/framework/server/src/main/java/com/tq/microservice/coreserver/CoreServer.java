package com.tq.microservice.coreserver;

import com.tq.microservice.annotation.Executable;
import com.tq.microservice.App;
import com.tq.microservice.configurationservice.ConfigurationService;
import com.tq.microservice.gateway.GatewayConfig;
import com.tq.microservice.gateway.UnregisteredServiceRegistry;
import com.tq.microservice.gateway.servicebuilder.ProxyServiceBuilder;
import com.tq.microservice.registryservice.RegistryService;
import com.tq.utility.CollectionUtil;
import com.tq.utility.Util;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

@Executable
public class CoreServer {
  private static final String DEFAULT_CONFIG_FILE_NAME = "coreserver.properties";
  private static final String COMMANDLINE_OPTION_CONFIG_FILE = "config-file";

  private String parseConfigFileName(String... args) throws ParseException {
    Options options = new Options();
    options.addOption("COMMANDLINE_OPTION_CONFIG_FILE", false, "conig file path");

    CommandLineParser parser = new DefaultParser();
    CommandLine command = parser.parse(options, args);

    String configFileName = DEFAULT_CONFIG_FILE_NAME;
    if (command.hasOption("COMMANDLINE_OPTION_CONFIG_FILE")) {
      configFileName = command.getOptionValue("COMMANDLINE_OPTION_CONFIG_FILE");
    }

    return configFileName;
  }

  private CoreServerConfig readConfig(String configFileName)
    throws IOException, InstantiationException, IllegalAccessException {

    try (final FileInputStream inputStream = new FileInputStream(configFileName)) {
      Properties properties = new Properties();
      properties.load(inputStream);

      CoreServerConfig config = CollectionUtil.propertiesToObject(properties, CoreServerConfig.class);
      return config;
    }
  }

  private void startGateway(GatewayConfig gatewayConfig) {
    try {
      ProxyServiceBuilder.Config proxyServiceBuilderConfig = new ProxyServiceBuilder.Config();
      proxyServiceBuilderConfig.setProtocolDirectory(gatewayConfig.getProtocolDirectory());
      proxyServiceBuilderConfig.setBuildDirectory(gatewayConfig.getBuildDirectory());
      proxyServiceBuilderConfig.setProtoCompilerPath(gatewayConfig.getProtoCompilerPath());
      proxyServiceBuilderConfig.setProtoIncludeDirectory(gatewayConfig.getProtoIncludeDirectory());
      proxyServiceBuilderConfig.setGrpcPluginPath(gatewayConfig.getGrpcPluginPath());
      proxyServiceBuilderConfig.setJavaCompilerPath(gatewayConfig.getJavaCompilerPath());
      proxyServiceBuilderConfig.setJarPath(gatewayConfig.getJarPath());
      proxyServiceBuilderConfig.setClassPath(gatewayConfig.getClassPath());

      Server server = ServerBuilder.forPort(gatewayConfig.getPort())
        .fallbackHandlerRegistry(new UnregisteredServiceRegistry(new ProxyServiceBuilder().build(proxyServiceBuilderConfig)))
        .build()
        .start();
      Runtime.getRuntime().addShutdownHook(new Thread(() -> server.shutdown()));
      server.awaitTermination();
    } catch (Exception e) {
      // ignore
    }
  }

  private void startConfigurationService(/* todo */) {
    try {
      Server server = ServerBuilder.forPort(12345)
        .addService(new ConfigurationService())
        .build()
        .start();

      Runtime.getRuntime().addShutdownHook(new Thread(() -> server.shutdown()));
      server.awaitTermination();
    } catch (Exception e) {
      // ignore
    }
  }

  private void startRegistryService(/* todo */) {
    try {
      Server server = ServerBuilder.forPort(12346)
        .addService(new RegistryService())
        .build()
        .start();

      Runtime.getRuntime().addShutdownHook(new Thread(() -> server.shutdown()));
      server.awaitTermination();
    } catch (Exception e) {
      // ignore
    }
  }
  
  public void execute(String... args) throws Exception {
    CoreServerConfig config = readConfig(parseConfigFileName(args));

    if (config.activeModule.gateway) {
      new Thread(() -> startGateway(config.gateway)).start();
    }

    if (config.activeModule.configurationService) {
      new Thread(() -> startConfigurationService()).start();
    }

    if (config.activeModule.registryService) {
      new Thread(() -> startRegistryService()).start();
    }
  }

  public static void main(String... args) {
    App.main(args);
  }
}
