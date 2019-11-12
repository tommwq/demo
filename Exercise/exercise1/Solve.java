import java.util.ArrayList;
import java.util.List;

class Combine extends ArrayList<Integer> {
        public Combine() {}
        
        public Combine(List<Integer> list) {
                if (list == null) {
                        return;
                }

                addAll(list);
        }
}

public class Solve {
        
        public static void main(String... args) {
                List<Integer> list = sequence(5);
                List<Combine> combination = combine(list, 3);
                for (Combine combine: combination) {
                        printList(combine);
                }

                printList(list);
        }

        public static List<Integer> sequence(int size) {
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                        list.add(i);
                }

                return list;
        }

        public static <T> List<T> emptyList() {
                return new ArrayList<T>();
        }

        public static <T> void printList(List<T> list) {
                if (list == null) {
                        return;
                }

                for (T t: list) {
                        System.out.print(t == null ? "null" : t.toString());
                        System.out.print(" ");
                }
                System.out.println(" ");
        }

        // public List<List<Integer>> permutation(int n) {
        // }

        public static List<Combine> combine(List<Integer> list, int combine_size) {
                if (list == null || combine_size == 0 || combine_size < list.size()) {
                        return emptyList();
                }
                
                if (combine_size == list.size()) {
                        ArrayList<Combine> result = new ArrayList<>();
                        result.add(new Combine(list));
                        return result;
                }

                List<Combine> list1 = combine(

                return null;
        }
}
