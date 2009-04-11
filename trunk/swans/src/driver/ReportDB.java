package driver;

import java.util.Vector;

//the report database which stored in each RouteMARKTE node
//contain a vector of report
public class ReportDB
{
	private Vector<Report> reportDB = new Vector<Report>();
	private int routeMARKTEId = 0;
	
	public Vector<Report> getReportDB()
	{
		return reportDB;
	}
	public void setReportDB(Vector<Report> reportDB)
	{
		this.reportDB = reportDB;
	}
	public int getRouteMARKTEId()
	{
		return routeMARKTEId;
	}
	public void setRouteMARKTEId(int routeMARKTEId)
	{
		this.routeMARKTEId = routeMARKTEId;
	}
}
