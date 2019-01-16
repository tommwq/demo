package com.example.controller;

import java.io.Serializable;

public final class Response<T> implements Serializable {
    protected Response() {
    }

    public Response(int returnCode, T result) {
        this.result = result;
        this.returnCode = returnCode;
    }

    public T result;
    public int returnCode;
}
