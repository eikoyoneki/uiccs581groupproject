//Wenxuan Gao, spring 2009
package driver;

import java.util.Iterator;
import java.util.Vector;

public class ReportBook {
	static private long gRN = 0;//global report number control
	private Vector<ReportItem> ReportList;
	private final int sizeLimit = 10;
	
	public ReportBook(){
		ReportList = new Vector<ReportItem>();
	}
	public ReportBook(ReportBook book){
		gRN = book.gRN;
		ReportList = book.getReportList();
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
		//add a existing report
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
						otherqi.increaseOtherHit();
				}
				
				qi.setNumOfOtherHit(0);
				qi.increaseHit();
			}
		}
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

}
