package driver;

import java.util.HashSet;
import java.util.Vector;

public class KNNReportBook {
	static private long gRN = 0;//global report number control
	private Vector<KNNReportItem> ReportList;
	private final int sizeLimit = 10;
	private HashSet<Long> neighborWantIdList = new HashSet<Long>();//what neighbor want IDSa - TSb
	private HashSet<Long> selfunknowIdList = new HashSet<Long>(); //what the neighbor can offer IDSb - IDSa
	private HashSet<Long> trackSet = new HashSet<Long>(); //store all the report id of the reports that has ever received by this node
	private HashSet<Long> reportIdList = new HashSet<Long>();
	private MALENA supplytrainer;
	private HashSet<KNNReportItem> answerSet = new HashSet<KNNReportItem>();
	
	public KNNReportBook(){
		ReportList = new Vector<KNNReportItem>();
	}
	public KNNReportBook(KNNReportBook book){
		gRN = book.gRN;
		ReportList = book.getReportList();
	}
	
	public double computeMaxDist(KNNQueryItem q)
	{
		double max = 0;
		double x = q.getX();
		double y = q.getY();
		
		for(KNNReportItem qi : ReportList)
		{
			double tmp =Math.pow(x-qi.getX(),2)+Math.pow(y-qi.getY(), 2);
			if (tmp > max) max = tmp;			
		}
		
		return Math.sqrt(max);
		
	}
	
	public void match(KNNQueryItem q)
	{
		for(ReportItem qi : ReportList)
		{
			long reportId = qi.getReport_id();
			if(qi.match(q))
			{
				answerSet.add(qi);
				for(ReportItem otherqi : ReportList)
				{
					if(otherqi.getReport_id() != reportId)
					{
						otherqi.increaseOtherHit();
						if(neighborWantIdList.contains(reportId))
							otherqi.increaseOtherHit2();
					}
				}
				
				qi.setNumOfOtherHit(0);
				qi.increaseHit();
				if(neighborWantIdList.contains(reportId))
				{
					qi.setNumOfOtherHit2(0);
					qi.increaseHit2();
				}
				
			}
		}
	}
	
	
	public static long getGRN() {
		return gRN;
	}
	public static void setGRN(long grn) {
		gRN = grn;
	}
	public Vector<KNNReportItem> getReportList() {
		return ReportList;
	}
	public void setReportList(Vector<KNNReportItem> reportList) {
		ReportList = reportList;
	}
	public HashSet<Long> getNeighborWantIdList() {
		return neighborWantIdList;
	}
	public void setNeighborWantIdList(HashSet<Long> neighborWantIdList) {
		this.neighborWantIdList = neighborWantIdList;
	}
	public HashSet<Long> getSelfunknowIdList() {
		return selfunknowIdList;
	}
	public void setSelfunknowIdList(HashSet<Long> selfunknowIdList) {
		this.selfunknowIdList = selfunknowIdList;
	}
	public HashSet<Long> getTrackSet() {
		return trackSet;
	}
	public void setTrackSet(HashSet<Long> trackSet) {
		this.trackSet = trackSet;
	}
	public HashSet<Long> getReportIdList() {
		return reportIdList;
	}
	public void setReportIdList(HashSet<Long> reportIdList) {
		this.reportIdList = reportIdList;
	}
	public MALENA getSupplytrainer() {
		return supplytrainer;
	}
	public void setSupplytrainer(MALENA supplytrainer) {
		this.supplytrainer = supplytrainer;
	}
	public HashSet<KNNReportItem> getAnswerSet() {
		return answerSet;
	}
	public void setAnswerSet(HashSet<KNNReportItem> answerSet) {
		this.answerSet = answerSet;
	}
	public int getSizeLimit() {
		return sizeLimit;
	}
	

}
