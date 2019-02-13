package PrintParameter;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class PrintParameter extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        resp.setContentType("text/text");
        PrintWriter writer = resp.getWriter();
        
        Map<String,String[]> parameters = req.getParameterMap();
        for (Map.Entry<String,String[]> pair: parameters.entrySet()) {
            writer.print(pair.getKey() + ": ");
            for (String value: pair.getValue()) {
                writer.print(value + " ");
            }
            writer.println();
        }
    }
}
