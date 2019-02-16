package HelloServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebFilter(filterName="filter", urlPatterns={"/*"})
public class HelloFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain next)
        throws IOException, ServletException {
        next.doFilter(req, resp);
        resp.getWriter().println("powered by filter");
    }
}
