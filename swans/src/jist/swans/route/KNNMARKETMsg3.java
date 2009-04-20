package jist.swans.route;

import java.util.HashSet;
import java.util.Vector;

import driver.KNNReportItem;

public class KNNMARKETMsg3
{
	private HashSet<Long> reportNeed;
	private Vector<KNNReportItem> answers;
	private Vector<KNNReportItem> brokerReport;
	
	public KNNMARKETMsg3()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public KNNMARKETMsg3(Vector<KNNReportItem> answers,
			Vector<KNNReportItem> brokerReport, HashSet<Long> reportNeed)
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
