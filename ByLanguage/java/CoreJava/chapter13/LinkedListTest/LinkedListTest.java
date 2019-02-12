import java.util.*;

public class LinkedListTest {
    public static void main(String[] args) {
        List<String> a = new LinkedList<>();
        a.add("Amy");
        a.add("Carl");
        a.add("Erica");

        List<String> b = new LinkedList<>();
        b.add("Bob");
        b.add("Doug");
        b.add("Frances");
        b.add("Gloria");

        ListIterator<String> it1 = a.listIterator();
        Iterator<String> it2 = b.iterator();

        while (it2.hasNext()) {
            if (it1.hasNext())
                it1.next();

            it1.add(it2.next());
        }

        System.out.println(a);
        while (it2.hasNext()) {
            it2.next();
            if (it2.hasNext()) {
                it2.next();
                it2.remove();
            }
        }

        System.out.println(b);
        a.removeAll(b);
        System.out.println(a);
    }
}
        
