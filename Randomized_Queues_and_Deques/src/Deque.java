import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by santhoshvai on 28/03/17.
 */
public class Deque<Item> implements Iterable<Item> {
    private int n;         // number of elements on queue
    private Node first;    // beginning of queue
    private Node last;     // end of queue

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // construct an empty deque
    public Deque() {
        n = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkNull(item);
        Node oldFirst = first;
        first = new Node();
        if (oldFirst != null) oldFirst.prev = first;
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (last == null) last = first;
        n++;
    }

    // add the item to the end
    public void addLast(Item item) {
        checkNull(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        if (first.next != null) first.next.prev = null;
        first = first.next;
        n--;
        if (isEmpty()) last = first;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        if (last.prev != null) last.prev.next = null;
        last = last.prev;
        n--;
        if (last == null) first = null;
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private void checkNull(Item item) {
        if (item == null) throw new NullPointerException();
    }

    public static void main(String[] args) {
        Deque<String> deq = new Deque<String>();
        deq.addFirst("prakash");
        deq.addFirst("yoga");
        deq.addLast("santhosh");
        for (String s : deq){
            System.out.println(s);
        }
        System.out.println("-------------\nRemoving Last\n------------- ::: " + deq.removeLast());
        for (String s : deq){
            StdOut.println(s);
        }
    }
}