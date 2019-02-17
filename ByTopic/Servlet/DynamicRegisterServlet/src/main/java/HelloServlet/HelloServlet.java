package HelloServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name="helloservlet", urlPatterns={"/helloservlet"})
public class HelloServlet extends HttpServlet {

    private ServletContext ctx;
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("ok");
    }

    @Override
    public void init(ServletConfig config) {
        System.out.println("helloservlet init");
    }
}
