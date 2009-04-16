package jist.swans.route;

import java.util.HashSet;

import driver.QueryBook;

public class MARKETMsg2
{
	private QueryBook querybook;
	private HashSet<Long> reportUnknown;
	private HashSet<Long> reportCanOffer;
	
	public MARKETMsg2()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param querybook
	 * @param reportCanOffer
	 * @param reportUnknown
	 */
	public MARKETMsg2(QueryBook querybook, HashSet<Long> reportCanOffer,
			HashSet<Long> reportUnknown)
	{
		super();
		this.querybook = querybook;
		this.reportCanOffer = reportCanOffer;
		this.reportUnknown = reportUnknown;
	}

	public QueryBook getQuerybook()
	{
		return querybook;
	}

	public void setQuerybook(QueryBook querybook)
	{
		this.querybook = querybook;
	}

	public HashSet<Long> getReportUnknown()
	{
		return reportUnknown;
	}

	public void setReportUnknown(HashSet<Long> reportUnknown)
	{
		this.reportUnknown = reportUnknown;
	}

	public HashSet<Long> getreportCanOffer()
	{
		return reportCanOffer;
	}

	public void setreportCanOffer(HashSet<Long> reportCanOffer)
	{
		this.reportCanOffer = reportCanOffer;
	}
	
	
	
}
