package TimeoutFilter;

import java.io.*;
import javax.servlet.*;

import java.util.logging.Logger;

public class TimeoutFilter implements Filter {

    private Logger logger = Logger.getGlobal();
    
    public void init(FilterConfig ignored) {
        System.out.println("init " + this);
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain next) throws IOException, ServletException {
        Manager.start();
        Manager.onRequest(Thread.currentThread());
        try {
            next.doFilter(req, resp);
        } finally {
            Manager.onResponse(Thread.currentThread());
        }
    }

    public void destroy() {
        System.out.println("destroy " + this);
        Manager.stop();
    }
}
