//Wenxuan Gao, spring 2009
package driver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

public class ReportBook {
	static private long gRN = 0;//global report number control
	private Vector<ReportItem> ReportList;
	private final int sizeLimit = 10;
	private HashSet<Long> neighborWantIdList = new HashSet<Long>();//want neighbor want IDSa - TSb
	private HashSet<Long> selfunknowIdList = new HashSet<Long>(); //what the neighbor can offer IDSb - IDSa
	private HashSet<Long> trackSet = new HashSet<Long>(); //store all the report id of the reports that has ever received by this node
	private MALENA supplytrainer;

	public ReportBook(){
		ReportList = new Vector<ReportItem>();
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
	public void computeSupply()
	{
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
	

	/**
	 * create a msg body which contains the current report in the node
	 * @param size
	 * @return
	 */
	public Vector<ReportItem> createMsg(int size)
	{
		Vector<ReportItem> reporttoSend = new Vector();
		rankReport();
		int i = 0;
		int reportnum = ReportList.size();
		int currentsize = ReportList.get(i).getSize();
		while(currentsize < size )
		{
			reporttoSend.add(ReportList.get(i));
			i++;
			if(i < reportnum)
				currentsize += ReportList.get(i).getSize(); 
			else
				break;
		}
		return reporttoSend;
	}
	

	/**
	 * add the reports received from other nodes in the msg
	 * how to rank the new reports added?
	 * @param reportSet
	 */
	public void mergeReport(Vector<ReportItem> reportSet)
	{
		rankReport();
		int requiredsize = 0;
		for(ReportItem report : reportSet)
		{
			requiredsize += report.getSize();
		}

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
		}
		
	}
	
	public void rankReport()
	{
		CacheScheme.LRU1(this);
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
		ReportList.remove(i - 1);
	}
	
	
	synchronized public boolean delReport(ReportItem r){
		long rid= r.getReport_id();
		return delReport(rid);		
	}
	
	synchronized public boolean delReport(long r_id ){
		//delete an report
		ReportItem reportItem = null;
		Iterator<ReportItem> it=null;
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
	
	synchronized public long addReport(int node, int size, double value){
		//add a new report
		//Every new report have to be added using this method
		//to ensure its id is unique
		ReportItem report = new ReportItem();
		report.setReport_id(++gRN);
		report.setHome_node(node);
		report.setSize(size);
		report.setValue(value);
		return addReport(report);
	}
	
	synchronized public long addReport(ReportItem report) {
		//add a existing report received from other nodes
		this.getReportList().add(report);				
		return gRN;
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
	
	//record down the report ids of the nodes that is communicating with itself 
	public HashSet<Long> getNeighborReportIdList()
	{
		return neighborWantIdList;
	}
	public void setNeighborReportIdList(HashSet<Long> neighborWantIdList)
	{
		this.neighborWantIdList = neighborWantIdList;
	}
	
	public Vector<ReportItem> getReportList() {
		return ReportList;
	}
	public void setReportList(Vector<ReportItem> reportList) {
		ReportList = reportList;
	}

}
