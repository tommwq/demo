public class Solve1 {
        public static void main(String... args) {
                fib(7);
        }

        public static void fib(int n) {
                int a = 0;
                int b = 1;
                int t;

                for (int i = 0; i < n; i++) {
                        t = a;
                        a = b;
                        b = a + t;
                        System.out.println(a);
                }
        }
}
