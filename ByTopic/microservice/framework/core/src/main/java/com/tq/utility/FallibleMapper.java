package com.tq.utility;

@FunctionalInterface
public interface FallibleMapper<From,To> {
  To call(From input) throws Exception;
}
