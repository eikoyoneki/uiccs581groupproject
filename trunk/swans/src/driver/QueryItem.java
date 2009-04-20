//Wenxuan Gao, Spring 2009 
package driver;

import java.util.Calendar;
import java.util.Random;

public class QueryItem {
	protected long  query_id;
	protected int home_node;
	protected double center;
	protected double range;
	protected Calendar createTime;
	protected boolean matched = false;
	
	
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
		createTime = Calendar.getInstance();
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
		createTime = Calendar.getInstance();
	}
	
		
	public double getCenter() {
		return this.center;
	}
	
	public double getRange() {
		return range;
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


	public Calendar getCreateTime()
	{
		return createTime;
	}


	public void setCreateTime(Calendar createTime)
	{
		this.createTime = createTime;
	}


	public boolean isMatched()
	{
		return matched;
	}


	public void setMatched(boolean matched)
	{
		this.matched = matched;
	}
	
	
}
