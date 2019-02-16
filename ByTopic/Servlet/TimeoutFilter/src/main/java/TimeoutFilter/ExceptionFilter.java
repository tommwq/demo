package TimeoutFilter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.logging.Logger;

public class ExceptionFilter implements Filter {

    private Logger logger = Logger.getGlobal();
    
    public void init(FilterConfig ignored) {
        System.out.println("init " + this);
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain next) throws IOException, ServletException {
        try {
            next.doFilter(req, resp);
        } catch (IOException e) {
            System.out.println("io exception");
            onException(req, resp, e);
        } catch (ServletException e) {
            System.out.println("servlet exception");
            onException(req, resp, e);
        } catch (Exception e) {
            System.out.println("exception");
            onException(req, resp, e);
        }
    }

    public void onException(ServletRequest req, ServletResponse resp, Exception e) {
        if (resp.isCommitted()) {
            return;
        }
        
        resp.reset();
        if (resp instanceof HttpServletResponse) {
            HttpServletResponse httpResp = (HttpServletResponse) resp;
            httpResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                PrintWriter writer = httpResp.getWriter();
                e.getCause().printStackTrace(writer);
            } catch (IOException ex) {
                // resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    public void destroy() {
        System.out.println("destroy " + this);
    }
}
