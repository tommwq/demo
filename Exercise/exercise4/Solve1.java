import java.util.Scanner;

public class Solve1 {
        private static final int[] DAYS_IN_MONTH = {
                31, 29, 31, 30,
                31, 30, 31, 31,
                30, 31, 30, 31
        };
        
        public static void main(String... args) {
                new Solve1().solve();
        }

        public void solve() {
                Scanner scanner = new Scanner(System.in);
                int year, month, day;
                year = scanner.nextInt();
                month = scanner.nextInt();
                day = scanner.nextInt();

                System.out.println(dayOfYear(year, month, day));
        }

        public int dayOfYear(int year, int month, int dayOfMonth) {
                if (!isValidDate(year, month, dayOfMonth)) {
                        throw new IllegalArgumentException("illegal date");
                }

                int dayOfYear = 0;
                for (int i = 0; i < month - 1; i++) {
                        dayOfYear += DAYS_IN_MONTH[i];
                }
                dayOfYear += dayOfMonth;
                return dayOfYear;
        }

        public boolean isValidDate(int year, int month, int day) {
                if (month < 1 || month > 12) {
                        return false;
                }

                if (day < 1 || day > DAYS_IN_MONTH[month]) {
                        return false;
                }

                if ((!isLeapYear(year)) && month == 2 && day > DAYS_IN_MONTH[1] - 1) {
                        return false;
                }

                if (year == 1752 && month == 9 && day >= 3 && day <= 13) {
                        return false;
                }
                return true;
        }

        public boolean isLeapYear(int year) {
                if (year % 400 == 0) {
                        return true;
                }

                if (year % 100 != 0 && year % 4 == 0) {
                        return true;
                }

                return false;
        }
}
