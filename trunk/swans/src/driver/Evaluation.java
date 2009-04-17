package driver;

public class Evaluation {
	private double total_report_received=0;
	private double match_throuhput=0; //total_report_received_from_neigbors
					//the total number of reports that are “correctly” transmitted	
	private double total_answers=0; // total number of answers ever generated in the system
	private double total_response_time=0;
	private double total_answered_query=0;
    //those parameters are double not int because we need consider KNN
	
	
	//private double recall=0;         //recall = match_throughput/total_answers
	//private double match_ratio=0;    //precision = match_throughput/total_report_received
	//private double avg_response_time=0;  //total_response_time/total_answered_query	
	
	public double getTotal_report_received() {
		return total_report_received;
	}
	public void increaseTotal_report_received(double x) {
		this.total_report_received += x;
	}
	public double getMatch_throuhput() {
		return match_throuhput;
	}
	public void increaseMatch_throuhput(double x) {
		this.match_throuhput += x;
	}
	public double getTotal_answers() {
		return total_answers;
	}
	public void increaseTotal_answers(double x) {
		this.total_answers += x;
	}
	public double getTotal_response_time() {
		return total_response_time;
	}
	public void increaseTotal_response_time(double x) {
		this.total_response_time += x;
	}
	public double getTotal_answered_query() {
		return total_answered_query;
	}
	public void increaseTotal_answered_query(double x) {
		this.total_answered_query += x;
	}
	public double getRecall() {
		return match_throuhput /total_answers;
	}

	public double getMatch_ratio() {
		return match_throuhput/total_answers;
	}

	public double getResponse_time() {
		
		return total_response_time/total_answered_query;
	}
	
	

}
