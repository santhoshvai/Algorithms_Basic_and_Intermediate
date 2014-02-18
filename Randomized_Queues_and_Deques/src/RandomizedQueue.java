/*************************************************************************
 * Name: Santhosh Vaiyapuri
 * Email: santhoshvai@gmail.com
 *
 * Compilation:  javac RandomisedQueue.java
 * Execution:
 * Dependencies: java.util.Iterator
 *
 * Description:   A randomized queue is similar to a stack or queue, except 
 * that the item removed is chosen uniformly at random from items in the 
 * data structure. This Java class creates a generic data type RandomizedQueue
 *
 *************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] q; // queue elements
	private int N = 0; // number of elements on queue
	private int first = 0; // index of first element of queue
	private int last = 0; // index of available empty slot

	
    /**
     *construct an empty RandomizedQueue
     */
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		q = (Item[]) new Object[2]; // cast needed as no generic array creation in Java (see slides)
	}
	 /**
     * is the RandomizedQueue empty?
     * @return <tt>true</tt> if the queue is empty
     */
	public boolean isEmpty() {
		return N == 0;
	}
    /**
     * Get the number of items on the RandomizedQueue
     * @return <tt>Total No</tt> of items on the deque
     */
	public int size() {
		return N;
	}

	
	@SuppressWarnings("unchecked")
	private void resize(int sz) { // resize the array // PRIVATE
		Item[] temp = (Item[]) new Object[sz];
		for (int i = 0; i < N; i++) {
			temp[i] = q[(first + i) % q.length]; //recopy to front of array
		}
		q = temp;
		first = 0; // set index of first element of queue to 0
		last = N; // set index of last+1 element of queue (empty slot)
	}

    /**
     * add the item to queue
     */
	public void enqueue(Item item) {
		if (item == null)
			throw new java.lang.NullPointerException();
		// double size of array if necessary and recopy to front of array
		if (N == q.length)
			resize(2 * q.length); // double size of array when queue is full
		q[last++] = item; // add item
		if (last == q.length) last = 0; // avoid index array out of bounds
		N++;
	}

    /**
     * delete and return a random item
     */
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException();
		int index = (first + StdRandom.uniform(N)) % q.length;
		Item result = q[index];
		q[index] = q[first];
		q[first] = null; // to avoid loitering
		N--;
		first++;
		if (first == q.length)	first = 0; // to avoid bugs
		// shrink size of array if necessary
		if (N > 0 && N == q.length / 4) // Halve the size of array when queue is quarter-full
			resize(q.length / 2);
		return result;
	}
    /**
     * return (but do not delete) a random item
     */
	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException();
		int index = (first + StdRandom.uniform(N)) % q.length;
		Item result = q[index];
		return result;
	}

	public Iterator<Item> iterator() {
		return new RandomIterator();
	}

	private class RandomIterator implements Iterator<Item> {

		private int[] order;
		private int index;

		public RandomIterator() {
			order = new int[N]; //randomize the order of array Item array using a int array
			for (int i = 0; i < N; ++i) {
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

	/**
	 * Unit test client.
	 */
	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		rq.enqueue("prakash1");
		rq.enqueue("yoga1");
		rq.enqueue("santhosh1");
		rq.enqueue("yoga2");
		rq.enqueue("santhosh2");
		rq.enqueue("prakash2");
		StdOut.println("Items in Queue\n-------------");
		for(String s : rq){
			StdOut.println(s);
		}
		StdOut.println("Random Sample : \t" + rq.sample());
	}
}