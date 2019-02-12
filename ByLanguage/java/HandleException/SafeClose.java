public class SafeClose {
    public static void close(AutoCloseable closeable, DelayedException delayed) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (Exception e) {
            delayed.addException(e);
        }
    }

    public static void close(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (Exception e) {
            // ignore it
        }
    }
}
