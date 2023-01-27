/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int experimentCount;
    private final double[] fractions;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        experimentCount = trials;
        fractions = new double[experimentCount];
        for (int expNum = 0; expNum < experimentCount; expNum++) {
            Percolation pr = new Percolation(n);
            int openedSites = 0;
            while (!pr.percolates()) {
                int i = StdRandom.uniformInt(1, n + 1), j = StdRandom.uniformInt(1, n + 1);
                if (!pr.isOpen(i, j)) {
                    pr.open(i, j);
                    openedSites++;
                }
            }
            double fraction = (double) openedSites / (n * n);
            fractions[expNum] = fraction;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev();
    }

    public static void main(String[] args) {
        // int[] input = StdIn.readAllInts();
        int n = Integer.parseInt(args[0]), t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);

        String confidence = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
