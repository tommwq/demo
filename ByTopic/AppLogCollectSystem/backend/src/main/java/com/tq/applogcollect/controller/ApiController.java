package com.tq.applogcollect.controller;

import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.LogCollectServer;
import com.tq.applogcollect.LogCollectService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ApiController {

  @Autowired
  private LogCollectServer server;

  private Logger logger = LoggerFactory.getLogger(IndexController.class);
  
  @RequestMapping(value="/api/devices")
  @ResponseBody
  public Set<String> device() {
    return LogCollectService.onlineDevices.keySet();
  }

  @RequestMapping(value="/api/log/{deviceId}")
  @ResponseBody
  public List<String>log(@PathVariable("deviceId") String deviceId) throws Exception {
    LogCollectService.LogRecordInputStream stream = server.service.onlineDevices.get(deviceId);
    if (stream != null) {
      stream.command();
      return stream.logBuffer
        .stream()
        .map(x -> x.toString())
        .collect(Collectors.toList());
    } 

    return new ArrayList<String>();
  }
}
