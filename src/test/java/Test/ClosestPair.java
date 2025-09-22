import java.util.*;

public final class ClosestPair {

    private ClosestPair() {}

    public static final class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
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
            m.ns = System.nanoTime() - t;
        }
    }

    private static double rec(Point[] px, Point[] py, int L, int R, Metrics m) {
        m.in();
        try {
            int n = R - L;
            if (n <= 3) return brute(px, L, R, m);

            int mid = L + n / 2;
            double midx = px[mid].x;

            List<Point> pyl = new ArrayList<>();
            List<Point> pyr = new ArrayList<>();
            for (Point p : py) {
                if (p.x <= midx) pyl.add(p); else pyr.add(p);
            }

            double dl = rec(px, pyl.toArray(new Point[0]), L, mid, m);
            double dr = rec(px, pyr.toArray(new Point[0]), mid, R, m);
            double d = Math.min(dl, dr);

            List<Point> strip = new ArrayList<>();
            for (Point p : py) if (Math.abs(p.x - midx) < d) strip.add(p);

            for (int i = 0; i < strip.size(); i++) {
                for (int j = i + 1; j < strip.size() && j <= i + 7; j++) {
                    m.cmp++;
                    d = Math.min(d, dist(strip.get(i), strip.get(j)));
                }
            }
            return d;
        } finally {
            m.out();
        }
    }

    private static double brute(Point[] pts, int L, int R, Metrics m) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = L; i < R; i++) {
            for (int j = i + 1; j < R; j++) {
                m.cmp++;
                best = Math.min(best, dist(pts[i], pts[j]));
            }
        }
        return best;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
