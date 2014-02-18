/*************************************************************************
 * Name: Santhosh Vaiyapuri
 * Email: santhoshvai@gmail.com
 *
 * Compilation:  
 * Execution:	echo A B C D E F G H I | java Subset 3 
 * Dependencies: 
 *
 * Description:    a client program Subset.java that takes
 *  a command-line integer k; reads in a sequence of N strings
 *  from standard input using StdIn.readString(); and prints out
 *  exactly k of them, uniformly at random. Each item from the sequence
 *  can be printed out at most once. You may assume that k is GEQ 0
 *  and no greater than the number of string N on standard input.
 *************************************************************************/
public class Subset {
    private static int total(final String[] args) {
        try {
            return Integer.parseInt(args[0]);
        } catch (final NumberFormatException err) {
            throw new IllegalArgumentException(err);
        }
    }
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        int N = total(args);
        final String[] strings = StdIn.readAllStrings();
        for (String str : strings) {
            q.enqueue(str);
        }
        for (int i = 0; i < N; i++) {
            StdOut.println(q.dequeue());
        }
    }
}