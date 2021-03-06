package HelloListener;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloListener extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        HttpSession session = req.getSession();

        String word = (String) session.getAttribute("ok");
        if (word == null || word.isEmpty()) {
            word = "ok";
            session.setAttribute("ok", word);
        }

        int count = (int) session.getAttribute("count");
        count += 1;
        session.setAttribute("count", count);
        
        resp.setContentType("text/text");
        PrintWriter writer = resp.getWriter();
        writer.println(session.getAttribute("ok") + " " + session.getAttribute("count"));
    }
}
