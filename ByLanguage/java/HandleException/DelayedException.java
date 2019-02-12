import java.util.ArrayList;
import java.util.List;

// TOOD add toString()
public class DelayedException extends RuntimeException {

    private List<Exception> exceptions = new ArrayList<>();
    private int cursor = 0;

    public DelayedException() {}

    public DelayedException(Exception e) {
        exceptions.add(e);
    }

    public Exception firstException() {
        cursor = 0;
        return nextException();
    }
    
    public Exception nextException() {
        if (cursor < 0 || cursor >= exceptions.size()) {
            return null;
        }
        
        Exception e = exceptions.get(cursor);
        cursor++;
        return e;
    }
    
    public void addException(Exception e) {
        exceptions.add(e);
    }

    public boolean exceptionOccurred() {
        return !exceptions.isEmpty();
    }
    
    public void raise() {
        if (exceptionOccurred()) {
            throw this;
        }
    }
}
