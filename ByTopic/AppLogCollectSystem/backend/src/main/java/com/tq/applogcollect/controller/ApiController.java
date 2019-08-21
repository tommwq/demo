package com.tq.applogcollect.controller;

import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.LogCollectServer;
import com.tq.applogcollect.LogCollectService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.tq.applogcollect.storage.LogStorage;
import com.tq.applogcollect.storage.Memory;

@RestController
public class ApiController {

  @Autowired
  private LogCollectServer server;

  @RequestMapping(value="/api/devices")
  @ResponseBody
  public Set<String> device() {
    return server.getService().getOnlineDeviceIdSet();
  }

  @RequestMapping(value="/api/log/{deviceId}")
  @ResponseBody
  public List<String>log(@PathVariable("deviceId") String deviceId) throws Exception {
    LogStorage storage = new Memory();
    return storage.load(deviceId, 0L, 0)
      .stream()
      .map(LogRecord::toString)
      .collect(Collectors.toList());
  }
}
