package jist.swans.route;

import java.util.HashSet;
import java.util.Vector;

import driver.ReportItem;

public class MARKETMsg3
{
	private HashSet<Long> reportNeed;
	private Vector<ReportItem> answers;
	private Vector<ReportItem> brokerReport;
	
	public MARKETMsg3()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MARKETMsg3(Vector<ReportItem> answers,
			Vector<ReportItem> brokerReport, HashSet<Long> reportNeed)
	{
		super();
		this.answers = answers;
		this.brokerReport = brokerReport;
		this.reportNeed = reportNeed;
	}
	
	public HashSet<Long> getReportNeed()
	{
		return reportNeed;
	}
	public void setReportNeed(HashSet<Long> reportNeed)
	{
		this.reportNeed = reportNeed;
	}
	public Vector<ReportItem> getAnswers()
	{
		return answers;
	}
	public void setAnswers(Vector<ReportItem> answers)
	{
		this.answers = answers;
	}
	public Vector<ReportItem> getBrokerReport()
	{
		return brokerReport;
	}
	public void setBrokerReport(Vector<ReportItem> brokerReport)
	{
		this.brokerReport = brokerReport;
	}
	
	
}
