import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Stringify {
    public static String toString(int[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }
}

class Permutation<T> {

    private List<T> elements;
    private List<List<T>> permutation = new ArrayList<>();
        
    public Permutation(List<T> elements) {
        this.elements = elements;
        generate(new ArrayList<>(), elements);
    }

    private void generate(List<T> fixed, List<T> remain) {
        if (remain.isEmpty()) {
            permutation.add(fixed);
            return;
        } 

        for (T element: remain) {
            List<T> newFixed = new ArrayList<>();
            newFixed.addAll(fixed);
            newFixed.add(element);

            List<T> newRemain = new ArrayList<>();
            newRemain.addAll(remain);
            newRemain.remove(element);
            generate(newFixed, newRemain);
        }
    }

    public List<List<T>> permutation() {
        return permutation;
    }

    public int count() {
        return permutation.size();
    }
}

public class Lucky3 {

    public static class Bet {
        public int[] numbers = {0,0,0};
        public boolean isRumbled = false;
    }

    private Bet bet = null;
    private int[] target = {0,0,0};
    private boolean isPlayerWin = false;

    public void play(Bet bet) {
        setBet(bet);
        generateTarget();
        judge();
    }

    private void generateTarget() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        for (int i = 0; i < 3; i++) {
            target[i] = Math.abs(random.nextInt()) % 10;
        }
    }

    private void judge() {
        isPlayerWin = false;
        
        int[][] all = expandBet();
        for (int[] bet: all) {
            if (isMatch(bet)) {
                isPlayerWin = true;
            }
        }
    }

    public boolean isPlayerWin() {
        return isPlayerWin;
    }

    private int[][] expandBet() {
        if (bet.isRumbled) {
            Permutation<Integer> permutation = new Permutation<>(Arrays.asList(new Integer[]{0,1,2}));
            
            int[][] result = new int[permutation.count()][3];
            int position = 0;
            for (List<Integer> perm: permutation.permutation()) {
                for (int i = 0; i < 3; i++) {
                    result[position][i] = bet.numbers[perm.get(i)];
                }
                position++;
            }
            return result;
        } else {
            return new int[][]{
                bet.numbers
            };
        }
    }

    public int[] target() {
        return target;
    }

    private boolean isMatch(int bet[]) {
        System.out.printf("match %s %s\n", Stringify.toString(bet), Stringify.toString(target));
        return Arrays.equals(bet, target);
    }

    private void setBet(Bet bet) {
        this.bet = bet;
    }

    public static void main(String... args) throws IOException {
        Console console = System.console();
        if (console == null) {
            System.err.println("error: cannot open console");
            System.exit(-1);
        }

        console.writer().println("Lucky3");
        console.writer().println("numbers: ");
        BufferedReader reader = new BufferedReader(console.reader());

        Bet bet = new Bet();
        Scanner scanner = new Scanner(reader);
        for (int i = 0; i < 3; i++) {
            int chr = (int) scanner.nextByte();
            // TODO check with Character.isDigit
            bet.numbers[i] = chr;
        }

        bet.isRumbled = scanner.nextBoolean();
        reader.close();
        
        Lucky3 game = new Lucky3();
        game.play(bet);
        console.writer().println(game.isPlayerWin() ? "win" : "lose");

        for (int i = 0; i < 3; i++) {
            console.writer().println(game.target()[i]);
        }
    }
}
