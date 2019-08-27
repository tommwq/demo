package com.tq.microservice.coreserver;

import com.tq.microservice.gateway.GatewayConfig;

public class CoreServerConfig {

  public static class ModuleToggle {
    boolean gateway;
    boolean configurationService;
    boolean registryService;
  }

  ModuleToggle activeModule;
  GatewayConfig gateway;
}
