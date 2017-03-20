import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Locale;

/**
 * Created by santhoshvai on 18/03/17.
 */
public class PercolationStats {

    private double[] trials;
    private int noOfTrials;
    private double mean;
    private double stddev;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = new double[trials];
        noOfTrials = trials;
        int gridSize = n * n;

        for (int i = 0; i <= (trials-1); i++) {
            /*
            Initialize all sites to be blocked.
            Repeat the following until the system percolates:
                Choose a site uniformly at random among all blocked sites.
                Open the site.
            The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
             */
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }
            this.trials[i] = ((double) percolation.numberOfOpenSites()) / (double) gridSize;
        }
        mean = StdStats.mean(this.trials);
        if (noOfTrials == 1) stddev = Double.NaN;
        else stddev = StdStats.stddev(this.trials);

    }
    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - ((1.96 * stddev())/Math.sqrt(noOfTrials)));
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + ((1.96 * stddev())/Math.sqrt(noOfTrials)));
    }

    // test client (described below)
    public static void main(String[] args) {
        if (args.length < 2)
        {
            throw new java.lang.IllegalArgumentException();
        }
        int n = Integer.parseInt(args[0]); // N-by-N grid
        int t = Integer.parseInt(args[1]); // perform T independent computational experiments

        PercolationStats per = new PercolationStats(n, t);
        StdOut.printf(Locale.US, "mean                    = %f\n", per.mean());
        StdOut.printf(Locale.US, "stddev                  = %f\n", per.stddev());
        StdOut.printf(Locale.US, "95%% confidence interval = [%f, %f]\n", per.confidenceLo(), per.confidenceHi());
    }
}
