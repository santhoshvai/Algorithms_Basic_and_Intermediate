package dataStructures;

public class QuickUnion { //Lazy approach

	private int[] id; 
	//id[i] is the parent of i
	//root of i is id[id[...id[i]...]] // Keep going until it doesnt change
	// root is the representative of its connected component
	public QuickUnion (int N){
		// set id of each object to itself (N array accesses)
		id = new int[N];
		for (int i=0;i<N;i++)
			id[i] = i;

	}
	private int root (int i){ 
		//chase parent pointers until each root (depth of i array accesses)
		while(i != id[i]) //till its parent is its id
			i=id[i];
		return i; //top of the tree id
	}
	public boolean connected (int p,int q){ 
		//(depth of p and q array accesses)
		return root(p)==root(q);
	}
	public void union (int p,int q){ //this is the quick union algorithm
		// set the  p's root to q's root (depth of p and q array accesses)
		int i = root(p); //depth of p  array access
		int j = root(q); //depth of q  array access
		id[i] = j;
	}
}

/* No of array accesses: | Init:N | union(N) | find(N) | 
 * Trees can get tall 
 * Find can get too expensive (could be N array accesses)
 * linear
 */
