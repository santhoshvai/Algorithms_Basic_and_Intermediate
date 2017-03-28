import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by santhoshvai on 28/03/17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q; // queue elements
    private int n; // number of elements on queue
    private int first; // index of first element of queue
    private int last; // index of available empty slot

    // construct an empty randomized queue
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }

    // resize the underlying array
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last  = n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        if (n == q.length) resize(2*q.length);   // double size of array if necessary
        q[last++] = item;                        // add item
        if (last == q.length) last = 0;          // wrap-around
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int index = (first + StdRandom.uniform(n)) % q.length;
        Item item = q[index];
        q[index] = q[first];
        q[first] = null;                            // to avoid loitering
        n--;
        first++;
        if (first == q.length) first = 0;           // wrap-around
        // shrink size of array if necessary
        if (n > 0 && n == q.length/4) resize(q.length/2);
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = (first + StdRandom.uniform(n)) % q.length;
        return q[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        private int[] order;
        private int index;

        public RandomIterator() {
            order = new int[n]; // randomize the order of array Item array using a int array
            for (int i = 0; i < n; ++i) {
                order[i] = (first + i) % q.length;
            }
            StdRandom.shuffle(order);
            index = 0;
        }

        public boolean hasNext() {
            return index < order.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = q[order[index++]];
            return item;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("prakash1");
        rq.enqueue("yoga1");
        rq.enqueue("santhosh1");
        rq.enqueue("yoga2");
        rq.enqueue("santhosh2");
        rq.enqueue("prakash2");
        StdOut.println("Items in Queue\n-------------");
        for ( String s : rq){
            StdOut.println(s);
        }
        StdOut.println("Random Sample : \t" + rq.sample());
    }
}