//Wenxuan Gao, Spring 2009 
package driver;

import java.util.Random;

public class QueryItem {
	long  query_id;
	int home_node;
	double center;
	double range;
	
	public QueryItem(long query_id, int node) 
	{ 
		this.query_id = query_id;
		this.home_node = node;
		Random randomGenerator = new Random();
	    center = Math.abs(randomGenerator.nextGaussian());
		while(center > 1)
		{
			center *= randomGenerator.nextDouble();
		}
		double limit = Math.min(center, 1-center);
		range = limit * randomGenerator.nextDouble(); 
	}
	
	
	public QueryItem(long query_id, int node, int seed)
	{
		this.query_id = query_id;
		this.home_node = node;
        Random randomGenerator = new Random(seed);
        center = Math.abs(randomGenerator.nextGaussian());
		while(center > 1)
		{
			center *= randomGenerator.nextDouble();
		}
		double limit = Math.min(center, 1-center);
		range = limit * randomGenerator.nextDouble();	
	}
	
		
	public double getCenter() {
		return this.center;
	}
	
	public double getRange() {
		return range;
	}
	
	public void setQuery(double center,double range) {
		if(center <=0 || center >=1)
		{
		System.out.println("center should be between 0 and 1");
		System.exit(1);
		}
	if(range<=0||range>=center || (center+range)>=1)
	{
		System.out.println("range must be positive and less than center");
		System.exit(1);
	}
	this.center = center;
	this.range = range;
	}

	public long getQuery_id() {
		return query_id;
	}

	
	public int getHome_node() {
		return home_node;
	}

	public void setHome_node(int home_node) {//may be used in the 2nd query pattern
		this.home_node = home_node;
	}

	public void setQuery_id(long query_id) {
		this.query_id = query_id;
	}
}
