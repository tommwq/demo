package com.example.controller;

import java.io.Serializable;

public final class Response<T> implements Serializable {
    public T result;
    public int returnCode;

    protected Response() {
    }

    public Response(int returnCode, T result) {
        this.result = result;
        this.returnCode = returnCode;
    }
}
