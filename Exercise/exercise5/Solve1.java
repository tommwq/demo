import java.util.Arrays;
import java.util.Scanner;

public class Solve1 {
        public static void main(String... args) {
                new Solve1().solve();
        }

        public void solve() {
                Scanner scanner = new Scanner(System.in);
                int[] num = new int[3];
                num[0] = scanner.nextInt();
                num[1] = scanner.nextInt();
                num[2] = scanner.nextInt();
                Arrays.sort(num);
                for (int i = 0; i < 3; i++) {
                        System.out.printf("%d ", num[i]);
                }
                System.out.println();
        }
}
