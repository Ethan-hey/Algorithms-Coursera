import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
/**
 * Created by THINKPAD on 1/17/2017.
 */
public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Node first = null;
        Item item;
        Node last = null;
    }

    private int size;
    private Node firstP;
    private Node lastP;

    public Deque() {
        size = 0;
        firstP = null;
        lastP = null;
    }                           // construct an empty deque

    public boolean isEmpty() {
        return size == 0;
    }                 // is the deque empty?

    public int size() {
        return size;
    }                        // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        } else {
            Node newnode = new Node();
            newnode.item = item;
            if (firstP == null) {
                firstP = newnode;
                lastP = newnode;
            } else {
                firstP.first = newnode;
                firstP.first.last = firstP;
                firstP = firstP.first;
            }
            size += 1;
        }


    }          // add the item to the front

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        } else {
            Node newnode = new Node();
            newnode.item = item;
            if (lastP == null) {
                firstP = newnode;
                lastP = newnode;
            } else {
                lastP.last = newnode;
                lastP.last.first = lastP;
                lastP = lastP.last;
            }
            size += 1;
        }
    }           // add the item to the end

    public Item removeFirst() {
        Item item;
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        } else if (size == 1) {
            item = firstP.item;
            firstP.item = null;
            firstP = lastP = null;
        } else {
            item = firstP.item;
            firstP.last.first = null;
            Node firstPtemp = firstP;
            firstP = firstP.last;
            firstPtemp.item = null;
            firstPtemp.last = firstPtemp = null;
        }
        size -= 1;
        return item;
    }                // remove and return the item from the front

    public Item removeLast() {
        Item item;
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        } else if (size == 1) {
            item = lastP.item;
            lastP.item = null;
            lastP = firstP = null;
        } else {
            item = lastP.item;
            lastP.first.last = null;
            Node lastPtemp = lastP;
            lastP = lastP.first;
            lastPtemp.item = null;
            lastPtemp.first = lastPtemp = null;
        }
        size -= 1;
        return item;
    }                 // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }         // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item> {
        private Node current = firstP;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            } else {
                Item item = current.item;
                current = current.last;
                return item;
            }
        }
    }

    public static void main(String[] args) {
//        Deque<String> d = new Deque<>();
//        Deque<String> d2 = new Deque<>();
//
//        d.addFirst("a");
//        StdOut.println(d.size());
//        d.removeFirst();
//        StdOut.println(d.size());
//        d2.addFirst("a");
//        StdOut.println(d2.size());
//        d2.removeFirst();
//        StdOut.println(d2.size());
//        d.addFirst("a");
//        StdOut.println(d.size());
//        d2.addFirst("a");
//        StdOut.println(d2.size());

    }
}
