//Wenxuan Gao, spring 2009
package driver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

public class ReportBook {
	static private long gRN = 0;//global report number control
	
	private Vector<ReportItem> ReportList;
	private final int sizeLimit = 20;
	private HashSet<Long> neighborWantIdList = new HashSet<Long>();//what neighbor want IDSa - TSb
	private HashSet<Long> selfunknowIdList = new HashSet<Long>(); //what the neighbor can offer IDSb - IDSa
	private HashSet<Long> trackSet = new HashSet<Long>(); //store all the report id of the reports that has ever received by this node
	private HashSet<Long> reportIdList = new HashSet<Long>();
	private MALENA supplytrainer = new MALENA();
	private HashSet<ReportItem> answerSet = new HashSet<ReportItem>();//store the report content that should send to the encountered neighbor
	private HashSet<Long> neverTransmitSet = new HashSet<Long>(); //track the report that has never transmit from this node
	private Vector<Long> advSet = new Vector<Long>();

	public ReportBook(){
		ReportList = new Vector<ReportItem>();
		
	}
	
	public ReportBook(int node, QueryBook querybook){
		ReportList = new Vector<ReportItem>();
		addReport(node, querybook);
	}
	
	public ReportBook(ReportBook book){
		gRN = book.gRN;
		ReportList = book.getReportList();
	}

	/**
	 * match a queryitem and update the number of hit if matched
	 * @param q
	 */
	public void match(QueryItem q)
	{
		for(ReportItem qi : ReportList)
		{
			long reportId = qi.getReport_id();
			if(qi.match(q))
			{
				answerSet.add(qi); // if match B's query, this report is a candidate in answer set
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

	/**
	 * compute the supply of all the reports
	 * after knowing the neighborWantIdList in communication step 2
	 */
	public void computeSupply(QueryBook querybook)
	{
		computeDemand(querybook);
		for(ReportItem report : ReportList)
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
	
	public void computeDemand(QueryBook querybook)
	{
		for(ReportItem report : ReportList)
		{
			report.computeDemand(querybook);
		}
	}
	
	public void getHitReport(LinkedList<QueryItem> otherQueryList)
	{
		answerSet.clear();
		for(QueryItem query : otherQueryList)
		{
			match(query);
		}
	}
	
	

	/**
	 * create a msg body which contains the current report in the node
	 * @param size
	 * @return
	 */
	public Vector<ReportItem> createAnswerMsg(int size, LinkedList<QueryItem> otherQueryList)
	{
		
		getHitReport(otherQueryList);
		Vector<ReportItem> reporttoSend = new Vector();
		rankReport();
		
		int reportnum = answerSet.size();
		Vector<ReportItem> reportsCandidate = new Vector(answerSet);
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
	
	public Vector<ReportItem> createBrokerMsg(int size)
	{
		Vector<ReportItem> reporttoSend = new Vector();
		//do not have to rerank the report
		Vector<ReportItem> reportsCandidate = new Vector(ReportList);
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
	
	public Vector<ReportItem> createRelayMsg()
	{
		Vector<ReportItem> reporttoSend = new Vector();
		
		for(Long id : advSet)
		{
			for(ReportItem report : ReportList)
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
		Vector<ReportItem> reportCandidate = new Vector();
		for(Long id : neverTransmitSet)
		{
			for(ReportItem report : ReportList)
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
	public void mergeReport(Vector<ReportItem> reportSet)
	{
		
		int requiredsize = 0;
		for(ReportItem report : reportSet)
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

		for(ReportItem report : reportSet)
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
		CacheScheme.GRS(this.getReportList());
	}
	
	public void rankReport(Vector<ReportItem> reports)
	{
		CacheScheme.GRS(reports);
	}
	
	public int getBookSize()
	{
		int i = 0;
		for(ReportItem report : ReportList)
		{
			i += report.getSize();
		}
		return i;
	}
	
	synchronized public void delLastReport()
	{
		int i = ReportList.size();
		if(i == 0)
			return;
		reportIdList.remove(ReportList.get(i - 1).getReport_id());
		neverTransmitSet.remove(ReportList.get(i - 1).getReport_id());
		ReportList.remove(i - 1);
		
	}
	
	
	
	
	synchronized public long addReport(int node, QueryBook querybook){
		//add a new report
		//Every new report have to be added using this method
		//to ensure its id is unique
		ReportItem report = new ReportItem(++gRN, node);
		trackSet.add(report.getReport_id());
		reportIdList.add(report.getReport_id());
		neverTransmitSet.add(report.getReport_id());
		this.getReportList().add(report);
		queryNewReport(report, querybook);
		return gRN;
		
		
	}
	
	
	
	public void queryNewReport(ReportItem report, QueryBook querybook)
	{
		for(QueryItem query : querybook.getQueryList())
		{
			if(report.match(query))
			{
				for(ReportItem otherqi : ReportList)
				{
					if(otherqi.getReport_id() != report.getReport_id())
					{
						otherqi.increaseOtherHit();
						otherqi.increaseOtherHit2();
					}
				}
				Evaluation.increaseTotal_answers(1);
				report.setNumOfOtherHit(0);
				report.increaseHit();
				report.setNumOfOtherHit2(0);
				report.increaseHit2();
				
			}
		}
	}
	
	public boolean isReportExisting(long r_id) {
		// is report already in the book?
		ReportItem reportItem = null;
		Iterator<ReportItem> it=null;
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
	
	public boolean isReportExisting(ReportItem r) {
		long rid=r.getReport_id();
		return isReportExisting(rid);
	}
	
	public static long getGRN() {
		return gRN;
	}
	public static void setGRN(long grn) {
		gRN = grn;
	}
	

	
	public Vector<ReportItem> getReportList() {
		return ReportList;
	}
	public void setReportList(Vector<ReportItem> reportList) {
		ReportList = reportList;
	}
	public HashSet<Long> getNeighborWantIdList()
	{
		return neighborWantIdList;
	}
	public void setNeighborWantIdList(HashSet<Long> neighborWantIdList)
	{
		this.neighborWantIdList = neighborWantIdList;
	}
	public HashSet<Long> getSelfunknowIdList()
	{
		return selfunknowIdList;
	}
	public void setSelfunknowIdList(HashSet<Long> selfunknowIdList)
	{
		selfunknowIdList.removeAll(trackSet);
		this.selfunknowIdList = selfunknowIdList;
	}
	public HashSet<Long> getTrackSet()
	{
		return trackSet;
	}
	public void setTrackSet(HashSet<Long> trackSet)
	{
		this.trackSet = trackSet;
	}
	public MALENA getSupplytrainer()
	{
		return supplytrainer;
	}
	public void setSupplytrainer(MALENA supplytrainer)
	{
		this.supplytrainer = supplytrainer;
	}
	public int getSizeLimit()
	{
		return sizeLimit;
	}
	public HashSet<Long> getReportIdList()
	{
		if(!reportIdList.isEmpty())
			reportIdList.clear();
		for(ReportItem report : ReportList)
		{
			reportIdList.add(report.getReport_id());
		}
		return reportIdList;
	}
	public void setReportIdList(HashSet<Long> reportIdList)
	{
		this.reportIdList = reportIdList;
	}
	public HashSet<ReportItem> getAnswerSet()
	{
		return answerSet;
	}
	public void setAnswerSet(HashSet<ReportItem> answerSet)
	{
		this.answerSet = answerSet;
	}

	
	
}