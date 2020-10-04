package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.List;
import java.util.ArrayList;

public class Percolation {
    private final WeightedQuickUnionUF grid;
    private final List<Integer> openSites;
    private final int size;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N){
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("Percolation grid size must be positive integer");
        }
        grid = new WeightedQuickUnionUF(N*N + 2); // 0 = top, N*N + 1 = bottom
        for (int i = 1; i <= N; i += 1) {
            grid.union(0, i);
        }
        for (int j = N*N - N + 1; j <= N*N; j += 1) {
            grid.union(N*N + 1, j);
        }
        openSites = new ArrayList<>();
        size = N;
    }

    // Converts (row, col) to position in WeightedQuickUnion
    private int xyTo1D(int row, int col) {
        return (size * row) + col + 1;
    }

    private boolean inGrid(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException("Row and Column must be within prescribed range!");
        }
        if (!isOpen(row, col)) {
            openSites.add(xyTo1D(row, col));
            if (inGrid(row - 1, col) && isOpen(row - 1, col)) {
                grid.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (inGrid(row + 1, col) && isOpen(row + 1, col)) {
                grid.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            if (inGrid(row, col - 1) && isOpen(row, col - 1)) {
                grid.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (inGrid(row, col + 1) && isOpen(row, col + 1)) {
                grid.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException("Row and Column must be within prescribed range!");
        }
        return openSites.contains(xyTo1D(row, col));
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException("Row and Column must be within prescribed range!");
        }
        return grid.connected(0, xyTo1D(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites.size();
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.connected(0, size*size + 1);
    }

    public static void main(String[] args){}  // use for unit testing (not required, but keep this here for the autograder)
}
