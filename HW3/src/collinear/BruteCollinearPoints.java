import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

/**
 * Created by THINKPAD on 1/21/2017.
 */
public class BruteCollinearPoints {

    private List<LineSegment> lslist = new ArrayList<LineSegment>();
    private LineSegment[] ls;
    private Point[] points;
    private LinkedList<Point[]> segmentpoints = new LinkedList<Point[]>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException();
        }
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException();
            }
            this.points[i] = points[i];
        }
        Arrays.sort(this.points);
        checkdup();
        for (int i = 0; i < this.points.length - 3; i++) {
            for(int j = i + 1; j < this.points.length - 2; j++) {
                for (int k = j + 1; k < this.points.length - 1; k++) {
                    for (int l = k + 1; l < this.points.length; l++) {
                        if (this.points[i].slopeTo(this.points[j]) == this.points[j].slopeTo(this.points[k])
                                && this.points[j].slopeTo(this.points[k]) == this.points[k].slopeTo(this.points[l])) {
                            Point p1 = this.points[i];
                            Point p2 = this.points[l];
                            if (validsegment(p1, p2)) {
                                lslist.add(new LineSegment(p1, p2));
                                Point[] pointpair = new Point[2];
                                pointpair[0] = p1;
                                pointpair[1] = p2;
                                segmentpoints.add(pointpair);
                            }
                        }
                    }
                }
            }
        }
        ls = new LineSegment[lslist.size()];
        ls = lslist.toArray(new LineSegment[lslist.size()]);

    }    // finds all line segments containing 4 points

    private void checkdup() {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].equals(points[i + 1])) {
                throw new java.lang.IllegalArgumentException();
            }
        }
    }

    private boolean validsegment(Point p1, Point p2) {
        for (int i = 0; i < segmentpoints.size(); i++) {
            Point orip1 = segmentpoints.get(i)[0];
            Point orip2 = segmentpoints.get(i)[1];
            if ((segmentpoints.get(i)[0].equals(p1) && segmentpoints.get(i)[1].equals(p2))
                    || (segmentpoints.get(i)[0].equals(p2) && segmentpoints.get(i)[1].equals(p1))) {
                return false;
            }
        }
        return true;
    }

    public int numberOfSegments() {
        return ls.length;
    }        // the number of line segments

    public LineSegment[] segments() {
        return ls;
    }                // the line segments

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
