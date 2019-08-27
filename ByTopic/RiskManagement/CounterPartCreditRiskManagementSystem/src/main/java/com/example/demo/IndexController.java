package com.example.demo;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;

@RestController
@RequestMapping("/api")
public class IndexController {

  public static class Response {
    public int error = 0;
    public String reason = "";
  }

  public static class NewCounterpart {
    public String name;
    public int type;
  }
  

  @RequestMapping("/counterpart")
  @ResponseBody
  public List<RateInfo> index() {
    Counterpart c1 = new Counterpart("1", "中信证券", CounterpartType.Institution);
    c1.changeIndustryDebtAssetRatio(1);
    c1.changeIndustryRoe(2);
    Counterpart c2 = new Counterpart("2", "中国银行", CounterpartType.Institution);
    
    List<Counterpart> counterparts = Arrays.asList(c1, c2);

    return counterparts.stream()
      .map(cp -> {
          cp.rate();
          return cp.getRateInfo();
        })
      .collect(Collectors.toList());
  }

  @RequestMapping("/counterpart/new")
  @ResponseBody
  public Response newCounterpart(NewCounterpart aCounterpart) {
    System.err.println(aCounterpart.name);
    Response resp = new Response();
    return resp;
  }
}
