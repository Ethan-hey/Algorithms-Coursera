/**
 * Created by THINKPAD on 1/23/2017.
 */
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.LinkedList;

public class Board {

    private final int[][] tiles;
    private final int dimen;

    public Board(int[][] blocks) {
        dimen = blocks.length;
        tiles = new int[dimen][dimen];
        for (int i = 0; i < dimen; i++) {
            for (int j = 0; j < dimen; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }
    }
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)

    public int dimension() {
        return dimen;
    }                 // board dimension n

    public int hamming() {
        int counter = 0;
        for (int i = 0; i < dimen; i++) {
            for (int j = 0; j < dimen; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != 1 + i * dimen + j) {
                    counter++;
                }
            }
        }
        return counter;
    }                   // number of blocks out of place

    public int manhattan() {
        int counter = 0;
        for (int i = 0; i < dimen; i++) {
            for (int j = 0; j < dimen; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != 1 + i * dimen + j) {
                    int[] ns = twoD(tiles[i][j]);
                    int row = ns[0];
                    int col = ns[1];
                    counter += (Math.abs(row - i) + Math.abs(col - j));
                }
            }
        }
        return counter;
    }                 // sum of Manhattan distances between blocks and goal

    public boolean isGoal() {
        for (int i = 0; i < dimen; i++) {
            for (int j = 0; j < dimen; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != 1 + i * dimen + j) {
                    return false;
                }
            }
        }
        return true;
    }                // is this board the goal board?

    public Board twin() {
        int row1;
        int col1;
        int row2;
        int col2;
        if (tiles[0][0] != 0) {
            row1 = col1 = 0;
            if (tiles[0][1] != 0) {
                row2 = 0;
                col2 = 1;
            } else {
                row2 = 1;
                col2 = 0;
            }
        } else {
            row1 = 0;
            col1 = 1;
            row2 = 1;
            col2 = 0;
        }
        int temp;
        int[][] tilecopy = copytiles();
        temp = tilecopy[row1][col1];
        tilecopy[row1][col1] = tilecopy[row2][col2];
        tilecopy[row2][col2] = temp;
        return new Board(tilecopy);
    }                    // a board that is obtained by exchanging any pair of blocks

    private int[][] copytiles() {
        int[][] copyed = new int[dimen][dimen];
        for (int i = 0; i < dimen; i++) {
            for (int j = 0; j < dimen; j++) {
                copyed[i][j] = tiles[i][j];
            }
        }
        return copyed;
    }

    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            return false;
        }
        if (((Board) y).dimen != this.dimen) {
            return false;
        }
        for (int i = 0; i < dimen; i++) {
            for (int j = 0; j < dimen; j++) {
                if (tiles[i][j] != ((Board) y).tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }        // does this board equal y?

    public Iterable<Board> neighbors() {
        LinkedList<Board> tilelist = new LinkedList<>();
        int blank = findblank();
        int row = twoD(blank)[0];
        int col = twoD(blank)[1];
        if (row != 0) {
            int[][] upcopy = copytiles();
            int temp = upcopy[row][col];
            upcopy[row][col] = upcopy[row - 1][col];
            upcopy[row - 1][col] = temp;
            tilelist.add(new Board(upcopy));
        }
        if (row != (dimen - 1)) {
            int[][] downcopy = copytiles();
            int temp = downcopy[row][col];
            downcopy[row][col] = downcopy[row + 1][col];
            downcopy[row + 1][col] = temp;
            tilelist.add(new Board(downcopy));
        }
        if (col != 0) {
            int[][] leftcopy = copytiles();
            int temp = leftcopy[row][col];
            leftcopy[row][col] = leftcopy[row][col - 1];
            leftcopy[row][col - 1] = temp;
            tilelist.add(new Board(leftcopy));
        }
        if (col != (dimen - 1)) {
            int[][] rightcopy = copytiles();
            int temp = rightcopy[row][col];
            rightcopy[row][col] = rightcopy[row][col + 1];
            rightcopy[row][col + 1] = temp;
            tilelist.add(new Board(rightcopy));
        }
        return tilelist;

    }     // all neighboring boards

    private int[] twoD(int n) {
        int[] ns = new int[2];
        ns[0] = (n -1) / dimen;
        ns[1] = (n - 1) % dimen;
        return ns;
    }

    private int toN(int[] ns) {
        int row = ns[0];
        int col = ns[1];
        return row * dimen + col + 1;
    }

    private int findblank() {
        int n = 0;
        for (int i = 0; i < dimen; i++) {
            for(int j = 0; j < dimen; j++) {
                if (tiles[i][j] == 0) {
                    int[] ns = {i, j};
                    n = toN(ns);
                    break;
                }
            }
        }
        return n;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimen + "\n");
        for (int i = 0; i < dimen; i++) {
            for (int j = 0; j < dimen; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }               // string representation of this board (in the output format specified below)

    public static void main(String[] args) {
//        int[][] t = {{1, 2, 3},  {4, 5, 6}, {7, 8, 0}};
//        Board b = new Board(t);
//        StdOut.println(b.equals(b));
//        StdOut.println(b.isGoal());
//        StdOut.println(b.hamming());
//        StdOut.println(b.manhattan());
//        for (Board board : b.neighbors()) {
//            System.out.println("original" + b.toString());
//            System.out.println(board.toString());
//        }
//        for (int i : b.twoD(5)) {
//            System.out.println(i);
//        }
//        int row = 2;
//        int col = 2;
//        int[] ns = {row, col};
//        System.out.println(b.toN(ns));

    } // unit tests (not graded)
}
