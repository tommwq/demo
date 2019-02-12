public class PairTest {
    public static void main(String[] args) {
        String[] words = { "Mary", "had", "a", "little", "lamb" };
        Pair<String,String> mm = ArrayAlg.minmax(words);
        System.out.println("min = " + mm.first());
        System.out.println("max = " + mm.second());
    }
}

class Pair<T,U> {
    private T first;
    private U second;

    public Pair(T f, U s) {
        first = f;
        second = s;
    }

    public T first() {
        return first;
    }

    public U second() {
        return second;
    }
}

class ArrayAlg {
    public static Pair<String,String> minmax(String[] a) {
        if (a == null || a.length == 0) {
            return null;
        }

        String min = a[0];
        String max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0)
                min = a[i];

            if (max.compareTo(a[i]) < 0)
                max = a[i];
        }

        return new Pair<>(min, max);
    }
}
