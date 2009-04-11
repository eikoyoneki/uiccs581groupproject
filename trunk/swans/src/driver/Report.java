package driver;

import java.util.Random;

public class Report {
	private double value;
	private int numOfHit = 0; //number of hit for this report in the certain node
	private int numOfOtherHit = 0; //number of hit for other report in the node after the hit of itself

	public Report(int seed)
	{
		Random randomGenerator = new Random(seed);
		value = Math.atan(randomGenerator.nextDouble());
	}
	
	public Report()
	{
		Random randomGenerator = new Random();
		value = Math.atan(randomGenerator.nextDouble());
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

	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}
	
	public boolean match(Query query)
	{
		boolean matchresult = false;
		if(value > (query.center - query.range) && value < (query.center + query.range))
			matchresult = true;
		return matchresult;
	}
	
}
