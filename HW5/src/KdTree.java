import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.*;

/**
 * Created by THINKPAD on 1/28/2017.
 */
public class KdTree {
    private TreeSet<Node> tree = new TreeSet<Node>();
    private Node root;
    private int size;


    private class Node {
        boolean isOdd;
        Point2D point;
        Node left;
        Node right;

        public Node(Point2D point, boolean isOdd) {
            this.point = point;
            this.isOdd = isOdd;
            this.left = null;
            this.right = null;
        }
    }

    // construct an empty set of points
    public KdTree() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (this.isEmpty()) {
            Node n = new Node(p, true);
            root = n;
            size += 1;
        } else if (this.contains(p)) {
            return;
        } else {
            insertPoint(root, p);
            size += 1;
        }
    }

    private void insertPoint(Node root, Point2D p) {
        double[] values = calvalues(root, p);

        double rootVal = values[0];
        double pointVal = values[1];

        if (rootVal > pointVal) {
            if (root.left == null) {
                Node n = new Node(p, !root.isOdd);
                root.left = n;
            } else {
                insertPoint(root.left, p);
            }
        } else {
            if (root.right == null) {
                Node n = new Node(p, !root.isOdd);
                root.right = n;
            } else {
                insertPoint(root.right, p);
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (this.isEmpty()) {
            return false;
        }
        return findcontatins(root, p);
    }

    private boolean findcontatins(Node root, Point2D p) {
        double[] values = calvalues(root, p);
        double rootVal = values[0];
        double pointVal = values[1];

        if (root.point.equals(p)) {
            return true;
        } else if (rootVal > pointVal) {
            if (root.left == null) {
                return false;
            } else {
                return findcontatins(root.left, p);
            }
        } else {
            if (root.right == null) {
                return false;
            } else {
                return findcontatins(root.right, p);
            }
        }
    }

    private double[] calvalues(Node root, Point2D p) {
        double rootVal;
        double pointVal;
        if (root.isOdd) {
            rootVal = root.point.x();
            pointVal = p.x();
        } else {
            rootVal = root.point.y();
            pointVal = p.y();
        }
        double[] ret = {rootVal, pointVal};
        return ret;
    }

    // draw all points to standard draw
    public void draw() {
        if (!isEmpty()) {
            drawPoint(root);
        }
    }

    private void drawPoint(Node root) {
        root.point.draw();
        if (!(root.left == null)) {
            drawPoint(root.left);
        }
        if (!(root.right == null)) {
            drawPoint(root.right);
        }
    }

//     all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> inRange = new ArrayList<Point2D>();
        if (isEmpty()) {
            return inRange;
        } else {
            calrange(rect, root, inRange, 0, 0, 1, 1);
            return inRange;
        }
    }

    private void calrange(RectHV rect, Node root, ArrayList<Point2D> inRange, double minX, double minY, double maxX, double maxY) {
        if (rect.contains(root.point)) {
            inRange.add(root.point);
        }
        RectHV leftRec;
        RectHV rightRec;
        if (root.isOdd) {
            leftRec = new RectHV(minX, minY, root.point.x(), maxY);
            rightRec = new RectHV(root.point.x(), minY, maxX, maxY);

        } else {
            leftRec = new RectHV(minX, minY, maxX, root.point.y());
            rightRec = new RectHV(minX, root.point.y(), maxX, maxY);
        }
        if (root.left != null && rect.intersects(leftRec)) {
            if (root.isOdd) {
                calrange(rect, root.left, inRange, minX, minY, root.point.x(), maxY);
            } else {
                calrange(rect, root.left, inRange, minX, minY, maxX, root.point.y());
            }
        }
        if (root.right != null && rect.intersects(rightRec)) {
            if (root.isOdd) {
                calrange(rect, root.right, inRange, root.point.x(), minY, maxX, maxY);
            } else {
                calrange(rect, root.right, inRange, minX, root.point.y(), maxX, maxY);
            }
        }
    }


//     a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

        if (isEmpty()) {
            return null;
        } else {
            nearest = root.point;
            shortest = p.distanceSquaredTo(root.point);
            calnearest(p, root, 0, 0, 1, 1);
        }
        return nearest;
    }

    private Point2D nearest;
    private double shortest;

    private void calnearest(Point2D p, Node root, double minX, double minY, double maxX, double maxY) {
        if (p.distanceSquaredTo(root.point) < shortest) {
            nearest = root.point;
            shortest = p.distanceSquaredTo(root.point);
        }
        RectHV partRec;
        if (root.left != null) {
            if (root.isOdd) {
                partRec = new RectHV(minX, minY, root.point.x(), maxY);
            } else {
                partRec = new RectHV(minX, minY, maxX, root.point.y());
            }
            if (partRec.distanceSquaredTo(p) < shortest) {
                calnearest(p, root.left, partRec.xmin(), partRec.ymin(), partRec.xmax(), partRec.ymax());
            }
        }
        if (root.right != null) {
            if (root.isOdd) {
                partRec = new RectHV(root.point.x(), minY, maxX, maxY);
            } else {
                partRec = new RectHV(minX, root.point.y(), maxX, maxY);
            }
            if (partRec.distanceSquaredTo(p) < shortest) {
                calnearest(p, root.right, partRec.xmin(), partRec.ymin(), partRec.xmax(), partRec.ymax());
            }
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
//        KdTree t = new KdTree();
//        Point2D a = new Point2D(0.7, 0.1);
//        Point2D b = new Point2D(0.7, 0.2);
////        Point2D c = new Point2D(0.7, 0.3);
////        Point2D d = new Point2D(0.7, 0.4);
//        Point2D e = new Point2D(0.7, 0.38);
//        t.insert(a);
//        t.insert(b);
//        t.insert(a);
////        t.insert(c);
////        t.insert(d);
//        Point2D k = t.nearest(e);
////        t.insert(e);

    }
}
