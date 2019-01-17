import java.util.*;

public class OptionalDemo {
    
    public static void main(String... args) {
        Optional<Long> opt = Optional.ofNullable(null);

        System.out.println(opt.get());
    }
}
