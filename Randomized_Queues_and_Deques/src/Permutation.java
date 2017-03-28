import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by santhoshvai on 28/03/17.
 */
public class Permutation {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();
        final String[] strings = StdIn.readAllStrings();
        for (String str : strings) {
            q.enqueue(str);
        }

        for (int i = 0; i < n; i++) {
            StdOut.println(q.dequeue());
        }
    }
}
