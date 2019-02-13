package SpringBootApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@SpringBootApplication
@Component
public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    @Autowired
    private Greeting greeting;


    public String getGreeting() {
        return greeting.words();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner run(ApplicationContext ctx) {
        return args -> {
            logger.info(getGreeting());
        };
    }
}

@Component
class Greeting {
    private String words = "Hello, World!";
    public String words() {
        return words;
    }
}

