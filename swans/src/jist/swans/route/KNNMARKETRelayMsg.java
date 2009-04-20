package jist.swans.route;

import java.util.Vector;

import driver.KNNReportItem;

public class KNNMARKETRelayMsg
{
	private Vector<KNNReportItem> relayReports;

	public KNNMARKETRelayMsg(Vector<KNNReportItem> relayReports)
	{
		super();
		this.relayReports = relayReports;
	}

	public Vector<KNNReportItem> getRelayReports()
	{
		return relayReports;
	}

	public void setRelayReports(Vector<KNNReportItem> relayReports)
	{
		this.relayReports = relayReports;
	}
	
	
}
