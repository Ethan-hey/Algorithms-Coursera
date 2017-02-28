import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by THINKPAD on 1/28/2017.
 */
public class PointSET {

    private TreeSet<Point2D> tSet;

    // construct an empty set of points
    public PointSET() {
        tSet = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return tSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return tSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        tSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return tSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : tSet) {
            p.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (tSet.isEmpty()) {
            return null;
        }
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }
        ArrayList<Point2D> range = new ArrayList<Point2D>();
        for (Point2D p : tSet) {
            if (p.x() > rect.xmin() && p.x() < rect.xmax() && p.y() > rect.ymin() && p.y() < rect.ymax()) {
                range.add(p);
            }
        }
        return range;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Point2D nearest;
        if (tSet.isEmpty()) {
            return null;
        }
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        nearest = tSet.first();
        double dist = distance(p, tSet.first());
        for (Point2D cur : tSet) {
            double tempDist = distance(p, cur);
            if (tempDist < dist) {
                nearest = cur;
                dist = tempDist;
            }
        }
        return nearest;
    }

    private double distance(Point2D p1, Point2D p2) {
        double disX = p1.x() - p2.x();
        double disY = p2.y() - p2.y();
        return Math.pow(Math.pow(disX, 2) + Math.pow(disY, 2), 0.5);
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
