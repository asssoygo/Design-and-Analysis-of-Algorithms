package Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import static java.lang.Math.*;
import static java.util.concurrent.ThreadLocalRandom.current;

public class ClosestPairTest {

    @Test
    public void smallRandomMatchesON2() {
        for (int t = 0; t < 50; t++) {
            int n = 50 + current().nextInt(50);
            ClosestPair.Point[] pts = randPoints(n);
            Metrics m = new Metrics();
            double fast = ClosestPair.solve(pts, m);
            double slow = slowON2(pts);
            assertTrue(abs(fast - slow) < 1e-9);
        }
    }

    @Test
    public void largeRandomRuns() {
        int n = 200000;
        ClosestPair.Point[] pts = randPoints(n);
        Metrics m = new Metrics();
        double fast = ClosestPair.solve(pts, m);
        assertTrue(fast >= 0.0);
        assertTrue(m.maxDepth <= (int)(2* (Math.log(n)/Math.log(2))) + 20);
        System.out.println("n=" + n + " cmp=" + m.cmp + " depth=" + m.maxDepth + " time(ns)=" + m.ns);
    }

    private static ClosestPair.Point[] randPoints(int n){
        ClosestPair.Point[] a = new ClosestPair.Point[n];
        for(int i=0;i<n;i++){
            double x = current().nextDouble(-1e6, 1e6);
            double y = current().nextDouble(-1e6, 1e6);
            a[i] = new ClosestPair.Point(x,y);
        }
        return a;
    }

    private static double slowON2(ClosestPair.Point[] pts){
        double best = Double.POSITIVE_INFINITY;
        for(int i=0;i<pts.length;i++)
            for(int j=i+1;j<pts.length;j++)
                best = Math.min(best, Math.hypot(pts[i].x-pts[j].x, pts[i].y-pts[j].y));
        return best;
    }
}
