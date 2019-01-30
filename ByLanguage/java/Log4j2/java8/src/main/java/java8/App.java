package java8;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class App {
    protected static final Logger logger = LogManager.getLogger();
    
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        String message = new App().getGreeting();
        logger.info(message);
    }
}
