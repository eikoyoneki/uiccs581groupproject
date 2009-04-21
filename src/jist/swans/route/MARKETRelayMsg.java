package jist.swans.route;

import java.util.Vector;

import driver.ReportItem;

public class MARKETRelayMsg
{
	private Vector<ReportItem> relayReports;

	public MARKETRelayMsg(Vector<ReportItem> relayReports)
	{
		super();
		this.relayReports = relayReports;
	}

	public Vector<ReportItem> getRelayReports()
	{
		return relayReports;
	}

	public void setRelayReports(Vector<ReportItem> relayReports)
	{
		this.relayReports = relayReports;
	}
	
	
}
