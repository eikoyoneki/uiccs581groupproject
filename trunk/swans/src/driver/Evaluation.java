package driver;

public class Evaluation {
	
	static private double total_report_received=0;
	static private double match_throuhput=0; //total_report_received_from_neigbors
					//the total number of reports that are “correctly” transmitted	
	static private double total_answers=0; // total number of answers ever generated in the system
	static private double total_response_time=0;
	static private double total_answered_query=0;
    //those parameters are double not int because we need consider KNN
	
	
	//private double recall=0;         //recall = match_throughput/total_answers
	//private double match_ratio=0;    //precision = match_throughput/total_report_received
	//private double avg_response_time=0;  //total_response_time/total_answered_query	
	
	static public double getTotal_report_received() {
		return total_report_received;
	}
	static public void increaseTotal_report_received(double x) {
		total_report_received += x;
	}
	static public double getMatch_throuhput() {
		return match_throuhput;
	}
	static public void increaseMatch_throuhput(double x) {
		match_throuhput += x;
	}
	static public double getTotal_answers() {
		return total_answers;
	}
	static public void increaseTotal_answers(double x) {
		total_answers += x;
	}
	static public double getTotal_response_time() {
		return total_response_time;
	}
	static public void increaseTotal_response_time(double x) {
		total_response_time += x;
	}
	static public double getTotal_answered_query() {
		return total_answered_query;
	}
	static public void increaseTotal_answered_query(double x) {
		total_answered_query += x;
	}
	static public double getRecall() {
		return match_throuhput /total_answers;
	}

	static public double getMatch_ratio() {
		return match_throuhput/total_answers;
	}

	static public double getResponse_time() {
		
		return total_response_time/total_answered_query;
	}
	

}
