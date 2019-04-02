package com.servletintroduce.helloservlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        resp.setContentType("text/text");
        PrintWriter writer = resp.getWriter();
        writer.println("Hello, servlet!");
    }
}
