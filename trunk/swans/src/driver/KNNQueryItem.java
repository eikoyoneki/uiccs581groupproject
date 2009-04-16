package driver;

import java.util.*;

public class KNNQueryItem {
	
	int home_node;
	double x;
	double y;
	
	public KNNQueryItem(int home_node, double x, double y) {
		super();
		this.home_node = home_node;
		this.x = x;
		this.y = y;
	}
	
	
	public int getHome_node() {
		return home_node;
	}
	public void setHome_node(int home_node) {
		this.home_node = home_node;
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
