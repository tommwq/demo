package com.tq.utility;


@FunctionalInterface
public interface Procedure<T> {
  void call(T... args) throws Exception;
}
