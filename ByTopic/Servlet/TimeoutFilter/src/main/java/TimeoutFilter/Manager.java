package TimeoutFilter;

import java.util.*;
import java.util.concurrent.*;

public class Manager {

    private static boolean started = false;
    private static Map<Thread, Long> map;

    static {
        map = new ConcurrentHashMap<>();
    }

    public static Long now() {
        return System.currentTimeMillis();
    }
    
    public static void onRequest(Thread thread) {
        map.put(thread, now());
    }

    public static void onResponse(Thread thread) {
        map.remove(thread);
    }

    public static void checkTimeout() {
        Long now = now();

        Set<Map.Entry<Thread,Long>> entries = map.entrySet();
        for (Map.Entry<Thread,Long> entry: entries) {
            Thread thread = entry.getKey();
            Long startTime = entry.getValue();
            if (now - startTime > 2000) {
                thread.interrupt();
            }
        }
    }

    public static void start() {
        if (started) {
            return;
        }

        started = true;
        try {
            while (true) {
                checkTimeout();
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            started = false;            
        }
    }
}
