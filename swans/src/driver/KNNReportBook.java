package driver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
	
	//compute the max distance
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
		double max_dist = computeMaxDist(q);
		for(KNNReportItem qi : ReportList)
		{
			long reportId = qi.getReport_id();
			double degree = qi.match(q, max_dist);
			if(true)
			{
				answerSet.add(qi);
				for(KNNReportItem otherqi : ReportList)
				{
					if(otherqi.getReport_id() != reportId)
					{
						otherqi.increaseOtherHit(degree);
						if(neighborWantIdList.contains(reportId))
							otherqi.increaseOtherHit2(degree);
					}
				}
				
				qi.setNumOfOtherHit(0);
				qi.increaseHit(degree);
				if(neighborWantIdList.contains(reportId))
				{
					qi.setNumOfOtherHit2(0);
					qi.increaseHit2(degree);
				}//endif
				
			}//end if(true)
		}//end for
	}//end
	
	/**
	 * compute the supply of all the reports
	 * after knowing the neighborWantIdList in communication step 2
	 */
	public void computeSupply()
	{
		for(KNNReportItem report : ReportList)
		{
			if(!neighborWantIdList.contains(report.getReport_id()))
			{
				supplytrainer.addIV(report, false);
				report.increasefi();
			}
			else
			{
				supplytrainer.addIV(report, true);
			}
		}
		supplytrainer.bayesianTrain(this);
	}
	
	public void getHitReport(KNNQueryItem q)
	{
		match(q);
	}
	
	/**
	 * create a msg body which contains the current report in the node
	 * @param size
	 * @return
	 */
	public Vector<KNNReportItem> createAnswerMsg(int size, KNNQueryItem q)
	{
		
		getHitReport(q);
		Vector<KNNReportItem> reporttoSend = new Vector<KNNReportItem>();
		rankReport();
		int i = 0;
		int reportnum = answerSet.size();
		Vector<KNNReportItem> reportsCandidate = new Vector<KNNReportItem>(answerSet);
		
		int currentsize = reportsCandidate.get(i).getSize();
		while(currentsize < size && i < reportnum)
		{
			//the reports must be new to B and also hit by B's query
			if(neighborWantIdList.contains(reportsCandidate.get(i).getReport_id()))
			{
				reporttoSend.add(reportsCandidate.get(i));
				i++;
				if(i < reportnum)
					currentsize += reportsCandidate.get(i).getSize(); 
				else
					break;
			}
			else
				i++;
		}
		return reporttoSend;
	}
	
	public Vector<KNNReportItem> createBrokerMsg(int size)
	{
		Vector<KNNReportItem> reporttoSend = new Vector<KNNReportItem>();
		rankReport();
		int i = 0;
		int reportnum = ReportList.size();
		Vector<KNNReportItem> reportsCandidate = new Vector<KNNReportItem>(ReportList);
		reportsCandidate.removeAll(answerSet);
		int currentsize = reportsCandidate.get(i).getSize();
		while(currentsize < size && i < reportnum)
		{
			//the broker enhance rpeorts only have to be new to B
			if(neighborWantIdList.contains(reportsCandidate.get(i).getReport_id()))
			{
				reporttoSend.add(reportsCandidate.get(i));
				i++;
				if(i < reportnum)
					currentsize += reportsCandidate.get(i).getSize(); 
				else
					break;
			}
			else
				i++;
		}
		return reporttoSend;
	}
	
	/**
	 * add the reports received from other nodes in the msg
	 * how to rank the new reports added, we defined our self
	 * and we update the track set here.
	 * @param reportSet
	 */
	public void mergeReport(Vector<KNNReportItem> reportSet)
	{
		rankReport();
		int requiredsize = 0;
		for(KNNReportItem report : reportSet)
		{
			requiredsize += report.getSize();
		}

		//remove the low priority reports and 
		//get enough space for the new reports
		while(getBookSize() + requiredsize > sizeLimit)
		{
			delLastReport();
		}

		for(KNNReportItem report : reportSet)
		{
			report.refresh();
			ReportList.add(report);
			trackSet.add(report.getReport_id());
		}
		
	}
	
	public void rankReport()
	{
		KNNCacheScheme.LRU1(this.getReportList());
	}
	
	public void rankReport(Vector<KNNReportItem> reports)
	{
		KNNCacheScheme.LRU1(reports);
	}
	
	public int getBookSize()
	{
		int i = 0;
		for(KNNReportItem report : ReportList)
		{
			i += report.getSize();
		}
		return i;
	}
	
	synchronized public void delLastReport()
	{
		int i = ReportList.size();
		ReportList.remove(i - 1);
	}
	
	
	synchronized public boolean delReport(ReportItem r){
		long rid= r.getReport_id();
		return delReport(rid);		
	}
	
	synchronized public boolean delReport(long r_id ){
		//delete an report
		KNNReportItem reportItem = null;
		Iterator<KNNReportItem> it=null;
		it = this.getReportList().iterator();
		while(it.hasNext())
		{
			reportItem =  it.next();

			if(reportItem.getReport_id() == r_id)
			{
				//this is the order we should remove
				it.remove();
				return true;
			}
		}
		return false;
	}
	
	synchronized public long addReport(int home_node, int time, double x,
			double y, int seed){
		//add a new report
		//Every new report have to be added using this method
		//to ensure its id is unique
		
		KNNReportItem report = new KNNReportItem(++gRN, home_node, time, x,y, seed);
		
		return addReport(report);
	}
	
	synchronized public long addReport(KNNReportItem report) {
		//add a existing report received from other nodes
		this.getReportList().add(report);				
		return gRN;
	}
	
	public boolean isReportExisting(long r_id) {
		// is report already in the book?
		KNNReportItem reportItem = null;
		Iterator<KNNReportItem> it=null;
		it = this.getReportList().iterator();
		while(it.hasNext())
		{
			reportItem =  it.next();

			if(reportItem.getReport_id() == r_id)
			{
				return true;				
			}
		}		
		return false;
	}
	
	public boolean isReportExisting(KNNReportItem r) {
		long rid=r.getReport_id();
		return isReportExisting(rid);
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
