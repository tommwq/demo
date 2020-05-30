public class PrintCompilation {

    private static volatile long x;
    
    public static void main(String[] args) throws Exception {

        while (true) {
            long time = System.currentTimeMillis();
            if (time % 2 == 0) {
                System.out.println("loop");
            }
            x = loop(time);
            System.out.println("sleep");
            Thread.sleep(1000);
        }
    }

    public static long loop(long x) {
        for (int i = 0;i < 1000; i++) {
            x *= 3;
        }
        return x;
    }
}
