package jist.swans.route;

import java.util.HashSet;

import driver.QueryBook;

public class MARKETMsg1
{
	private QueryBook querybook;
	private HashSet<Long> reportCanOffer;
	
	
	public MARKETMsg1(QueryBook querybook, HashSet<Long> reportCanOffer)
	{
		super();
		this.querybook = querybook;
		this.reportCanOffer = reportCanOffer;
	}

	public QueryBook getQuerybook()
	{
		return querybook;
	}

	public void setQuerybook(QueryBook querybook)
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
