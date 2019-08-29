package com.tq.microservice.coreserver;

import com.tq.microservice.gateway.GatewayConfig;
import com.tq.microservice.registryservice.RegistryServiceConfig;

public class CoreServerConfig {

  public static class ModuleToggle {
    boolean gateway;
    boolean configurationService;
    boolean registryService;
  }

  ModuleToggle activeModule;
  GatewayConfig gateway;
  RegistryServiceConfig registryservice;
}
