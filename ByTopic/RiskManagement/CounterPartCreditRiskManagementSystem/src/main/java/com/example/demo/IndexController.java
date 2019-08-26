package com.example.demo;

import java.util.List;
import java.util.ArrayList;

import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;

@RestController
public class IndexController {


  @RequestMapping("/")
  @ResponseBody
  public List<RateInfo> index() {
    List<Counterpart> counterparts = new ArrayList<>();
    counterparts.add(new Counterpart("1", "中信证券", CounterpartType.Institution));
    counterparts.add(new Counterpart("2", "建设证券", CounterpartType.Institution));

    return counterparts.stream()
      .map(cp -> {
          cp.rate();
          return cp.getRateInfo();
        })
      .collect(Collectors.toList());
  }
}
