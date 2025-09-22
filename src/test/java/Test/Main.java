import java.util.concurrent.ThreadLocalRandom;
package Test;

public class Main {
    public static void main(String[] args) {
        int n = 10000; // можно менять для тестов
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            double x = ThreadLocalRandom.current().nextDouble(-1e6, 1e6);
            double y = ThreadLocalRandom.current().nextDouble(-1e6, 1e6);
            pts[i] = new ClosestPair.Point(x, y);
        }

        Metrics m = new Metrics();
        double result = ClosestPair.solve(pts, m);

        System.out.println("Closest Pair Distance = " + result);
        System.out.println("n = " + n);
        System.out.println("Comparisons = " + m.cmp);
        System.out.println("Max Recursion Depth = " + m.maxDepth);
        System.out.println("Time (ns) = " + m.ns);
    }
}
