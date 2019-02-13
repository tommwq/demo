package InvokeThread;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class InvokeThread extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        resp.setContentType("text/text");
        PrintWriter writer = resp.getWriter();
        writer.println("thread id: " + Thread.currentThread().getId() + "\n" +
                       "servlet: " + this);
    }
}
