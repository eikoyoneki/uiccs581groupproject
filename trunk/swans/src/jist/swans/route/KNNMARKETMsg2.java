package jist.swans.route;

import java.util.HashSet;

import driver.KNNQueryBook;

public class KNNMARKETMsg2
{
	private KNNQueryBook querybook;
	private HashSet<Long> reportUnknown;
	private HashSet<Long> reportCanOffer;
	
	public KNNMARKETMsg2()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param querybook
	 * @param reportCanOffer
	 * @param reportUnknown
	 */
	public KNNMARKETMsg2(KNNQueryBook querybook, HashSet<Long> reportCanOffer,
			HashSet<Long> reportUnknown)
	{
		super();
		this.querybook = querybook;
		this.reportCanOffer = reportCanOffer;
		this.reportUnknown = reportUnknown;
	}

	public KNNQueryBook getQuerybook()
	{
		return querybook;
	}

	public void setQuerybook(KNNQueryBook querybook)
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
