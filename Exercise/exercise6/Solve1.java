public class Solve1 {
        private static final int[] character = new int[]{
                0x38, 0x6c, 0x44, 0x40,
                0x40, 0x40, 0x44, 0x4c,
                0x38
        };
        
        public static void main(String... args) {
                new Solve1().solve();
        }

        public void solve() {
                for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 8; j++) {
                                int mask = 1 << (7 - j);
                                printDot((mask & character[i]) != 0);
                        }
                        System.out.println();
                }
        }

        public void printDot(boolean solid) {
                System.out.printf("%c", solid ? '*' : ' ');
        }
}
