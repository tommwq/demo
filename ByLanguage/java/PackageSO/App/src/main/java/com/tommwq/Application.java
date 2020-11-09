package com.tommwq;


import com.sun.javaws.security.AppPolicy;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Application {
    public static void main(String[] args) throws IOException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {

        App app = App.get();
        app.init();
        app.hello();
    }
}
