public class Solve1 {
        public static void main(String... args) {
                int[] prime = new int[100];
                for (int i = 0; i < 100; i++) {
                        prime[i] = 1;
                }

                for (int i = 0; i < 100; i++) {
                        int n = i + 101;
                        int p = 2;
                        while (p < 100) {
                                if (n % p == 0) {
                                        prime[i] = 0;
                                }
                                p += (p == 2) ? 1 : 2;
                        }
                }

                int count = 0;
                for (int i = 0; i < 100; i++) {
                        if (prime[i] == 1) {
                                count++;
                                System.out.println(i + 101);
                        }
                }
        }
}
