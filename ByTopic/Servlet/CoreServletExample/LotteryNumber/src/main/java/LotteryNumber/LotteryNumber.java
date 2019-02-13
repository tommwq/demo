package LotteryNumber;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LotteryNumber extends HttpServlet {

    private long modTime;
    private int[] numbers = new int[10];

    public void init() throws ServletException {
        modTime = System.currentTimeMillis() / 1000 * 1000;
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = randomNum();
        }
    }

    private int randomNum() {
        return ((int) (Math.random() * 100));
    }

    public long getLastModified(HttpServletRequest request) {
        return modTime;
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String title = "Your Lottery Numbers";
        String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
            "Transitional//EN\">\n";

        out.println(docType +
                    "<HTML>\n" +
                    "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
                    "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                    "<H1 ALIGN=CENTER>" + title + "<H1>\n" +
                    "<B>Based upon extensive research of " +
                    "astro-illogical trends, psychic farces, " +
                    "and detailed statistical claptrap, " +
                    "we have chosen the " + numbers.length +
                    " best lottery numbers for you.</B>" +
                    "<OL>");

        for (int i = 0; i < numbers.length; i++) {
            out.println("  <LI>" + numbers[i]);
        }

        out.println("</OL>" + "</BODY></HTML>");
    }
}
