package driver;

public class Query {
	
	double center;
	double range;
	
	public Query()
	{
		this.center = 0.5;
		this.range =0.1;		
	}
	
	public Query(Query q)
	{
		this.center = q.center;
		this.range = q.range;		
	}
	public Query(double center, double range) {
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
}
