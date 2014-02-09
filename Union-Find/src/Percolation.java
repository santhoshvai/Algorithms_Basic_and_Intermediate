/****************************************************************************
 *  Compilation:  
 *  Execution:  
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Percolation DataStructure
 *
 ****************************************************************************/
/**
 *  The <tt>Percolation</tt> class represents a Percolation data structure.
 *  It supports the <em>open</em> operation, along with
 *  methods for determining whether a site is full, whether a site is open
 *  and if total grid percolates.
 *  <p>
 *  This implementation uses weighted quick union.
 *  <p>
 *     
 *  @author Santhosh Vaiyapuri
 */
public class Percolation {
    private WeightedQuickUnionUF cell; //contains the id number
    private WeightedQuickUnionUF cell_backwash; /*to solve backwash problem.
    (when percolation occurs, all components that contain an open 
    cell in the bottom row are declared “full”, because they are 
    connected through the dummy bottom row to an actual full cell in the bottom row)
    so this cell_backwash has no dummy row bottom row at all*/
    private boolean [] openState; //false-closed, true-open
    private int totalN;
    /**
     * Initializes an empty Percolation data structure of N-by-N grid, with all sites blocked.
     * @throws java.lang.IllegalArgumentException if N < 0
     * @param N the number of columns/rows
     */
	public Percolation(int N) {
	// create N-by-N grid, with all sites blocked
		this.totalN = N;
	    this.cell = new WeightedQuickUnionUF(N*N+2); //+2 are for additional top and bottom cells
	    this.cell_backwash = new WeightedQuickUnionUF(N*N+1);
	    this.openState = new boolean[N*N];
	    for (int i = 0; i < (N*N); i++) {
	    	this.openState[i] = false;
	    }
	}
	public void open(int row, int column) { // open site (row i, column j) if it is not already
		int indexI = row - 1;
		int indexJ = column - 1;
		if (isOpen(row, column)) return;
		int offset = indexI*totalN + indexJ; 
		int cellOffset = offset; //cellOffset is same as offset now, as N*N stores the top row
		this.openState[offset] = true; //make it open
		/*
		 * Below you have to connect it with top/bottom cell
		 * and bottom site if your site is on top/bottom row
		 */
		if (indexI == 0) //if it is top row
		{
			cell.union((totalN * totalN),  cellOffset);	
			cell_backwash.union((totalN * totalN),  cellOffset);	
		}
		if (indexI == totalN - 1) //if it is bottom row
		{
			cell.union((totalN * totalN) + 1, cellOffset);
			//cell_backwash.union((totalN * totalN)+1,  cellOffset); //since there is no dummy bottom row			
		}
		/*
		 * Below you have to connect it with left/right/up/down cell
		 * so find corresponding i and j indices and connect
		 */
		// union adjacent open sites
		int leftIndexI = indexI; //same row
		int leftIndexJ = indexJ - 1; //1 column lesser
		if (leftIndexJ >= 0)
		{
		
		  if (isOpen(leftIndexI + 1, leftIndexJ + 1)) //check if it is open
		  {
			int cellOffsetLeft = (leftIndexI * totalN + leftIndexJ);
			cell.union(cellOffsetLeft, cellOffset);
			cell_backwash.union(cellOffsetLeft, cellOffset);
		  }
		}
		int rightIndexI = indexI; //same row
		int rightIndexJ = indexJ + 1; //1 column greater
		if (rightIndexJ < totalN)
		{
		  if (isOpen(rightIndexI + 1, rightIndexJ + 1)) //check if it is open
		  {
			int cellOffsetRight = (rightIndexI * totalN + rightIndexJ);
			cell.union(cellOffsetRight, cellOffset);
			cell_backwash.union(cellOffsetRight, cellOffset);
		  }
		}
		int upIndexI = indexI - 1; //1 row less
		int upIndexJ = indexJ; //same column 
		if (upIndexI >= 0)
		{
		  if (isOpen(upIndexI + 1, upIndexJ + 1)) //check if it is open
		  {
			int cellOffsetUp = (upIndexI * totalN + upIndexJ);
			cell.union(cellOffsetUp, cellOffset);
			cell_backwash.union(cellOffsetUp, cellOffset);
		  }
		}
		int downIndexI = indexI + 1; //1 row greater
		int downIndexJ = indexJ; //same column 
		if (downIndexI < totalN)
		{
		  if (isOpen(downIndexI + 1, downIndexJ + 1)) //check if it is open
		  {
			int cellOffsetDown = (downIndexI * totalN + downIndexJ);
			cell.union(cellOffsetDown, cellOffset);	
			cell_backwash.union(cellOffsetDown, cellOffset);
		  }
		}
	}
	 /**
     * Returns boolean stating whether the site is open
     * @return is site (row i, column j) open??
     * @param row the grid row number
     * @param column the grid column number
     */	
	public boolean isOpen(int row, int column) {
		indexCheck(row, column);
		int indexI = row - 1;
		int indexJ = column - 1;		
	    int offset = indexI*totalN + indexJ; //row-major
	    return openState[offset];
	}   
	/**
     * Check if the row and column number is in the grid
     * @throws java.lang.IndexOutOfBoundsException
     * @param i the grid row number
     * @param j the grid column number
     */
	private void indexCheck(int i, int j)
	{
	    if (i < 1 || j < 1 || i > totalN || j > totalN)
	    	throw new java.lang.IndexOutOfBoundsException();
	}
    /**
     * Is the sites <tt>(row i, column j)</tt> full??
     * @param row the integer representing the grid row number
     * @param column the integer representing the grid column number
     * @return <tt>true</tt> if the site <tt>(row i, column j)</tt>
     *   is full, and <tt>false</tt> otherwise
     * @throws java.lang.IndexOutOfBoundsException 
     * if the specified row and column number is out of bounds
     */
	public boolean isFull(int row, int column) {
		indexCheck(row, column);
		int indexI = row - 1;
		int indexJ = column - 1;
		int offset = indexI * totalN + indexJ; 
		int cellIndex = offset;

		boolean isfull = cell_backwash.connected((totalN * totalN), cellIndex);
		return (isOpen(row, column) && isfull);
	}
	/**
     * Does the  <tt>system</tt> percolate?
     * @return <tt>true</tt> if the <tt>system</tt> percolates
     *   , and <tt>false</tt> otherwise
     */
	public boolean percolates(){ // does the system percolate?
		//if there is only one site, just return if it is open or not
		if (totalN == 1) return (openState[0]); 
		//just check if the top and bottom cell are connected
		return cell.connected((totalN * totalN) ,  totalN * totalN + 1); 		
	}

}
