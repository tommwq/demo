package com.tq.applogcollect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tq.applogcollect.LogCollectService;
import com.tq.applogcollect.LogCollectServer;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class HomeController {

  @Autowired
  private LogCollectServer server;
  
  @RequestMapping(value="/")
  public String index(Model model) {

    model.addAttribute("onlineDeviceSet", LogCollectService.onlineDevices.keySet());
    return "view/index";
  }

  @RequestMapping(value="/log")
  public String log(Model model) throws Exception {

    server.service.onlineDevices.keySet().stream().forEach(x -> System.err.println(x));
    
    server.service
      .onlineDevices
      .get("")
      .command();

    model.addAttribute("logs", server.service
                       .onlineDevices
                       .get("")
                       .logBuffer);
    
    return "view/log";
  }
}
