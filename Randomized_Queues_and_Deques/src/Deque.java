/*************************************************************************
 * Name: Santhosh Vaiyapuri
 * Email: santhoshvai@gmail.com
 *
 * Compilation:  javac Deque.java
 * Execution:
 * Dependencies: java.util.Iterator
 *
 * Description:  A double-ended queue or deque (pronounced "deck") is a 
 * generalization of a stack and a queue that supports inserting and 
 * removing items from either the front or the back of the data structure. 
 * This file creates a generic data type Deque.
 *
 *************************************************************************/

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node<Item> first;
    private Node<Item> last;
    
	private class Node<Item> {      
        public Node<Item> previous;
        public Node<Item> next;
        public Item item;
        
        public Node(Node<Item> previous, Node<Item> next, Item item) {
            this.previous = previous;
            this.next = next;
            this.item = item;
        }
    }
    //your iterator implementation must support the operations next() and hasNext()
	//in constant worst-case time and use a constant amount of extra space per iterator.
    private class DequeIterator implements Iterator<Item> {       
        private Node<Item> current = first;
           public boolean hasNext() {
            return current != null;
        }        
        public void remove() {
        	//throw an UnsupportedOperationException if the client calls the remove() method in the iterator; 
            throw new java.lang.UnsupportedOperationException();
        }
        
        public Item next() {
            
            if (!hasNext())
            	//throw a java.util.NoSuchElementException if the client 
                //attempts to remove an item from an empty deque;
                throw new java.util.NoSuchElementException();                           
            Item item = current.item;           
            current = current.next;          
            return item;
        }
    }
    /**
     *construct an empty deque
     */
    public Deque() {
        
        size  = 0;
        first = null;
        last  = null;
    }
    
    /**
     * is the deque empty?
     * @return <tt>true</tt> if the queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Get the number of items on the deque
     * @return <tt>Total No</tt> of items on the deque
     */
    public int size() {
        return size;
    }
    /**
     * insert the item at the front
     * @param item the item to be inserted
     */
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();        
        Node<Item> node = new Node<Item>(null, first, item);        
        if (!isEmpty())
            first.previous = node;           
        first = node;       
        if (last == null) last = first;       
        size++;
    }
    
    /**
     * insert the item at the end
     * @param item the item to be inserted
     */
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();            
        Node<Item> node = new Node<Item>(last, null, item);      
        if (last != null)
            last.next = node;           
        last = node;
        
        if (isEmpty()) first = last;
        
        size++;
    }
    
    /**
     * delete and return the item at the front
     * @return item the item that is deleted
     */
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();      
        Item item = first.item;       
        if (first.next != null) first.next.previous = null;
        if (last == first) last = null;
        first = first.next;        
        size--;       
        return item;
    }
    
    /**
     * delete and return the item at the end
     * @return item the item that is deleted
     */
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();      
        Item item = last.item;        
        if (last.previous != null) last.previous.next = null;
        if (last == first) first = last = null;
        if (last != null) last = last.previous;       
        size--;        
        return item;
    } 
   /**
    * return an iterator over items in order from front to end
    */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    public static void main(String[] args) {
    	Deque<String> deq = new Deque<String>();
		deq.addFirst("prakash");
		deq.addFirst("yoga");
		deq.addLast("santhosh");
		for(String s : deq){
			System.out.println(s);
		}
		System.out.println("-------------\nRemoving Last\n-------------");
		deq.removeLast();
		for(String s : deq){
			StdOut.println(s);
		}
    }
}