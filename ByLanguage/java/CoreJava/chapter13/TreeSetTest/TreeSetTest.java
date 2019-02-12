import java.util.*;

public class TreeSetTest {
    public static void main(String[] args) {
        SortedSet<Item> parts = new TreeSet<>();
        parts.add(new Item("Toaster", 1234));
        parts.add(new Item("Widget", 4562));
        parts.add(new Item("Modem", 9912));
        System.out.println(parts);

        SortedSet<Item> sortByDescription = new TreeSet<>(new Comparator<Item>() {
                public int compare(Item a, Item b) {
                    String da = a.getDescription();
                    String db = b.getDescription();
                    return da.compareTo(db);
                }
            });

        sortByDescription.addAll(parts);
        System.out.println(sortByDescription);
    }
}

class Item implements Comparable<Item> {
    private String description;
    private int partNumber;

    public Item(String desc, int num) {
        description = desc;
        partNumber = num;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return "[description=" + description + ", partNumber=" + partNumber + "]";
    }

    public boolean equals(Object operand) {
        if (this == operand) return true;
        if (operand == null) return false;
        Item another = (Item) operand;
        return Objects.equals(description, another.description) &&
            partNumber == another.partNumber;
    }

    public int hashCode() {
        return Objects.hash(description, partNumber);
    }

    public int compareTo(Item other) {
        return Integer.compare(partNumber, other.partNumber);
    }
}
                  
