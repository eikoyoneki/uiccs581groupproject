package driver;

import java.util.Random;

public class Report {
	private double value;

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
