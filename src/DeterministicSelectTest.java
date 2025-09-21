import java.util.Arrays;
import java.util.Random;

public class DeterministicSelectTest {
    public static void main(String[] args) {
        Random rnd = new Random();
        for (int t = 0; t < 100; t++) {
            int n = 50 + rnd.nextInt(200);
            int[] a = rnd.ints(n, 0, 1000).toArray();
            int[] b = Arrays.copyOf(a, a.length);
            Arrays.sort(b);

            int k = rnd.nextInt(n);
            Metrics m = new Metrics();
            int sel = DeterministicSelect.select(Arrays.copyOf(a, a.length), k, m);

            if (sel != b[k]) {
                System.out.println("Test failed at trial " + t + " (expected " + b[k] + " got " + sel + ")");
                return;
            }
        }
        System.out.println("All 100 tests passed");
    }
}