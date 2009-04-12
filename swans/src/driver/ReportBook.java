//Wenxuan Gao, spring 2009
package driver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

public class ReportBook {
	static private long gRN = 0;//global report number control
	private Vector<ReportItem> ReportList;
	private final int sizeLimit = 10;
	private HashSet<Long> neighborReportIdList = new HashSet<Long>();
	
	

	public ReportBook(){
		ReportList = new Vector<ReportItem>();
	}
	public ReportBook(ReportBook book){
		gRN = book.gRN;
		ReportList = book.getReportList();
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
						if(!neighborReportIdList.contains(reportId))
							otherqi.increaseOtherHit2();
					}
				}
				
				qi.setNumOfOtherHit(0);
				qi.increaseHit();
				if(!neighborReportIdList.contains(reportId))
				{
					qi.setNumOfOtherHit2(0);
					qi.increaseHit2();
				}
			}
		}
	}
	
	//create a msg body which contains the current report in the node
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
	
	//add the reports received from other nodes
	//how to rank the new reports added?
	public void addReport(Vector<ReportItem> reportSet)
	{
		
	}
	
	public void rankReport()
	{
		CacheScheme.LRU1(this);
	}
	
	
	synchronized public boolean delReport(ReportItem r){
		long rid= r.getReport_id();
		return delReport(rid);		
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
		return neighborReportIdList;
	}
	public void setNeighborReportIdList(HashSet<Long> neighborReportIdList)
	{
		this.neighborReportIdList = neighborReportIdList;
	}
	
	public Vector<ReportItem> getReportList() {
		return ReportList;
	}
	public void setReportList(Vector<ReportItem> reportList) {
		ReportList = reportList;
	}

}
