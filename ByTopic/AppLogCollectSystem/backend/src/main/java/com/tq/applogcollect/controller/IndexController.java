package com.tq.applogcollect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tq.applogcollect.LogCollectService;
import com.tq.applogcollect.LogCollectServer;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class IndexController {

  @Autowired
  private LogCollectServer server;

  private Logger logger = LoggerFactory.getLogger(IndexController.class);
  
  @RequestMapping(value="/")
  public String index(Model model) {

    server.service.onlineDevices.keySet().stream().forEach(x -> logger.debug("|" + x + "|"));
    
    model.addAttribute("onlineDeviceSet", LogCollectService.onlineDevices.keySet());
    return "view/index";
  }

  @RequestMapping(value="/log")
  public String log(Model model) throws Exception {

    logger.warn("size: " + server.service.onlineDevices.keySet().size());
    server.service.onlineDevices.keySet().stream().forEach(x -> logger.warn("|" + x + "|"));

    LogCollectService.LogRecordInputStream stream = server.service.onlineDevices.get("test_client");
    logger.warn("" + stream);
    if (stream != null) {
      stream.command();
      model.addAttribute("logs", stream.logBuffer);      
      return "view/log";
    }
    
    model.addAttribute("onlineDeviceSet", LogCollectService.onlineDevices.keySet());
    return "view/index";
  }
}
