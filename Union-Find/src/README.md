
[Source](http://coursera.cs.princeton.edu/algs4/assignments/percolation.html "Permalink to Programming Assignment 1: Percolation")

# Programming Assignment 1: Percolation

## Programming Assignment 1: Percolation

Write a program to estimate the value of the _percolation threshold_ via Monte Carlo simulation.

**Install a Java programming environment.** Install a Java programming environment on your computer by following these step-by-step instructions for your operating system [ [Mac OS X][1] · [Windows][2] · [Linux][3] ]. After following these instructions, the commands `javac-algs4` and `java-algs4` will classpath in both [stdlib.jar][4] and [algs4.jar][5]: the former contains libraries for reading data from _standard input_, writing data to _standard output_, drawing results to _standard draw_, generating random numbers, computing statistics, and timing programs; the latter contains all of the algorithms in the textbook.

**Percolation.** Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as _percolation_ to model such situations.

**The model.** We model a percolation system using an _N_-by-_N_ grid of _sites_. Each site is either _open_ or _blocked_. A _full_ site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system _percolates_ if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic materials example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from top to bottom, with full sites conducting. For the porous substance example, the open sites correspond to empty space through which water might flow, so that a system that percolates lets water fill open sites, flowing from top to bottom.)

![Percolates][6]

**The problem.** In a famous scientific problem, researchers are interested in the following question: if sites are independently set to be open with probability _p_ (and therefore blocked with probability 1 − _p_), what is the probability that the system percolates? When _p_ equals 0, the system does not percolate; when _p_ equals 1, the system percolates. The plots below show the site vacancy probability _p_ versus the percolation probability for 20-by-20 random grid (left) and 100-by-100 random grid (right).

![Percolation threshold for 20-by-20 grid][7]                ![Percolation threshold for 100-by-100 grid][8]          

When _N_ is sufficiently large, there is a _threshold_ value _p_* such that when _p_ < _p_* a random _N_-by-_N_ grid almost never percolates, and when _p_ > _p_*, a random _N_-by-_N_ grid almost always percolates. No mathematical solution for determining the percolation threshold _p_* has yet been derived. Your task is to write a computer program to estimate _p_*.

**Percolation data type.** To model a percolation system, create a data type `Percolation` with the following API:

>
>     public class Percolation {
>        public Percolation(int N)              // create N-by-N grid, with all sites blocked
>        public void open(int i, int j)         // open site (row i, column j) if it is not already
>        public boolean isOpen(int i, int j)    // is site (row i, column j) open?
>        public boolean isFull(int i, int j)    // is site (row i, column j) full?
>        public boolean percolates()            // does the system percolate?
>     }
>

By convention, the indices _i_ and _j_ are integers between 1 and _N_, where (1, 1) is the upper-left site: Throw a `java.lang.IndexOutOfBoundsException` if either _i_ or _j_ is outside this range. The constructor should take time proportional to N^2; all methods should take constant time plus a constant number of calls to the union-find methods `union()`, `find()`, `connected()`, and `count()`.

**Monte Carlo simulation.** To estimate the percolation threshold, consider the following computational experiment:

Initialize all sites to be blocked. Repeat the following until the system percolates: Choose a site (row i, column j) uniformly at random among all blocked sites. Open the site (row i, column j).

The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.

For example, if sites are opened in a 20-by-20 lattice according to the snapshots below, then our estimate of the percolation threshold is 204/400 = 0.51 because the system percolates when the 204th site is opened.

     
![Percolation 50 sites][9]


_50 open sites_

![Percolation 100 sites][10]


_100 open sites_

![Percolation 150 sites][11]


_150 open sites_

![Percolation 204 sites][12]


_204 open sites_

By repeating this computation experiment _T_ times and averaging the results, we obtain a more accurate estimate of the percolation threshold. Let _xt_ be the fraction of open sites in computational experiment _t_. The sample mean μ provides an estimate of the percolation threshold; the sample standard deviation σ measures the sharpness of the threshold.

> ![Estimating the sample mean and variance][13]

Assuming _T_ is sufficiently large (say, at least 30), the following provides a 95% confidence interval for the percolation threshold:

> ![95% confidence interval for percolation threshold][14]

To perform a series of computational experiments, create a data type `PercolationStats` with the following API.

>
>     public class PercolationStats {
>        public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
>        public double mean()                     // sample mean of percolation threshold
>        public double stddev()                   // sample standard deviation of percolation threshold
>        public double confidenceLo()             // returns lower bound of the 95% confidence interval
>        public double confidenceHi()             // returns upper bound of the 95% confidence interval
>        public static void main(String[] args)   // test client, described below
>     }
>

The constructor should throw a `java.lang.IllegalArgumentException` if either _N_ ≤ 0 or _T_ ≤ 0.

Also, include a `main()` method that takes two _command-line arguments_ _N_ and _T_, performs _T_ independent computational experiments (discussed above) on an _N_-by-_N_ grid, and prints out the mean, standard deviation, and the _95% confidence interval_ for the percolation threshold. Use _standard random_ from our standard libraries to generate random numbers; use _standard statistics_ to compute the sample mean and standard deviation.

>
>     % **java PercolationStats 200 100**
>     mean                    = 0.5929934999999997
>     stddev                  = 0.00876990421552567
>     95% confidence interval = 0.5912745987737567, 0.5947124012262428
>
>     % **java PercolationStats 200 100**
>     mean                    = 0.592877
>     stddev                  = 0.009990523717073799
>     95% confidence interval = 0.5909188573514536, 0.5948351426485464
>
>
>     % **java PercolationStats 2 10000**
>     mean                    = 0.666925
>     stddev                  = 0.11776536521033558
>     95% confidence interval = 0.6646167988418774, 0.6692332011581226
>
>     % **java PercolationStats 2 100000**
>     mean                    = 0.6669475
>     stddev                  = 0.11775205263262094
>     95% confidence interval = 0.666217665216461, 0.6676773347835391
>

**Analysis of running time and memory usage (optional and not graded).** Implement the `Percolation` data type using the quick-find algorithm [QuickFindUF.java][15] from `algs4.jar`.

Use the stopwatch data type from our standard library to measure the total running time of PercolationStats. How does doubling N affect the total running time? How does doubling T affect the total running time? Give a formula (using tilde notation) of the total running time on your computer (in seconds) as a single function of both N and T. Using the 64-bit memory-cost model from lecture, give the total memory usage in bytes (using tilde notation) that an N-by-N percolation system uses. Count all memory that is used, including memory for the union-find data structure.

Now, implement the `Percolation` data type using the weighted quick-union algorithm [WeightedQuickUnionUF.java][16] from `algs4.jar`. Answer the questions in the previous paragraph.

**Deliverables.** Submit only `Percolation.java` (using the weighted quick-union algorithm as implemented in the `WeightedQuickUnionUF` class) and `PercolationStats.java`. We will supply `stdlib.jar` and `WeightedQuickUnionUF`. Your submission may not call any library functions other than those in `java.lang`, `stdlib.jar`, and `WeightedQuickUnionUF`.




This assignment was developed by Bob Sedgewick and Kevin Wayne.
Copyright © 2008.

   [1]: http://algs4.cs.princeton.edu/mac
   [2]: http://algs4.cs.princeton.edu/windows
   [3]: http://algs4.cs.princeton.edu/linux
   [4]: http://algs4.cs.princeton.edu/code/stdlib.jar
   [5]: http://algs4.cs.princeton.edu/code/algs4.jar
   [6]: http://coursera.cs.princeton.edu/algs4/checklists/percolates.png
   [7]: http://coursera.cs.princeton.edu/algs4/checklists/percolation-threshold20.png
   [8]: http://coursera.cs.princeton.edu/algs4/checklists/percolation-threshold100.png
   [9]: http://coursera.cs.princeton.edu/algs4/checklists/percolation-50.png
   [10]: http://coursera.cs.princeton.edu/algs4/checklists/percolation-100.png
   [11]: http://coursera.cs.princeton.edu/algs4/checklists/percolation-150.png
   [12]: http://coursera.cs.princeton.edu/algs4/checklists/percolation-204.png
   [13]: http://coursera.cs.princeton.edu/algs4/checklists/percolation-stats.png
   [14]: http://coursera.cs.princeton.edu/algs4/checklists/percolation-confidence.png
   [15]: http://algs4.cs.princeton.edu/15uf/QuickFindUF.java.html
   [16]: http://algs4.cs.princeton.edu/15uf/WeightedQuickUnionUF.java.html