//Wenxuan Gao, spring 2009
package driver;

public class ReportItem {
	private long report_id;
	private int home_node;
	private int size;
	private double value;
	
	public ReportItem(){}
	
	public ReportItem(long id, int node, int size, double value) {
		//super();
		//check the boundary condition here
		if(value <=0 || value >=1)
			{
			System.out.println("value should be between 0 and 1");
			System.exit(1);
			}
		
		this.report_id = id;
		this.home_node = node;
		this.size = size;
		this.value = value;
	}
	
	
	public double match(QueryItem q){
		if(this.value <= (q.center+q.range) && this.value >= (q.center-q.range))
				return 1.0;
		else
			return 0.0;
	}


	public long getReport_id() {
		return report_id;
	}


	public void setReport_id(long report_id) {
		this.report_id = report_id;
	}


	public int getHome_node() {
		return home_node;
	}


	public void setHome_node(int home_node) {
		this.home_node = home_node;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public double getValue() {
		return value;
	}


	public void setValue(double value) {
		this.value = value;
	}

}
