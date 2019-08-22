package com.tq.utility;


@FunctionalInterface
public interface Emitter<T,R> {
  R call(T... args) throws Exception;
}
