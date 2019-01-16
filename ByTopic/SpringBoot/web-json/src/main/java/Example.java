import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class Example {

    public static class Message {
        private String content;
        public String getContent() {
            return content;
        }

        public void setContent(String value) {
            content = value;
        }
    }
    
    @RequestMapping("/")
    Message home() {
        Message message = new Message();
        message.setContent("Hello World!");
        return message;
    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }
}
