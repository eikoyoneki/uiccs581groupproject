package driver;

public class QueryItem {
	long  query_id;
	int home_node;
	double center;
	double range;
	
	public QueryItem()
	{
		//this.center = 0.5;
		//this.range =0.1;		
	}
	
	public QueryItem(long id, int node, double center, double range) {
		//super();
		//check the boundary condition here
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
		this.query_id = id;
		this.home_node = node;
		this.center = center;
		this.range = range;
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
}
