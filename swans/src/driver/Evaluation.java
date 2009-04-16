package driver;

public class Evaluation {
	private int total_received_report_no;
	private int total_received_from_neigbor_report;
	private int total_answers;
	
	
	private double recall;         //recall = match_throughput/total_answer
	private double match_throughput;  //the total number of reports that are “correctly” transmitted
	private double match_ratio;    //precision = match_throughput/total_received_report_no
	private double response_time;   //	
	public int getTotal_received_report_no() {
		return total_received_report_no;
	}
	public void setTotal_received_report_no(int total_received_report_no) {
		this.total_received_report_no = total_received_report_no;
	}
	public int getTotal_received_from_neigbor_report() {
		return total_received_from_neigbor_report;
	}
	public void setTotal_received_from_neigbor_report(
			int total_received_from_neigbor_report) {
		this.total_received_from_neigbor_report = total_received_from_neigbor_report;
	}
	public int getTotal_answers() {
		return total_answers;
	}
	public void setTotal_answers(int total_answers) {
		this.total_answers = total_answers;
	}
	public double getRecall() {
		return recall;
	}
	public void setRecall(double recall) {
		this.recall = recall;
	}
	public double getMatch_throughput() {
		return match_throughput;
	}
	public void setMatch_throughput(double match_throughput) {
		this.match_throughput = match_throughput;
	}
	public double getMatch_ratio() {
		return match_ratio;
	}
	public void setMatch_ratio(double match_ratio) {
		this.match_ratio = match_ratio;
	}
	public double getResponse_time() {
		return response_time;
	}
	public void setResponse_time(double response_time) {
		this.response_time = response_time;
	}
	

}
