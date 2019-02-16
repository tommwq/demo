package TimeoutFilter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        try {
            Thread.sleep(4*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("Hello");
    }
}
