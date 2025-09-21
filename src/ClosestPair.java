import java.util.*;

public final class ClosestPair {

    private ClosestPair() {}

    public static final class Point {
        public final double x, y;
        public Point(double x, double y){ this.x = x; this.y = y; }
    }

    public static double solve(Point[] pts, Metrics m) {
        long t = System.nanoTime();
        try {
            if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
            Point[] px = Arrays.copyOf(pts, pts.length);
            Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
            Point[] py = Arrays.copyOf(px, px.length);
            return rec(px, py, 0, px.length, m);
        } finally {
            m.ns += System.nanoTime() - t;
        }
    }

    private static double rec(Point[] px, Point[] py, int L, int R, Metrics m) {
        m.in();
        try {
            int n = R - L;
            if (n <= 3) return brute(px, L, R, m);
            int mid = L + n/2;
            double midX = px[mid].x;
            Point[] pyl = new Point[n];
            Point[] pyr = new Point[n];
            int il = 0, ir = 0;
            for (Point p : py) {
                if (p.x < midX || (p.x == midX && indexOf(px, L, mid, p) >= 0)) pyl[il++] = p;
                else pyr[ir++] = p;
            }
            double dl = rec(px, Arrays.copyOf(pyl, il), L, mid, m);
            double dr = rec(px, Arrays.copyOf(pyr, ir), mid, R, m);
            double d = Math.min(dl, dr);
            ArrayList<Point> strip = new ArrayList<>();
            for (Point p : py) if (Math.abs(p.x - midX) < d) strip.add(p);
            for (int i = 0; i < strip.size(); i++) {
                for (int j = i + 1; j < strip.size() && j <= i + 7; j++) {
                    d = Math.min(d, dist(strip.get(i), strip.get(j), m));
                }
            }
            return d;
        } finally {
            m.out();
        }
    }

    private static int indexOf(Point[] a, int L, int R, Point p){
        for (int i = L; i < R; i++) if (a[i] == p) return i;
        return -1;
    }

    private static double brute(Point[] a, int L, int R, Metrics m){
        double best = Double.POSITIVE_INFINITY;
        for (int i = L; i < R; i++){
            for (int j = i+1; j < R; j++){
                best = Math.min(best, dist(a[i], a[j], m));
            }
        }
        return best;
    }

    private static double dist(Point p, Point q, Metrics m){
        m.cmp++;
        double dx = p.x - q.x, dy = p.y - q.y;
        return Math.hypot(dx, dy);
    }
}
