import java.util.*;

public class Retirement2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How much money will you contribute every year? ");
        double payment = scanner.nextDouble();

        System.out.print("Interest rate in %: ");
        double interestRate = scanner.nextDouble();

        double balance = 0;
        int year = 0;
        String input;

        do {
            balance += payment;
            double interest = balance * interestRate / 100;
            balance += interest;

            year++;

            System.out.printf("After year %d, your balance is %,.2f%n", year, balance);

            System.out.print("Ready to retire? (Y/N) ");
            input = scanner.next();
        } while (input.equals("N"));
    }
}
