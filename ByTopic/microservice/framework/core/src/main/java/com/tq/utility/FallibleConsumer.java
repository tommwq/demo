package com.tq.utility;


@FunctionalInterface
public interface FallibleConsumer<T> {
    void call(T input) throws Exception;
}
