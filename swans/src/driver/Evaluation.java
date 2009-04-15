package driver;

public class Evaluation {
	private int total_received_report_no;
	private int total_received_from_neigbor_report;
	private int total_answers;
	
	
	private double recall;         //recall = match_throughput/total_answer
	private double match_throughput;  //the total number of reports that are “correctly” transmitted
	private double match_ratio;    //precision = match_throughput/total_received_report_no
	private double response_time;   //
	
	

}
