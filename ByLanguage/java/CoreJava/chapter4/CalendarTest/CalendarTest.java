import java.text.DateFormatSymbols;
import java.util.*;

public class CalendarTest {
    public static void main(String[] args) {
        GregorianCalendar date = new GregorianCalendar();
        int today = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH);

        date.set(Calendar.DAY_OF_MONTH, 1);
        int weekday = date.get(Calendar.DAY_OF_WEEK);
        int firstDayOfWeek = date.getFirstDayOfWeek();
        int indent = 0;
        while (weekday != firstDayOfWeek) {
            indent++;
            date.add(Calendar.DAY_OF_MONTH, -1);
            weekday = date.get(Calendar.DAY_OF_WEEK);
        }

        String[] weekdayNames = new DateFormatSymbols().getShortWeekdays();
        do {
            System.out.printf("%4s", weekdayNames[weekday]);
            date.add(Calendar.DAY_OF_MONTH, 1);
            weekday = date.get(Calendar.DAY_OF_WEEK);
        } while (weekday != firstDayOfWeek);

        System.out.println();
        for (int i = 1; i <= indent; i++) {
            System.out.print("    ");
        }

        date.set(Calendar.DAY_OF_MONTH, 1);
        do {
            int day = date.get(Calendar.DAY_OF_MONTH);
            System.out.printf("%3d", day);
            if (day == today) {
                System.out.print("*  ");
            } else {
                System.out.print("   ");
            }

            date.add(Calendar.DAY_OF_MONTH, 1);
            weekday = date.get(Calendar.DAY_OF_WEEK);

            if (weekday == firstDayOfWeek) {
                System.out.println();
            }
        } while (date.get(Calendar.MONTH) == month);

        if (weekday != firstDayOfWeek) {
            System.out.println();
        }
    }
}
