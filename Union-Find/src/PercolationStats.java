/****************************************************************************
 *  Compilation:  
 *  Execution:  java PercolationStats 200 100 (Example)
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Percolation DataStructure
 *
 ****************************************************************************/
/**
 *  The <tt>PercolationStats</tt> class represents a PercolationStats (experimentation) data structure.
 *  It supports the <em>mean</em> and <em>standard_deviation</em> operation
 *  <p>
 *  This implementation uses Percolation DataStructure
 *  <p>
 *     
 *  @author Santhosh Vaiyapuri
 */
public class PercolationStats {
	private double[] percolationThresholdArray;
	private double mean;
	private double stdDev;
	private int totalT;

	public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
	{
		if (N <= 0  || T <= 0)
			throw new java.lang.IllegalArgumentException();

		percolationThresholdArray = new double[T];
		totalT = T;
		for (int i = 0; i < T; ++i)  // perform T independent computational experiments
		{
			int totalOpen = 0; //no of open sites
			Percolation per = new Percolation(N);
			boolean Percolated = false;

			while (!Percolated)
			{
				//Use standard random from our standard libraries to generate random numbers
				int indexI = StdRandom.uniform(N) + 1;
                int indexJ = StdRandom.uniform(N) + 1;
				if (!per.isOpen(indexI, indexJ))
				{
					per.open(indexI,  indexJ);
					++totalOpen; //increment the no of open sites by one
					Percolated = per.percolates();
				}
			}
			//For example, if sites are opened in a 20-by-20 lattice according to the snapshots below, 
			//then our estimate of the percolation threshold is 204/400 = 0.51 
			//because the system percolates when the 204th site is opened.
			percolationThresholdArray[i] = (double) totalOpen / (double) (N * N);
		}

		//use standard statistics to compute the sample mean and standard deviation
		mean = StdStats.mean(percolationThresholdArray);
		stdDev = StdStats.stddev(percolationThresholdArray);

	}
	/**
     * 
     * @return the sample <tt>mean</tt> of percolation threshold
     */
	public double mean()          
	{
		return mean;
	}
	/**
     * 
     * @return the sample <tt>standard deviation </tt> of percolation threshold
     */
	public double stddev()                 
	{
		return stdDev;
	}
	/**
     * 
     * @return lower bound of the 95% confidence interval
     */
	public double confidenceLo()
	{
		return (mean - ((1.96 * stdDev) / Math.sqrt(totalT)));
	}
	/**
     * 
     * @return higher bound of the 95% confidence interval
     */
	public double confidenceHi() 
	{
		return (mean + ((1.96 * stdDev) / Math.sqrt(totalT)));
	}
	public static void main(String[] args)  
	{
		if (args.length < 2)
		{
			throw new java.lang.IllegalArgumentException();
		}
		int N = Integer.parseInt(args[0]); // N-by-N grid
		int T = Integer.parseInt(args[1]); //perform T independent computational experiments

		PercolationStats per = new PercolationStats(N, T);
		double mean = per.mean();
		double stddev = per.stddev();

        System.out.println("mean                    = " + mean);
        System.out.println("stddev                  = " + stddev);
        System.out.println("95% confidence interval = " + per.confidenceLo() + ", " + per.confidenceHi());
    }
}
