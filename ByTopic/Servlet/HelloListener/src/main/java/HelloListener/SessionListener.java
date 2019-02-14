package HelloListener;

import javax.servlet.*;
import javax.servlet.http.*;

public class SessionListener implements HttpSessionBindingListener, HttpSessionActivationListener, HttpSessionListener, HttpSessionAttributeListener {
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("bind");
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("unbind");
    }

    public void sessionDidActivate(HttpSessionEvent event) {
        System.out.println("activate");
    }

    public void sessionWillPassivate(HttpSessionEvent event) {
        System.out.println("passivate");
    }

    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("created");
        event.getSession().setAttribute("ok", "haha");
        event.getSession().setAttribute("count", 1);
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("destroyed");
    }

    public void attributeAdded(HttpSessionBindingEvent event) {
        System.out.println("added " + event.getName() + " " + event.getValue());
    }
    public void attributeRemoved(HttpSessionBindingEvent event) {
        System.out.println("removed " + event.getName() + " " + event.getValue());
    }
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.out.println("replaced " + event.getName() + " " + event.getValue());
    }
}
