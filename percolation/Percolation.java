/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;
    private final boolean[][] opened;
    private final int size;
    // TODO: change bottom to a list to avoid 'backwash'
    private final int bottom;
    private int openSites;
    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        bottom = size * size + 1;
        uf = new WeightedQuickUnionUF(size * size + 2);
        opened = new boolean[size][size];
        openSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkException(row, col);
        opened[row - 1][col - 1] = true;
        ++openSites;

        int indexNum = getQuickFindIndex(row, col);
        // if top row opened, union(cell, top)
        if (row == 1) {
            uf.union(indexNum, TOP);
        }

        if (row == size) {
            uf.union(indexNum, bottom);
        }

        // if any of cells in the middle rows are opened then check for neighbouring unions
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(indexNum, getQuickFindIndex(row - 1, col));
        }

        if (row < size && isOpen(row + 1, col)) {
            uf.union(indexNum, getQuickFindIndex(row + 1, col));
        }

        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(indexNum, getQuickFindIndex(row, col - 1));
        }

        if (col < size && isOpen(row, col + 1)) {
            uf.union(indexNum, getQuickFindIndex(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkException(row, col);
        return opened[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkException(row, col);
        return uf.find(TOP) == uf.find(getQuickFindIndex(row, col));

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(TOP) == uf.find(bottom);
    }

    private void checkException(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException();
        }
    }

    private int getQuickFindIndex(int row, int col) {
        return size * (row - 1) + col;
    }
    // test client (optional)
    // public static void main(String[] args)
}