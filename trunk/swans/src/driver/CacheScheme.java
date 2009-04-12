package driver;

import java.util.TreeMap;
import java.util.Vector;

import jist.swans.route.RouteMARKET;

public class CacheScheme
{
	public static void GRS(RouteMARKET node)
	{
		
	}
	public static void LRU1(ReportBook reportBook)
	{
		TreeMap<Integer,ReportItem> tempMap = new TreeMap<Integer,ReportItem>();
		for(ReportItem ri : reportBook.getReportList())
		{
			tempMap.put(ri.getNumOfOtherHit(), ri);
		}
		Vector<ReportItem> newList = new Vector<ReportItem>();
		for(int otherhit : tempMap.keySet())
		{
			newList.add(tempMap.get(otherhit));
		}
		reportBook.setReportList(newList);
	}
	public static void LRU2(ReportBook reportBook)
	{
		TreeMap<Integer,ReportItem> tempMap = new TreeMap<Integer,ReportItem>();
		for(ReportItem ri : reportBook.getReportList())
		{
			tempMap.put(ri.getNumOfOtherHit2(), ri);
		}
		Vector<ReportItem> newList = new Vector<ReportItem>();
		for(int otherhit : tempMap.keySet())
		{
			newList.add(tempMap.get(otherhit));
		}
		reportBook.setReportList(newList);
	}
	public static void LFU1(ReportBook reportBook)
	{
		TreeMap<Integer,ReportItem> tempMap = new TreeMap<Integer,ReportItem>();
		for(ReportItem ri : reportBook.getReportList())
		{
			tempMap.put(ri.getNumOfHit(), ri);
		}
		Vector<ReportItem> newList = new Vector<ReportItem>();
		for(int otherhit : tempMap.keySet())
		{
			newList.add(tempMap.get(otherhit));
		}
		reportBook.setReportList(newList);
	}
	public static void LFU2(ReportBook reportBook)
	{
		TreeMap<Integer,ReportItem> tempMap = new TreeMap<Integer,ReportItem>();
		for(ReportItem ri : reportBook.getReportList())
		{
			tempMap.put(ri.getNumOfHit2(), ri);
		}
		Vector<ReportItem> newList = new Vector<ReportItem>();
		for(int otherhit : tempMap.keySet())
		{
			newList.add(tempMap.get(otherhit));
		}
	}
}
