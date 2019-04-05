// 测试多线程volatile int加法在没有锁保护的情况下，是否会出现错误。
// 结论：会出现错误。
// 2019年03月17日

public class VolatileAdd {

    private volatile int value = 0;
    private class AddThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000 * 1000; i++) {
                value++;
            }
        }
    }

    private void runTest() throws Exception {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(value);
    }

    public static void main(String[] args) throws Exception {
        new VolatileAdd().runTest();
    }
}
