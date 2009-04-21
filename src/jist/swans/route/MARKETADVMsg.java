package jist.swans.route;

import java.util.Vector;

public class MARKETADVMsg
{
	private Vector<Long> advReportIds;

	public MARKETADVMsg(Vector<Long> advReportIds)
	{
		super();
		this.advReportIds = advReportIds;
	}

	public Vector<Long> getAdvReportIds()
	{
		return advReportIds;
	}

	public void setAdvReportIds(Vector<Long> advReportIds)
	{
		this.advReportIds = advReportIds;
	}
	
	
}
