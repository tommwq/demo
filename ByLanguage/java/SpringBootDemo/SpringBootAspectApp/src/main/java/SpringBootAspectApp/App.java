package SpringBootAspectApp;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@SpringBootApplication
@EnableAspectJAutoProxy
public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    @Autowired
    private Greeting greeting;
    
    public String getGreeting() {
        return greeting.greeting();
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
    public String greeting() {
        return "Hello!";
    }
}


@Aspect
@Component
class Decorator {
    @Before("execution(* *.greeting(..))")
    public void printTime() {
        System.out.println(new Date());
    }
}
