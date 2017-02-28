import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;


/**
 * Created by THINKPAD on 1/22/2017.
 */
public class FastCollinearPoints {

    private List<LineSegment> lslist = new ArrayList<LineSegment>();
    // List to store all the linesegments, which would be converted to an array later
    private LineSegment[] ls;
    // an array to store all the linesegemetns;
    private ArrayList<Point[]> startendpoints = new ArrayList<Point[]>();
    // an array to all the start and end points of a segment

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException();
            }
            Point oriP = points[i];
            Point[] pointscopy = new Point[points.length];
            System.arraycopy(points, 0, pointscopy, 0, points.length);
            Arrays.sort(pointscopy);
            checkdup(pointscopy);
            Arrays.sort(pointscopy, points[i].slopeOrder());
//            for (Point p : pointscopy) {
//                p.slope = p.slopeTo(oriP);
//            }

            for (int j = 0; j < pointscopy.length - 2; j++) {
                LinkedList<Point> sgpoints = new LinkedList<>();
                // to store all the points that might on the same line
                sgpoints.add(oriP);

                // check if the following three points on same the line with oriP
                Point curP = pointscopy[j];
                Point nexP = pointscopy[j + 1];
                Point nexnexP = pointscopy[j + 2];
                if (oriP.slopeTo(curP) == oriP.slopeTo(nexP)
                        && oriP.slopeTo(nexP) == oriP.slopeTo(nexnexP)) {
                    // add points that are on the same line
                    for (int k = j; k < pointscopy.length; k++) {
                        if (oriP.slopeTo(curP) == oriP.slopeTo(pointscopy[k])) {
                            sgpoints.add(pointscopy[k]);
                        }
                    }
                }
//                  (1000, 18000) -> (4000, 30000)

//                (28000, 13500) -> (3000, 26000)
//                (1000, 18000) -> (3500, 28000)
//                (1000, 18000) -> (4000, 30000)
//                (2000, 22000) -> (4000, 30000)
                if (sgpoints.size() >= 4) {
                    Point[] temp;
                    temp = sgpoints.toArray(new Point[sgpoints.size()]);
                    Arrays.sort(temp);
                    Point p1 = temp[0];
                    Point p2 = temp[temp.length - 1];
                    addsegment(p1, p2);
                    break;
                }
            }
        }

        for (Point[] ps : startendpoints) {
            lslist.add(new LineSegment(ps[0], ps[1]));
        }
        ls = new LineSegment[lslist.size()];
        ls = lslist.toArray(new LineSegment[lslist.size()]);
    }     // finds all line segments containing 4 or more points

    private void checkdup(Point[] points) {
        for(int i = 0; i < points.length - 1; i++) {
            if (points[i].equals(points[i + 1])) {
                throw new java.lang.IllegalArgumentException();
            }
        }
    }

    private void addsegment(Point p1, Point p2) {
        Point[] ps = new Point[2];
        ps[0] = p1;
        ps[1] = p2;
        Arrays.sort(ps);
        boolean existed = false;
        //p1 < p2 here
        // if not exist: add it
        // if exist but the original is a sub-segment: add it

        for (int i = 0; i < startendpoints.size(); i++) {
            Point orip1 = startendpoints.get(i)[0];
            Point orip2 = startendpoints.get(i)[1];
            if (p1.equals(orip1) && p2.equals(orip2)) {
                if (orip1.compareTo(p1) <= 0 && orip2.compareTo(p2) >= 0) {
                    // remove the original one and ready to add the new segment
                    startendpoints.remove(i);
                } else {
                    existed = true;
                    break;
                }
            }
        }
        if (!existed) {
            startendpoints.add(ps);
        }
    }

    public int numberOfSegments() {
        if (ls == null) {
            return 0;
        }
        return ls.length;
    }        // the number of line segments

    public LineSegment[] segments() {
        if (ls == null) {
            return null;
        }
        return ls;
    }                // the line segments

    public static void main(String[] args) {

    }
}
