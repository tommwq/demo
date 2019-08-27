package com.tq.utility;

/**
 * a procedure may fail.
 */
@FunctionalInterface
public interface FallibleProcedure {
  void call() throws Exception;
}
