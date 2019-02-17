package HelloServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    
    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("context init");
        ServletContext ctx = event.getServletContext();
        ServletRegistration.Dynamic dynamic = ctx.addServlet("myservlet", MyServlet.class);
        dynamic.addMapping("/myservlet");
    }
}

