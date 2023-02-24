/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;
    private int count;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("The argument is null.");
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("One of the points is null.");
            }
        }
        int n = points.length;
        Point[] copy = points.clone();
        Arrays.sort(copy);
        for (int i = 0; i < n - 1; i++) {
            if (copy[i].compareTo(copy[i + 1]) == 0) {
                throw new IllegalArgumentException("There are duplicate points.");
            }
        }
        segments = new LineSegment[n * n];
        count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double slope1 = copy[i].slopeTo(copy[j]);
                for (int k = j + 1; k < n; k++) {
                    double slope2 = copy[i].slopeTo(copy[k]);
                    if (slope1 == slope2) {
                        for (int l = k + 1; l < n; l++) {
                            double slope3 = copy[i].slopeTo(copy[l]);
                            if (slope1 == slope3) {
                                segments[count++] = new LineSegment(copy[i], copy[l]);
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        LineSegment[] copy = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            copy[i] = segments[i];
        }
        return copy;
    }
}
