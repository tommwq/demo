package com.tq.applogmanagement.controller;

import com.tq.applogmanagement.AppLogManagementProto.Log;
import com.tq.applogmanagement.LogManagementServer;
import com.tq.applogmanagement.LogManagementService;
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
import com.tq.applogmanagement.storage.LogStorage;
import com.tq.applogmanagement.storage.Memory;

import com.tq.applogmanagement.LogSession;

@RestController
public class ApiController {

  @Autowired
  private LogManagementServer server;

  @RequestMapping(value="/api/devices")
  @ResponseBody
  public Set<String> device() {
    return server.getService().getOnlineDeviceIdSet();
  }

  @RequestMapping(value="/api/log/{deviceId}")
  @ResponseBody
  public List<String>log(@PathVariable("deviceId") String deviceId) throws Exception {
    LogSession session = server.getService().getLogSession(deviceId);
    if (session != null) {
      session.command();
    }
      
    LogStorage storage = new Memory();
    return storage.load(deviceId, 0L, 0)
      .stream()
      .map(log -> log.toString())
      .collect(Collectors.toList());
  }
}
