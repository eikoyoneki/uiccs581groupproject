package driver;

public class KNNReportItem {
	
	int home_node;
	int time;
	double x;
	double y;
	
	public KNNReportItem(int home_node, int time, double x, double y) {
		super();
		this.home_node = home_node;
		this.time = time;
		this.x = x;
		this.y = y;
	}	
	
	public int getHome_node() {
		return home_node;
	}
	public void setHome_node(int home_node) {
		this.home_node = home_node;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public double getX() {
		return x;
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
