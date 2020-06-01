package readproperties;

import java.io.*;
import java.util.*;

public class App {
    public Properties readProperties() throws IOException {
        Properties properties = new Properties();

        // 源代码目录resources下的文件在编译时会复制到target/classes目录下。
        // 文件需要以“/”开头。如果使用ClassLoader.getResourceAsStream，可以省略前缀“/”。
        try (InputStream input = getClass().getResourceAsStream("/application.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } 
        return properties;
    }

    public String applicationName() {
        try {
            return readProperties().getProperty("application.name");
        } catch (IOException e) {
            return "";
        }
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.applicationName());
    }
}
