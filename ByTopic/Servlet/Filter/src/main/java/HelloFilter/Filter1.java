package HelloFilter;

import java.io.*;
import javax.servlet.*;

import java.util.logging.Logger;

public class Filter1 implements Filter {

    private Logger logger = Logger.getGlobal();
    
    public void init(FilterConfig ignored) {
        System.out.println("init " + this);
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain next) throws IOException, ServletException {
        System.out.println("doFilter step1 " + this + " " + Thread.currentThread().getId());
        next.doFilter(req, resp);
        System.out.println("doFilter step2 " + this + " " + Thread.currentThread().getId());
    }

    public void destroy() {
        System.out.println("destroy " + this);
    }
}
