/**
 * Created by santhoshvai on 18/03/17.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private enum Site {
        OPEN,
        BLOCKED;
    }

    private Site[] grid;
    private int mN;
    private WeightedQuickUnionUF uf;
    private int noOfOpenSites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        // By convention, the row and column indices are integers between 1 and n, where (1, 1) is the upper-left site:
        // The constructor should throw a java.lang.IllegalArgumentException if n ≤ 0.
        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }

        mN = n;
        noOfOpenSites = 0;
        int size = (n * n)+2;
        grid = new Site[size];
        uf = new WeightedQuickUnionUF(size);
        for (int i = 0; i < size; i++) {
            grid[i] = Site.BLOCKED;
        }

        // these two are dummy sites to speed up if system percolates
        for (int col = 1; col <= mN; col++) {
            // connect first row and first dummy
            uf.union(xyTo1D(1, col), 0);
            // connect last row and last dummy
            uf.union(xyTo1D(mN, col), size-1);
        }
    }
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row > mN || row == 0) || (col > mN || col == 0)) throw new IndexOutOfBoundsException();

        int index = xyTo1D(row, col);

        if (grid[index] == Site.OPEN) return;

        grid[index] = Site.OPEN;

        // connect to left if open
        if (col > 1) {
            if (grid[index-1] == Site.OPEN) uf.union(index, index-1);
        }
        // connect to right if open
        if (col < mN) {
            if (grid[index+1] == Site.OPEN) uf.union(index, index+1);
        }
        // connect to up if open
        if (row > 1) {
            if (grid[index-mN] == Site.OPEN) uf.union(index, index-mN);
        }
        // connect to down if open
        if (row < mN) {
            if (grid[index+mN] == Site.OPEN) uf.union(index, index+mN);
        }
        noOfOpenSites++;
    }
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row > mN || row == 0) || (col > mN || col == 0)) throw new IndexOutOfBoundsException();
        return grid[mN*(row-1) + col] == Site.OPEN;
    }
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row > mN || row == 0) || (col > mN || col == 0)) throw new IndexOutOfBoundsException();
        return isOpen(row, col) && uf.connected(xyTo1D(row, col), 0);
    }
    // number of open sites
    public int numberOfOpenSites() {
        return noOfOpenSites;
    }
    // does the system percolate?
    public boolean percolates()  {
        return uf.connected(0, grid.length - 1) && (noOfOpenSites > 0);
    }

    private int xyTo1D(int row, int col) {
        return (mN)*(row-1) + col;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(20);
        percolation.open(1, 1);
        percolation.open(2, 1);
        for (int i = 3; i <= 20; i++) {
            percolation.open(i, 1);
        }
        StdOut.println(percolation.isFull(2, 1));
        StdOut.println(percolation.noOfOpenSites);
        StdOut.println(percolation.percolates());
    }
}
