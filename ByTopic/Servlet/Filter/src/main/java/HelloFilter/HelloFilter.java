package HelloFilter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloFilter extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        resp.setContentType("text/text");
        PrintWriter writer = resp.getWriter();
        writer.println("Hello, Filter!");
        System.out.println("doGet " + this);
    }
}
