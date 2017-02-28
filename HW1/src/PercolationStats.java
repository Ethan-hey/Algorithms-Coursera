/**
 * Created by THINKPAD on 1/13/2017.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private double[] fracs; // fractions of opened tides / total tides
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        fracs = new double[trials];
        for (int i = 0; i < trials; i += 1) {
            Percolation p = new Percolation(n);
            int size = n * n;
            int opened = 0;
            while (!p.percolates()) {
                int[] loca = genenum(p, n);
                p.open(loca[0], loca[1]);
                opened += 1;
            }
            fracs[i] = (double) opened / (double) size;
        }
    }  // perform trials independent experiments on an n-by-n grid

    private int[] genenum(Percolation per, int n) {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);
        int[] loc = new int[2];
        if (per.isOpen(row, col)) {
            return genenum(per, n);
        } else {
            loc[0] = row;
            loc[1] = col;
            return loc;
        }
    }


    public double mean() {
        return StdStats.mean(fracs);
    }                          // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(fracs);
    }                        // sample standard deviation of percolation threshold
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(fracs.length);
    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(fracs.length);
    }                  // high endpoint of 95% confidence interval


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats pstat = new PercolationStats(n, trials);
        System.out.println("mean = " + pstat.mean());
        System.out.println("stddev = " + pstat.stddev());
        System.out.println("95% confidence interval = " + pstat.confidenceLo() + ", " + pstat.confidenceHi());
    }        // test client (described below)
}
