package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private int[][] grid;
    private WeightedQuickUnionUF wqu;
    private WeightedQuickUnionUF wqu2;

    private int virtualTopSite;
    private int virtualBottomSite;
    private int totalOpenNumber;

    /** Create N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        } else {
            this.N = N;
            this.totalOpenNumber = 0;
            grid = new int[N][N];
            wqu = new WeightedQuickUnionUF(N * N + 2);
            wqu2 = new WeightedQuickUnionUF(N * N + 1);
            virtualTopSite = xyTo1D(N - 1, N - 1) + 1;
            virtualBottomSite = virtualTopSite + 1;
            for (int i = 0; i < N; i++) {
                wqu.union(xyTo1D(0, i), virtualTopSite);
                wqu2.union(xyTo1D(0, i), virtualTopSite);
                wqu.union(xyTo1D(N - 1, i), virtualBottomSite);
            }
        }
    }

    /** Convert a 2D coordinates (x, y) to an 1D integer. */
    private int xyTo1D(int x, int y) {
        return (N * x + y);
    }

    /** Open the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        if ((row < 0 || row > N - 1) || (col < 0 || col > N - 1)) {
            throw new java.lang.IndexOutOfBoundsException();
        } else if (!isOpen(row, col)) {
            grid[row][col] = 1;
            totalOpenNumber++;
            if ((row - 1 >= 0) && isOpen(row - 1, col)) {
                wqu.union(xyTo1D(row, col), xyTo1D(row - 1, col));
                wqu2.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if ((row + 1 <= N - 1) && isOpen(row + 1, col)) {
                wqu.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                wqu2.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            if ((col - 1 >= 0) && isOpen(row, col - 1)) {
                wqu.union(xyTo1D(row, col), xyTo1D(row, col - 1));
                wqu2.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if ((col + 1 <= N - 1) && isOpen(row, col + 1)) {
                wqu.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                wqu2.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
    }

    /** Return whether the site (row, col) is open. */
    public boolean isOpen(int row, int col) {
        if ((row < 0 || row > N - 1) || (col < 0 || col > N - 1)) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            return grid[row][col] > 0;
        }
    }

    /** Return whether the site (row, col) is full. */;
    public boolean isFull(int row, int col) {
        if ((row < 0 || row > N - 1) || (col < 0 || col > N - 1)) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            return isOpen(row, col) && wqu2.connected(virtualTopSite, xyTo1D(row, col));
        }
    }

    /** Return the number of open sites. */
    public int numberOfOpenSites() {
        return totalOpenNumber;
    }

    /** Return whether the system percolates. */
    public boolean percolates() {
        return (numberOfOpenSites() > 0) && wqu.connected(virtualBottomSite, virtualTopSite);
    }

    /** Use for unit testing(not required). */
    public static void main(String[] args) {
        int N = 3;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        p.open(1, 0);
        p.open(1, 1);
        p.open(0, 2);
        p.open(2, 2);
        p.open(2, 2);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.println("i: " + i + "  j: " + j);
                System.out.println("isOpen: " + p.isOpen(i, j));
                System.out.println("isFull: " + p.isFull(i, j));
                System.out.println();
            }
        }
        System.out.println(p.percolates());
        System.out.println(p.numberOfOpenSites());
    }

}
