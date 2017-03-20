package dataStructures;

public class QuickFindUF { //Eager aaproach

	private int[] id;  
	//private integer array, thats our id array .. 
	//id contains the number indicating a connected component
	public QuickFindUF (int N){
		// set id of each object to itself (N array accesses)
		id = new int[N];
		for (int i=0;i<N;i++)
			id[i] = i;

	}
	public boolean connected (int p,int q){ //this is the quick find algorithm
		// check if p and q are in same component (2 array accesses)
		return id[p]==id[q];
	}
	public void union (int p,int q){ //this is the quick find algorithm
		// change all entries with id[p] to id[q] (at-most 2N+2 array accesses)
		int pid = id[p]; //1 array access
		int qid = id[q]; //1 array access
		for (int i=0;i<id.length;i++)
			if (id[i] == pid) id[i]=qid; //at-most N array access + at-most N array access
	}
}

/* No of array accesses: | Init:N | union(N~2N) | find(1) |
 * o(N^2) --> very slow  ==> quadratic
 * They dont scale ! as computer get bigger, these algs actually get slower  
 */
