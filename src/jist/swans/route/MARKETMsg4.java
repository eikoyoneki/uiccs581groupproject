package jist.swans.route;

import java.util.Vector;

import driver.ReportItem;

public class MARKETMsg4
{
	private Vector<ReportItem> answers;
	private Vector<ReportItem> brokerReport;
	
	
	public MARKETMsg4()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public MARKETMsg4(Vector<ReportItem> answers,
			Vector<ReportItem> brokerReport)
	{
		super();
		this.answers = answers;
		this.brokerReport = brokerReport;
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
