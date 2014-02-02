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
	    this.openState = new boolean[N*N];
	    for (int i = 0; i < (N*N); i++) 
	    	this.openState[i] = false;
	}
	public void open(int row, int column) { // open site (row i, column j) if it is not already
		int indexI = row - 1;
		int indexJ = column - 1;
		if (isOpen(row, column)) return;
		int offset = indexI*totalN + indexJ; 
		int cellOffset = offset + 1; 
		this.openState[offset] = true; //make it open
		/*
		 * Below you have to connect it with top/bottom cell
		 * and bottom site if your site is on top/bottom row
		 */
		if (indexI == 0) //if it is top row
		{
			cell.union(0,  cellOffset);		
		}
		if (indexI == totalN - 1) //if it is bottom row
		{
			cell.union((totalN * totalN) + 1, cellOffset);
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
			int cellOffsetLeft = (leftIndexI * totalN + leftIndexJ) + 1;
			cell.union(cellOffsetLeft, cellOffset);
		  }
		}
		int rightIndexI = indexI; //same row
		int rightIndexJ = indexJ + 1; //1 column greater
		if (rightIndexJ < totalN)
		{
		  if (isOpen(rightIndexI + 1, rightIndexJ + 1)) //check if it is open
		  {
			int cellOffsetRight = (rightIndexI * totalN + rightIndexJ) + 1;
			cell.union(cellOffsetRight, cellOffset);
		  }
		}
		int upIndexI = indexI - 1; //1 row less
		int upIndexJ = indexJ; //same column 
		if (upIndexI >= 0)
		{
		  if (isOpen(upIndexI + 1, upIndexJ + 1)) //check if it is open
		  {
			int cellOffsetUp = (upIndexI * totalN + upIndexJ) + 1;
			cell.union(cellOffsetUp, cellOffset);
		  }
		}
		int downIndexI = indexI + 1; //1 row greater
		int downIndexJ = indexJ; //same column 
		if (downIndexI < totalN)
		{
		  if (isOpen(downIndexI + 1, downIndexJ + 1)) //check if it is open
		  {
			int cellOffsetDown = (downIndexI * totalN + downIndexJ) + 1;
			cell.union(cellOffsetDown, cellOffset);
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
		int indexI = row - 1;
		int indexJ = column - 1;
		int offset = indexI * totalN + indexJ; 
		int cellIndex = offset + 1;

		boolean isFull = cell.connected(0, cellIndex);
		return (isOpen(row, column) && isFull);
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
		return cell.connected(0,  totalN * totalN + 1); 		
	}

}
