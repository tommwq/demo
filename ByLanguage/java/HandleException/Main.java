
class Resource1 implements AutoCloseable {
    public void close() {
        throw new RuntimeException("r1");
    }
}

class Resource2 implements AutoCloseable {
    public void close() {
        throw new RuntimeException("r2");
    }
}

public class Main {
    public static void main(String... args) {
        
        try {
            DelayedException delayed = new DelayedException();
            Resource1 r1 = null;
            Resource2 r2 = null;
            
            try {
                r1 = new Resource1();
                r2 = new Resource2();
            } finally {
                SafeClose.close(r1, delayed);
                SafeClose.close(r2, delayed);

                System.out.println("captured");
                
                delayed.raise();
            }
        } catch (DelayedException e) {
            for (Exception ex = e.firstException(); ex != null; ex = e.nextException()) {
                System.out.println(ex);
            }
        }
    }
}
