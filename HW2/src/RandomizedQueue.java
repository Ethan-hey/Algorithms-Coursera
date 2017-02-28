import java.util.Iterator;
import java.util.Queue;

/**
 * Created by THINKPAD on 1/17/2017.
 */
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int startsize = 2;
    private int size;
    private int factor = 2;
    private Item[] queue;

    public RandomizedQueue() {
        queue = (Item[]) new Object[startsize];
        size = 0;
    }                 // construct an empty randomized queue

    public boolean isEmpty() {
        return size == 0;
    }                 // is the queue empty?

    public int size() {
        return size;
    }                        // return the number of items on the queue

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (size == queue.length) {
            resize(factor * queue.length);
        }
        queue[size] = item;
        size += 1;
    }           // add the item

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    public Item dequeue() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        int loc = StdRandom.uniform(size);
        Item item = queue[loc];
        queue[loc] = queue[size - 1];
        queue[size - 1] = null;
        size -= 1;
        if (size > startsize && size <= 0.25 * queue.length) {
            resize(queue.length / factor);
        }
        return item;
    }                    // remove and return a random item

    public Item sample() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        int loc = StdRandom.uniform(size);
        Item item = queue[loc];
        return item;
    }                     // return (but do not remove) a random item

    public Iterator<Item> iterator() {
        return new RanQueueIterator();
    }         // return an independent iterator over items in random order

    private class RanQueueIterator implements Iterator<Item> {

        int[] locs = new int[size];
        int picked;

        private RanQueueIterator() {
            for (int i = 0; i < size; i++) {
                locs[i] = i;
            }
            StdRandom.shuffle(locs);
            int picked = 0;
        }


        public boolean hasNext() {
            return picked < size;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = queue[locs[picked]];
            picked += 1;
            return item;
        }
    }

    public static void main(String[] args) {
//        RandomizedQueue<Integer> rq = new RandomizedQueue();
//        rq.enqueue(1);
//        rq.enqueue(2);
//        rq.enqueue(3);
//        rq.enqueue(4);
//        rq.enqueue(5);
//        Iterator<Integer> r = rq.iterator();
//        int a = r.next();
//        int b = r.next();
//        int c = r.next();
//        int d = r.next();
//        int e = r.next();
//        int f = r.next();
    }
}
