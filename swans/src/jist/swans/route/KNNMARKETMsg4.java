package jist.swans.route;

import java.util.Vector;

import driver.KNNReportItem;

public class KNNMARKETMsg4
{
	private Vector<KNNReportItem> answers;
	private Vector<KNNReportItem> brokerReport;
	
	
	public KNNMARKETMsg4()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public KNNMARKETMsg4(Vector<KNNReportItem> answers,
			Vector<KNNReportItem> brokerReport)
	{
		super();
		this.answers = answers;
		this.brokerReport = brokerReport;
	}
	public Vector<KNNReportItem> getAnswers()
	{
		return answers;
	}
	public void setAnswers(Vector<KNNReportItem> answers)
	{
		this.answers = answers;
	}
	public Vector<KNNReportItem> getBrokerReport()
	{
		return brokerReport;
	}
	public void setBrokerReport(Vector<KNNReportItem> brokerReport)
	{
		this.brokerReport = brokerReport;
	}
	
	
}
