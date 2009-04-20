package driver;


public class KNNQueryItem extends QueryItem {

	double x;
	double y;
	
	public KNNQueryItem(long query_id, int home_node, double x, double y) {
		super(query_id, home_node);
		this.x = x;
		this.y = y;
	}
	
	

	public double getX() {
		return x;
	}
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void setX(double x) {
		this.x = x;		
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}


}
