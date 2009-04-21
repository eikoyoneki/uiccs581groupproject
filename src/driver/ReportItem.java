//Wenxuan Gao, spring 2009
package driver;

import java.util.Calendar;
import java.util.Random;

import jist.runtime.JistAPI;

public class ReportItem
{
	private long report_id;
	private int home_node;
	private int size;
	private double value;
	private Calendar createTime;

	private int numOfHit = 0; // number of hit for this report in the certain node
	private int numOfOtherHit = 0; // number of hit for other report in the node after the hit of itself

	private int numOfHit2 = 0;
	private int numOfOtherHit2 = 0; // the number of hit correspond to the LRU2,
									// and LFU2

	private long duration = 0; // how long the report stay in the reportDB
	private double freq = 0; // frequency of report accessed,added in need of
								// LFU = numOfHit / duration,
								// should be updated every simulation time
	private double freq2 = 0; // used for LFU2,= numOfHit2 / duration,
	
	private double demand = 0;
	private double supply = 0;
	private int age = 0;
	private int timeEncounteratNeighbor = 0;// number of times the report has been encountered at a neighbor fi

	/*public ReportItem(int seed)
	{
		Random randomGenerator = new Random(seed);
		value = Math.atan(randomGenerator.nextDouble());
		size = randomGenerator.nextInt(10);
	}

	public ReportItem()
	{
		Random randomGenerator = new Random();
		value = Math.atan(randomGenerator.nextDouble());
		size = randomGenerator.nextInt(10);
	}*/

	public ReportItem(long id, int node, int seed)
	{
		// super();
		// check the boundary condition here
		
		Random randomGenerator = new Random(seed);
		value = Math.atan(randomGenerator.nextDouble());
		size = randomGenerator.nextInt(10);
		this.report_id = id;
		this.home_node = node;
		createTime = Calendar.getInstance();
		
	}
	
	public ReportItem(long id, int node)
	{
		// super();
		// check the boundary condition here
		
		Random randomGenerator = new Random();
		value = Math.atan(randomGenerator.nextDouble());
		size = randomGenerator.nextInt(10);
		this.report_id = id;
		this.home_node = node;
		createTime = Calendar.getInstance();
		System.out.println("the simulation time is " + JistAPI.getTime());
		
		
	}

	/**
	 * calculate the demand of a certain report
	 * the query book is needed
	 */
	public void computeDemand(QueryBook querybook)
	{
		int querynum = querybook.getQueryList().size();
		double demand = 0.0;
		for(QueryItem query : querybook.getQueryList())
		{
			if(match(query))
			{
				demand += 1.0;
			}
		}
		demand = demand / querynum;
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

		duration -= (Calendar.getInstance().getTimeInMillis() - createTime.getTimeInMillis()); 
		freq = 1; 
		freq2 = 0;
	}

	public void increasefi()
	{
		timeEncounteratNeighbor++;
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
		{
			if(!q.isMatched())
			{
				q.setMatched(true);
				long time = Calendar.getInstance().getTimeInMillis() - q.getCreateTime().getTimeInMillis();
				Evaluation.increaseTotal_response_time(time);
				Evaluation.increaseTotal_answered_query(1);
			}
			GlobalDB.addMatchPair(q, this);
			
			return true;
			
		}
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

	public long getDuration()
	{
		duration += Calendar.getInstance().getTimeInMillis() - createTime.getTimeInMillis();
		return duration;
	}

	public void setDuration(long duration)
	{
		this.duration = duration;
	}

	public double getFreq()
	{
		setFreq();
		return freq;
	}

	public double getFreq2()
	{
		setFreq2();
		return freq2;
	}

	public void setFreq()
	{
		this.freq = (double) this.numOfHit / (double) getDuration();
	}

	public void setFreq2()
	{
		this.freq2 = (double) this.numOfHit2 / (double) getDuration();
	}

	public double getDemand()
	{
		return demand;
	}

	public void setDemand(double demand)
	{
		this.demand = demand;
	}

	public double getSupply()
	{
		return supply;
	}

	public void setSupply(double supply)
	{
		this.supply = supply;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public int getTimeEncounteratNeighbor()
	{
		return timeEncounteratNeighbor;
	}

	public void setTimeEncounteratNeighbor(int timeEncounteratNeighbor)
	{
		this.timeEncounteratNeighbor = timeEncounteratNeighbor;
	}

	public Calendar getCreateTime()
	{
		return createTime;
	}
	

}
