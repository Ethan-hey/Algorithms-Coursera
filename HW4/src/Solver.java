import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by THINKPAD on 1/24/2017.
 */
public class Solver {

    private boardNode iniNode;
    private boardNode twin_iniNode;
    private MinPQ<boardNode> pQueue = new MinPQ<boardNode>(new boardNodeComparator());
    private MinPQ<boardNode> twin_pQueue = new MinPQ<boardNode>(new boardNodeComparator());
    private LinkedList<Board> deleted;
    private LinkedList<Board> twin_deleted;
    private boardNode last;
    private boolean solvable = false;
    private ArrayList<Board> solution;

    private class boardNode {

        private Board board;
        private boardNode prev;
        private int moves;
        private int priority;

        boardNode(Board board) {
            this.board = board;
        }
    }

    private class boardNodeComparator implements Comparator<boardNode> {
        public int compare(boardNode n1, boardNode n2) {
            return n1.priority - n2.priority;
        }
    }


    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.NullPointerException();
        }
        iniNode = new boardNode(initial);
        twin_iniNode = new boardNode(initial.twin());
        twin_iniNode.moves = iniNode.moves = 0;
        twin_iniNode.priority = iniNode.priority = initial.manhattan();
        twin_iniNode.prev = iniNode.prev = null;
        pQueue.insert(iniNode);
        twin_pQueue.insert(twin_iniNode);
        last = solve();
    }           // find a solution to the initial board (using the A* algorithm)

    private boardNode solve() {
        deleted = new LinkedList<>();
        twin_deleted = new LinkedList<>();
        boardNode del;
        boardNode twin_del;
        while (!(del = pQueue.delMin()).board.isGoal()
                && !(twin_del = twin_pQueue.delMin()).board.isGoal()) {
            deleted.addFirst(del.board);
            twin_deleted.addFirst(twin_del.board);
            if (deleted.size() >= 5) {
                deleted.removeLast();
            }
            if (twin_deleted.size() >= 5) {
                twin_deleted.removeLast();
            }
            for (Board board : del.board.neighbors()) {
                if (del.prev == null || !board.equals(del.prev.board)) {
                    boardNode bnode = new boardNode(board);
                    bnode.prev = del;
                    bnode.moves = del.moves + 1;
                    bnode.priority = board.manhattan() + bnode.moves;
                    pQueue.insert(bnode);
                }
            }
            for (Board board : twin_del.board.neighbors()) {
                if (twin_del.prev == null || !board.equals(twin_del.prev.board)) {
                    boardNode bnode = new boardNode(board);
                    bnode.prev = twin_del;
                    bnode.moves = twin_del.moves + 1;
                    bnode.priority = board.manhattan() + bnode.moves;
                    twin_pQueue.insert(bnode);
                }
            }
        }
        if (del.board.isGoal()) {
            solvable = true;
            last = del;
        }
        if (isSolvable()) {
            Stack<Board> sol = new Stack<>();
            while (last.prev != null) {
                sol.push(last.board);
                last = last.prev;
            }
            sol.push(last.board);
            solution = new ArrayList<>();
            while (!sol.isEmpty()) {
                solution.add(sol.pop());
            }
        }
        deleted = null;
        twin_deleted = null;
        pQueue = null;
        twin_pQueue = null;
        return last;
    }

    public boolean isSolvable() {
        return solvable;
    }            // is the initial board solvable?

    public int moves() {
        if (isSolvable()) {
            return solution.size() - 1;
        } else {
            return -1;
        }

    }                     // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        return solution;
    }      // sequence of boards in a shortest solution; null if unsolvable

    public static void main(String[] args) {

    } // solve a slider puzzle (given below)
}
