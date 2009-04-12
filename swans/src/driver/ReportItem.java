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

		this.report_id = id;
		this.home_node = node;

	}
	
	public void increaseOtherHit()
	{
		numOfOtherHit++;
	}
	
	public void increaseHit()
	{
		numOfHit++;
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

}
