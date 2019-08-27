package com.tq.utility;

/**
 * a function that may fail.
 * @param <T> return type
 */
@FunctionalInterface
public interface FallibleFunction<T> {
  T call() throws Exception;
}
