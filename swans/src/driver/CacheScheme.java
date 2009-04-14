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
		TreeMap<Double,ReportItem> tempMap = new TreeMap<Double,ReportItem>();
		for(ReportItem ri : reportBook.getReportList())
		{
			tempMap.put((double)(ri.getNumOfOtherHit())/(double)ri.getSize(), ri);
		}
		Vector<ReportItem> newList = new Vector<ReportItem>();
		for(Double otherhit : tempMap.keySet())
		{
			//the priority is higher if the value is smaller
			newList.add(tempMap.get(otherhit));
		}
		reportBook.setReportList(newList);
	}
	
	public static void LRU2(ReportBook reportBook)
	{
		TreeMap<Double,ReportItem> tempMap = new TreeMap<Double,ReportItem>();
		for(ReportItem ri : reportBook.getReportList())
		{
			tempMap.put((double)ri.getNumOfOtherHit2()/(double)ri.getSize(), ri);
		}
		Vector<ReportItem> newList = new Vector<ReportItem>();
		for(Double otherhit : tempMap.keySet())
		{
			newList.add(tempMap.get(otherhit));
		}
		reportBook.setReportList(newList);
	}
	
	
	public static void LFU1(ReportBook reportBook)
	{
		TreeMap<Double,ReportItem> tempMap = new TreeMap<Double,ReportItem>();
		for(ReportItem ri : reportBook.getReportList())
		{
			tempMap.put(ri.getFreq()/ri.getSize(), ri);
		}
		Vector<ReportItem> newList = new Vector<ReportItem>();
		
		for(Double otherhit : tempMap.descendingKeySet())
		{
			//the priority is higher if the value is larger
			newList.add(tempMap.get(otherhit));
		}
		reportBook.setReportList(newList);
	}
	
	
	public static void LFU2(ReportBook reportBook)
	{
		TreeMap<Double,ReportItem> tempMap = new TreeMap<Double,ReportItem>();
		for(ReportItem ri : reportBook.getReportList())
		{
			tempMap.put(ri.getFreq2()/ri.getSize(), ri);
		}
		Vector<ReportItem> newList = new Vector<ReportItem>();
		for(Double otherhit : tempMap.descendingKeySet())
		{
			newList.add(tempMap.get(otherhit));
		}
	}
}
