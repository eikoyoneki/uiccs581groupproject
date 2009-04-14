//Wenxuan Gao, spring 2009
package driver;

import java.util.Random;

public class ReportItem
{
	private long report_id;
	private int home_node;
	private int size;
	private double value;

	private int numOfHit = 0; // number of hit for this report in the certain
	// node
	private int numOfOtherHit = 0; // number of hit for other report in the node
	// after the hit of itself

	private int numOfHit2 = 0;
	private int numOfOtherHit2 = 0; // the number of hit correspond to the LRU2,
									// and LFU2

	private int duration = 0; // how long the report stay in the reportDB
	private double freq = 0; // frequency of report accessed,added in need of
								// LFU
								// = numOfHit / duration,
								// should be updated every simulation time
	private double freq2 = 0; // used for LFU2,= numOfHit2 / duration,

	public ReportItem(int seed)
	{
		Random randomGenerator = new Random(seed);
		value = Math.atan(randomGenerator.nextDouble());
	}

	public ReportItem()
	{
		Random randomGenerator = new Random();
		value = Math.atan(randomGenerator.nextDouble());
	}

	public ReportItem(long id, int node, int seed)
	{
		// super();
		// check the boundary condition here
		Random randomGenerator = new Random(seed);
		value = Math.atan(randomGenerator.nextDouble());
		size = randomGenerator.nextInt(10);
		this.report_id = id;
		this.home_node = node;

	}


	/**
	 * when the nodes report transfered to other nodes
	 *	the other node need to refresh the variables in the report
	 */
	public void refresh()
	{
		numOfHit = 1; 
		numOfOtherHit = 0; 

		numOfHit2 = 0;
		numOfOtherHit2 = 0; 

		duration = 1; 
		freq = 1; 
		freq2 = 0;
	}

	public void increaseOtherHit()
	{
		numOfOtherHit++;
	}

	public void increaseHit()
	{
		numOfHit++;
	}

	public void increaseOtherHit2()
	{
		numOfOtherHit2++;
	}

	public void increaseHit2()
	{
		numOfHit2++;
	}

	public boolean match(QueryItem q)
	{
		if (this.value <= (q.center + q.range)
				&& this.value >= (q.center - q.range))
			return true;
		else
			return false;
	}

	public long getReport_id()
	{
		return report_id;
	}

	public void setReport_id(long report_id)
	{
		this.report_id = report_id;
	}

	public int getHome_node()
	{
		return home_node;
	}

	public void setHome_node(int home_node)
	{
		this.home_node = home_node;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}

	public int getNumOfHit2()
	{
		return numOfHit2;
	}

	public void setNumOfHit2(int numOfHit2)
	{
		this.numOfHit2 = numOfHit2;
	}

	public int getNumOfOtherHit2()
	{
		return numOfOtherHit2;
	}

	public void setNumOfOtherHit2(int numOfOtherHit2)
	{
		this.numOfOtherHit2 = numOfOtherHit2;
	}

	public int getNumOfHit()
	{
		return numOfHit;
	}

	public void setNumOfHit(int numOfHit)
	{
		this.numOfHit = numOfHit;
	}

	public int getNumOfOtherHit()
	{
		return numOfOtherHit;
	}

	public void setNumOfOtherHit(int numOfOtherHit)
	{
		this.numOfOtherHit = numOfOtherHit;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public double getFreq()
	{
		return freq;
	}

	public double getFreq2()
	{
		return freq2;
	}

	public void setFreq()
	{
		this.freq = (double) this.numOfHit / (double) this.duration;
	}

	public void setFreq2()
	{
		this.freq2 = (double) this.numOfHit2 / (double) this.duration;
	}

}
