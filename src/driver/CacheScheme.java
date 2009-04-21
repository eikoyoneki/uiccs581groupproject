package driver;

import java.util.TreeMap;
import java.util.Vector;

import jist.swans.route.RouteMARKET;

public class CacheScheme
{
	public static void GRS(Vector<ReportItem> reports)
	{
		TreeMap<Double,Vector<ReportItem>> tempMap = new TreeMap<Double,Vector<ReportItem>>();
		for(ReportItem ri : reports)
		{
			Vector<ReportItem> sameRankReport = tempMap.get(ri);
			if(sameRankReport == null)
				tempMap.put((double)(ri.getDemand() * (ri.getSupply()))/(double)ri.getSize(), new Vector<ReportItem>());
			sameRankReport.add(ri);
		}
		reports.clear();
		for(Double grs : tempMap.keySet())
		{
			//the priority is higher if the value is smaller
			for(ReportItem report : tempMap.get(grs))
				reports.add(report);
		}
		
	}
	
	public static void LRU1(Vector<ReportItem> reports)
	{
		TreeMap<Double,Vector<ReportItem>> tempMap = new TreeMap<Double,Vector<ReportItem>>();
		for(ReportItem ri : reports)
		{
			Vector<ReportItem> sameRankReport = tempMap.get(ri);
			if(sameRankReport == null)
				tempMap.put((double)(ri.getNumOfOtherHit())/(double)ri.getSize(), new Vector<ReportItem>());
			sameRankReport.add(ri);
		}
		reports.clear();
		for(Double grs : tempMap.keySet())
		{
			//the priority is higher if the value is smaller
			for(ReportItem report : tempMap.get(grs))
				reports.add(report);
		}
	
	}
	
	public static void LRU2(Vector<ReportItem> reports)
	{
		TreeMap<Double,Vector<ReportItem>> tempMap = new TreeMap<Double,Vector<ReportItem>>();
		for(ReportItem ri : reports)
		{
			Vector<ReportItem> sameRankReport = tempMap.get(ri);
			if(sameRankReport == null)
				tempMap.put((double)ri.getNumOfOtherHit2()/(double)ri.getSize(), new Vector<ReportItem>());
			sameRankReport.add(ri);
		}
		reports.clear();
		for(Double grs : tempMap.keySet())
		{
			//the priority is higher if the value is smaller
			for(ReportItem report : tempMap.get(grs))
				reports.add(report);
		}
	}
	
	
	public static void LFU1(Vector<ReportItem> reports)
	{
		TreeMap<Double,Vector<ReportItem>> tempMap = new TreeMap<Double,Vector<ReportItem>>();
		for(ReportItem ri : reports)
		{
			Vector<ReportItem> sameRankReport = tempMap.get(ri);
			if(sameRankReport == null)
				tempMap.put(ri.getFreq()/ri.getSize(), new Vector<ReportItem>());
			sameRankReport.add(ri);
		}
		reports.clear();
		for(Double grs : tempMap.descendingKeySet())
		{
			//the priority is higher if the value is smaller
			for(ReportItem report : tempMap.get(grs))
				reports.add(report);
		}
		
		
	}
	
	
	public static void LFU2(Vector<ReportItem> reports)
	{
		TreeMap<Double,Vector<ReportItem>> tempMap = new TreeMap<Double,Vector<ReportItem>>();
		for(ReportItem ri : reports)
		{
			Vector<ReportItem> sameRankReport = tempMap.get(ri);
			if(sameRankReport == null)
				tempMap.put(ri.getFreq2()/ri.getSize(), new Vector<ReportItem>());
			sameRankReport.add(ri);
		}
		reports.clear();
		for(Double grs : tempMap.descendingKeySet())
		{
			//the priority is higher if the value is smaller
			for(ReportItem report : tempMap.get(grs))
				reports.add(report);
		}
		
		
	}
}
