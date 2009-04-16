package driver;

import java.util.Random;

public class KNNReportItem {
	
	
	int report_id;
	int home_node;
	int time;
	double x;
	double y;
	private int size;
	
	private double numOfHit = 0; // number of hit for this report in the certain node
	private double numOfOtherHit = 0; // number of hit for other report in the node after the hit of itself

	private double numOfHit2 = 0;
	private double numOfOtherHit2 = 0; // the number of hit correspond to the LRU2,
									// and LFU2

	private int duration = 0; // how long the report stay in the reportDB
	private double freq = 0; // frequency of report accessed,added in need of
								// LFU = numOfHit / duration,
								// should be updated every simulation time
	private double freq2 = 0; // used for LFU2,= numOfHit2 / duration,
	
	private double demand = 0;
	private double supply = 0;
	private int age = 0;
	private int timeEncounteratNeighbor = 0;// number of times the report has been encountered at a neighbor fi

	
	public KNNReportItem(int report_id, int home_node, int time, double x,
			double y, int seed) {
		
		Random randomGenerator = new Random(seed);
		this.report_id = report_id;
		this.home_node = home_node;
		this.time = time;
		this.x = x;
		this.y = y;
		this.size = randomGenerator.nextInt(10);
	}
	
	public void increasefi()
	{
		timeEncounteratNeighbor++;
	}

	public void refresh()
	{
		numOfHit = 1; 
		numOfOtherHit = 0; 

		numOfHit2 = 0;
		numOfOtherHit2 = 0; 

		duration = 1; 
		freq = 1; 
		freq2 = 0;
	}
	
	public void increaseOtherHit(double x)
	{
		numOfOtherHit += x;
	}

	public void increaseHit(double x)
	{
		numOfHit += x;
	}

	public void increaseOtherHit2(double x)
	{
		numOfOtherHit2 += x;
	}

	public void increaseHit2(double x)
	{
		numOfHit2 += x;
	}
	
	
	
	//max_dist is the distance between sink and peers in the KNNReportBook
	// it's different for each node
	public double match(KNNQueryItem q, double max_dist)
	{
		return Math.sqrt(Math.pow(q.x,2)+ Math.pow(q.y,2))/max_dist;
		
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

	public int getReport_id() {
		return report_id;
	}

	public void setReport_id(int report_id) {
		this.report_id = report_id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getNumOfHit() {
		return numOfHit;
	}

	public void setNumOfHit(double numOfHit) {
		this.numOfHit = numOfHit;
	}

	public double getNumOfOtherHit() {
		return numOfOtherHit;
	}

	public void setNumOfOtherHit(double numOfOtherHit) {
		this.numOfOtherHit = numOfOtherHit;
	}

	public double getNumOfHit2() {
		return numOfHit2;
	}

	public void setNumOfHit2(double numOfHit2) {
		this.numOfHit2 = numOfHit2;
	}

	public double getNumOfOtherHit2() {
		return numOfOtherHit2;
	}

	public void setNumOfOtherHit2(double numOfOtherHit2) {
		this.numOfOtherHit2 = numOfOtherHit2;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getFreq() {
		return freq;
	}

	public void setFreq(double freq) {
		this.freq = freq;
	}

	public double getFreq2() {
		return freq2;
	}

	public void setFreq2(double freq2) {
		this.freq2 = freq2;
	}

	public double getDemand() {
		return demand;
	}

	public void setDemand(double demand) {
		this.demand = demand;
	}

	public double getSupply() {
		return supply;
	}

	public void setSupply(double supply) {
		this.supply = supply;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getTimeEncounteratNeighbor() {
		return timeEncounteratNeighbor;
	}

	public void setTimeEncounteratNeighbor(int timeEncounteratNeighbor) {
		this.timeEncounteratNeighbor = timeEncounteratNeighbor;
	}

}
