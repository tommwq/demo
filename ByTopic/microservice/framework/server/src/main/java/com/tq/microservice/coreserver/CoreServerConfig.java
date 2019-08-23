package com.tq.microservice.coreserver;

import com.tq.microservice.gateway.GatewayConfig;

public class CoreServerConfig {

  public static class Toggle {
    boolean gateway;
  }

  Toggle active;
  GatewayConfig gateway;
  
  /*
    gateway 是否启动 端口 
    configurationservice
    registryservice
  */
}
