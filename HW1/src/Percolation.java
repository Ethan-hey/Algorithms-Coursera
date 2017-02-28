/**
 * Created by THINKPAD on 1/12/2017.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int opened;
    private int size;
    private int length;
    private WeightedQuickUnionUF unionUF;
    private WeightedQuickUnionUF unionBW;
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        grid = new boolean[n][n];
        opened = 0;
        size = n * n;
        length = n;
        unionUF = new WeightedQuickUnionUF(size + 2);
        unionBW = new WeightedQuickUnionUF(size + 1);
    }
    private int calsequence(int row, int col) {
        return (row - 1) * length + col;
    }

    private boolean validclosed(int row, int col) {
        return (row > 0 && row <= length && col > 0 && col <= length && (!grid[row - 1][col - 1]));
    }

    private boolean validopened(int row, int col) {
        return (row > 0 && row <= length && col > 0 && col <= length && grid[row - 1][col - 1]);
    }

    private void validintput(int row, int col) {
        if (row <= 0 || row > length) {
            throw new IndexOutOfBoundsException("row index " + Integer.toString(row) + " out of bound");
        }
        if (col <= 0 || col > length) {
            throw new IndexOutOfBoundsException("col index " + Integer.toString(col) + " out of bound");
        }
    }


    public void open(int row, int col) {
        validintput(row, col);
        int seq = calsequence(row, col);
        // check itself
        if (validclosed(row, col)) {
            grid[row - 1][col - 1] = true;
            opened += 1;
        }
        if (row == 1) {
            unionUF.union(0, seq);
            unionBW.union(0, seq);
        }
        if (row == length) {
            unionUF.union(size + 1, seq);
        }
        // check and open up
        if (validopened(row - 1, col)) {
            unionUF.union(seq, calsequence(row - 1, col));
            unionBW.union(seq, calsequence(row - 1, col));
        }
        // check and open down
        if (validopened(row + 1, col)) {
            unionUF.union(seq, calsequence(row + 1, col));
            unionBW.union(seq, calsequence(row + 1, col));
        }
        // check and open left
        if (validopened(row, col - 1)) {
            unionUF.union(seq, calsequence(row, col - 1));
            unionBW.union(seq, calsequence(row, col - 1));
        }
        // check and open right
        if (validopened(row, col + 1)) {
            unionUF.union(seq, calsequence(row, col + 1));
            unionBW.union(seq, calsequence(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        validintput(row, col);
        return grid[row - 1][col - 1];
    } // is site (row, col) open?

    private boolean percolated = false;

    public boolean isFull(int row, int col) {
        validintput(row, col);
//        if (percolates() && !percolated) {
//            percolated = true;
//            return unionUF.connected(0, calsequence(row, col));
//        } else if (percolates() && percolated) {
//            return unionBW.connected(0, calsequence(row, col));
//        } else {
//            return unionUF.connected(0, calsequence(row, col));
//        }
        return unionBW.connected(0, calsequence(row, col));

    } // is site (row, col) full?


    public int numberOfOpenSites() {
        return opened;
    }      // number of open sites

    public boolean percolates() {
        return unionUF.connected(0, size + 1);
    } // does the system percolate?

    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        p.open(12, 6);
    }   // test client (optional)
}
