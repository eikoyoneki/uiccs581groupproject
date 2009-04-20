package driver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

public class KNNReportBook {
	static private long gRN = 0;//global report number control
	private Vector<KNNReportItem> ReportList;
	private final int sizeLimit =JistExperiment.reportBookSize;
	private HashSet<Long> neighborWantIdList = new HashSet<Long>();//what neighbor want IDSa - TSb
	private HashSet<Long> selfunknowIdList = new HashSet<Long>(); //what the neighbor can offer IDSb - IDSa
	private HashSet<Long> trackSet = new HashSet<Long>(); //store all the report id of the reports that has ever received by this node
	private HashSet<Long> reportIdList = new HashSet<Long>();
	private MALENA supplytrainer = new MALENA();
	private HashSet<KNNReportItem> answerSet = new HashSet<KNNReportItem>();
	private HashSet<Long> neverTransmitSet = new HashSet<Long>(); //track the report that has never transmit from this node
	private Vector<Long> advSet = new Vector<Long>();
	
	public KNNReportBook(){
		ReportList = new Vector<KNNReportItem>();
	}
	
	public KNNReportBook(int node, KNNQueryBook querybook){
		ReportList = new Vector<KNNReportItem>();		
	}
	
	public KNNReportBook(KNNReportBook book){
		gRN = book.gRN;
		ReportList = book.getReportList();
	}
	
	//compute the max distance in the reportDB
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
				answerSet.add(qi);// if match B's query, this report is a candidate in answer set
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
	public void computeSupply(KNNQueryBook querybook)
	{
		computeDemand(querybook);
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
	
	public void computeDemand(KNNQueryBook querybook)
	{
		double max_dist = computeMaxDist(querybook.getQueryList().getFirst());
		for(KNNReportItem report : ReportList)
		{
			report.computeDemand(querybook,max_dist);
		}
	}
	
	public void getHitReport(LinkedList<KNNQueryItem> otherQueryList)
	{
		for(KNNQueryItem query : otherQueryList)
		{
			match(query);
		}
	}
	
	/**
	 * create a msg body which contains the current report in the node
	 * @param size
	 * @return
	 */
	public Vector<KNNReportItem> createAnswerMsg(int size, LinkedList<KNNQueryItem> otherQueryList)
	{
		
		getHitReport(otherQueryList);
		Vector<KNNReportItem> reporttoSend = new Vector();
		rankReport();
		
		int reportnum = answerSet.size();
		Vector<KNNReportItem> reportsCandidate = new Vector(answerSet);
		int currentsize = 0;
		for(int i = 0; i < reportnum; ++i)
		{
			if(neighborWantIdList.contains(reportsCandidate.get(i).getReport_id()))
			{
				if((currentsize += reportsCandidate.get(i).getSize()) < size)
				{
					reporttoSend.add(reportsCandidate.get(i));
					neverTransmitSet.remove(reportsCandidate.get(i).getReport_id());
				}
				else
					break;
					
			}
		}

		return reporttoSend;
	}
	
	public Vector<KNNReportItem> createBrokerMsg(int size)
	{
		Vector<KNNReportItem> reporttoSend = new Vector();
		//do not have to rerank the report
		Vector<KNNReportItem> reportsCandidate = new Vector(ReportList);
		reportsCandidate.removeAll(answerSet);
		int reportnum = reportsCandidate.size();
		int currentsize = 0;
		for(int i = 0; i < reportnum; ++i)
		{
			if(neighborWantIdList.contains(reportsCandidate.get(i).getReport_id()))
			{
				if((currentsize += reportsCandidate.get(i).getSize()) < size)
				{
					reporttoSend.add(reportsCandidate.get(i));
					neverTransmitSet.remove(reportsCandidate.get(i).getReport_id());
				}
				else
					break;
					
			}
		}
		return reporttoSend;
	}
	
	public Vector<KNNReportItem> createRelayMsg()
	{
		Vector<KNNReportItem> reporttoSend = new Vector();
		
		for(Long id : advSet)
		{
			for(KNNReportItem report : ReportList)
			{
				if(report.getReport_id() == id)
				{
					reporttoSend.add(report);
					neverTransmitSet.remove(report.getReport_id());
				}
			}
		}
		
		return reporttoSend;
	}
	
	public Vector<Long> createAdvMsg(int size)
	{
		advSet.clear();
		Vector<KNNReportItem> reportCandidate = new Vector();
		for(Long id : neverTransmitSet)
		{
			for(KNNReportItem report : ReportList)
			{
				if(report.getReport_id() == id)
					reportCandidate.add(report);
			}
		}
		rankReport(reportCandidate);
		
		int reportnum = reportCandidate.size();
		int currentsize = 0;
		for(int i = 0; i < reportnum; ++i)
		{
			
			if((currentsize += reportCandidate.get(i).getSize()) < size)
			{
				advSet.add(reportCandidate.get(i).getReport_id());
			}
			else
				break;
					
		}
		
		return advSet;
	}
	
	public boolean createREQMsg(Vector<Long> advMsg)
	{
		boolean req = false;
		for(Long reportId : advMsg)
		{
			if(!trackSet.contains(reportId))
			{
				req = true;
				break;
			}
		}		
		return req;
	}
	
	/**
	 * add the reports received from other nodes in the msg
	 * how to rank the new reports added, we defined our self
	 * and we update the track set here.
	 * @param reportSet
	 */
	public void mergeReport(Vector<KNNReportItem> reportSet)
	{
		//rankReport();
		int requiredsize = 0;
		for(KNNReportItem report : reportSet)
		{
			requiredsize += report.getSize();
		}

		if(getBookSize() + requiredsize > sizeLimit)
			this.rankReport();
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
			reportIdList.add(report.getReport_id());
			neverTransmitSet.add(report.getReport_id());
		}
		
	}
	
	public void rankReport()
	{
		rankReport(this.getReportList());
	}
	
	public void rankReport(Vector<KNNReportItem> reports)
	{
		KNNCacheScheme.GRS(reports);
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
		reportIdList.remove(ReportList.get(i - 1).getReport_id());
		neverTransmitSet.remove(ReportList.get(i - 1).getReport_id());
		ReportList.remove(i - 1);
	}
		
	synchronized public long addReport(int node,double x, double y, KNNQueryBook querybook){
		//add a new report
		//Every new report have to be added using this method
		//to ensure its id is unique
		KNNReportItem report = new KNNReportItem(++gRN,node,x,y);
		trackSet.add(report.getReport_id());
		reportIdList.add(report.getReport_id());
		neverTransmitSet.add(report.getReport_id());
		this.getReportList().add(report);
		queryNewReport(report, querybook);
		return gRN;
		
		
	}
	
	public void queryNewReport(KNNReportItem report, KNNQueryBook querybook)
	{
		for(KNNQueryItem query : querybook.getQueryList())
		{
			double max_dist = computeMaxDist(query);
			
				for(KNNReportItem otherqi : ReportList)
				{
					if(otherqi.getReport_id() != report.getReport_id())
					{
						otherqi.increaseOtherHit(report.match(query,max_dist));
						otherqi.increaseOtherHit2(report.match(query,max_dist));
					}
				}
				Evaluation.increaseTotal_answers(1);
				report.setNumOfOtherHit(0);
				report.increaseHit(report.match(query,max_dist));
				report.setNumOfOtherHit2(0);
				report.increaseHit2(report.match(query,max_dist));
				
			
		}
	}
	
	synchronized public long addReport(int node, double x, double y){
		//add a new report
		//Every new report have to be added using this method
		//to ensure its id is unique
//		double x = 0, y=0;
//		x = getNodeLocationX(node); //still need to be implemented
//		y = getNodeLocationY(node);
		
		KNNReportItem report = new KNNReportItem(++gRN,node,x,y);		
		trackSet.add(report.getReport_id());
		reportIdList.add(report.getReport_id());
		neverTransmitSet.add(report.getReport_id());
		
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
		selfunknowIdList.removeAll(trackSet);
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
