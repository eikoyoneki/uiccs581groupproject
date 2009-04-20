package jist.swans.route;

import java.util.HashSet;

import driver.KNNQueryBook;

public class KNNMARKETMsg1
{
	private KNNQueryBook querybook;
	private HashSet<Long> reportCanOffer;
	
	
	public KNNMARKETMsg1(KNNQueryBook querybook, HashSet<Long> reportCanOffer)
	{
		super();
		this.querybook = querybook;
		this.reportCanOffer = reportCanOffer;
	}

	public KNNQueryBook getQuerybook()
	{
		return querybook;
	}

	public void setQuerybook(KNNQueryBook querybook)
	{
		this.querybook = querybook;
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
