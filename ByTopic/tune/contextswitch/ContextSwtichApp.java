class MyRunnable implements Runnable {
        @Override
        public void run() {
                for (int i = 0; i < 100000; i++) {
                        for (int j = 0; j < 100000; j++) {
                                if (j % 10 == 0) {
                                        try {
                                                Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                        }
                                }
                        }
                }
        }
}


public class ContextSwitchApp {
        private int count = 5000;
        private Thread[] threads = new Thread[count];
        
        public static void main(String... args) throws Exception {
                ContextSwitchApp app = new ContextSwitchApp();
                app.start();
                app.join();
        }

        private void start() {
                for (int i = 0; i < count; i++) {
                        threads[i] = new Thread(new MyRunnable());
                        threads[i].start();
                }
        }

        private void join() throws Exception {
                for (int i = 0; i < count; i++) {
                        threads[i].join();
                }
        }
}
