import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("The points argument is null.");
        }
        int n = points.length;
        for (int i = 0; i < n; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("One of the points is null.");
            }
        }
        Point[] sortedPoints = Arrays.copyOf(points, n);
        Arrays.sort(sortedPoints);
        for (int i = 1; i < n; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i - 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points exist.");
            }
        }

        Point[] aux = new Point[n];
        for (int i = 0; i < n; i++) {
            Point p = sortedPoints[i];
            Point[] otherPoints = Arrays.copyOf(sortedPoints, n);
            Arrays.sort(otherPoints, p.slopeOrder());

            int j = 1;
            while (j < n) {
                List<Point> collinearPoints = new ArrayList<>();
                double slope = p.slopeTo(otherPoints[j]);
                do {
                    collinearPoints.add(otherPoints[j]);
                    j++;
                } while (j < n && p.slopeTo(otherPoints[j]) == slope);

                if (collinearPoints.size() >= 3 && p.compareTo(collinearPoints.get(0)) < 0) {
                    segments.add(
                            new LineSegment(p, collinearPoints.get(collinearPoints.size() - 1)));
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
}
