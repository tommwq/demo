import java.util.logging.*;

public class GCSampleApp {

    private static Logger logger = Logger.getGlobal();

    public static void main(String[] args) {
        try {
            new GCSampleApp().run();
        } catch (Exception e) {
            logger.log(Level.WARNING, "error occurr", e);
        }
    }

    public void run() throws Exception {
        while (true) {
            Thread.sleep(1000);
        }
    }
}
