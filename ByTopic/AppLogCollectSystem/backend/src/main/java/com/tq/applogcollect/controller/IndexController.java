package com.tq.applogcollect.controller;

import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.LogCollectServer;
import com.tq.applogcollect.LogCollectService;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

  @Autowired
  private LogCollectServer server;

  private Logger logger = LoggerFactory.getLogger(IndexController.class);
  
  @RequestMapping(value="/")
  public String index(Model model) {
    
    model.addAttribute("onlineDeviceSet", LogCollectService.onlineDevices.keySet());
    return "view/index";
  }

  @RequestMapping(value="/log/{deviceId}")
  public ModelAndView log(@PathVariable("deviceId") String deviceId) throws Exception {
    ModelAndView modelAndView = new ModelAndView();

    LogCollectService.LogRecordInputStream stream = server.service.onlineDevices.get(deviceId);
    if (stream != null) {
      stream.command();
      modelAndView.addObject("logs", stream.logBuffer
                             .stream()
                             .map(record -> record.toString())
                             .collect(Collectors.toList()));
      modelAndView.setViewName("view/log");
    } else {
      modelAndView.addObject("onlineDeviceSet", LogCollectService.onlineDevices.keySet());
      modelAndView.setViewName("view/index");
    }

    return modelAndView;
  }
}
