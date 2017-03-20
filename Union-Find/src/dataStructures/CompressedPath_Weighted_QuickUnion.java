package dataStructures;

public class CompressedPath_Weighted_QuickUnion {
	/* Just after computing the root of p,
	 * set the id of each examined node to point that root
	 * thats gonna be a constant extra cost.
	 */
	private int[] id; 
	private int[] sz; //extra array to count the number of objects rooted at i
	//id[i] is the parent of i
	//root of i is id[id[...id[i]...]] // Keep going until it doesnt change
	// root is the representative of its connected component
	public CompressedPath_Weighted_QuickUnion (int N){
		// set id of each object to itself (N array accesses)
		id = new int[N];
		sz = new int[N];
		for (int i=0;i<N;i++){
			id[i] = i;
			sz[i] = 1;
		}

	}
	private int root (int i){ 
		//chase parent pointers until each root (depth of i array accesses)
		while(i != id[i]){ //till its parent is its id
			id[i] = id[id[i]]; //make  every node in its path point to its grand parent
			i=id[i];
		}
		return i; //top of the tree id
	}
	public boolean connected (int p,int q){ //same as quick union
		//(depth of p and q array accesses)
		return root(p)==root(q);
	}
	public void union (int p,int q){ //this is the weighted quick union algorithm
		// link root of smaller tree to the larger tree
		int i = root(p); //depth of p  array access
		int j = root(q); //depth of q  array access
		if (i==j) return;
		if (sz[i]<sz[j]) //update the size of the array
		{
			id[i] = j;
			sz[j] += sz[i];
		}
		else
		{
			id[j] = i;
			sz[i] += sz[j];
		}
		
	}
}

/* Analysing mathematically, 
 * Running Time,
 * 		Find : takes time proportional to depth of p and q
 * 		Union : takes constant take time given the roots
 *  
 * Proposition,
 *  	Depth of any node x is at most lg N (log base 2)
 *  
 *  No of array accesses: | Init:N | union(lg N) | find(lg N) | 
 */
