package driver;

import java.util.TreeMap;
import java.util.Vector;

public class KNNCacheScheme {
	public static void GRS(Vector<KNNReportItem> reports)
	{
		TreeMap<Double,KNNReportItem> tempMap = new TreeMap<Double,KNNReportItem>();
		for(KNNReportItem ri : reports)
		{
			tempMap.put((double)(ri.getDemand() * (1 - ri.getSupply()))/(double)ri.getSize(), ri);
		}
		reports.clear();
		for(Double grs : tempMap.keySet())
		{
			//the priority is higher if the value is smaller
			reports.add(tempMap.get(grs));
		}
		
	}
	
	public static void LRU1(Vector<KNNReportItem> reports)
	{
		TreeMap<Double,KNNReportItem> tempMap = new TreeMap<Double,KNNReportItem>();
		for(KNNReportItem ri : reports)
		{
			tempMap.put((double)(ri.getNumOfOtherHit())/(double)ri.getSize(), ri);
		}
		reports.clear();
		for(Double otherhit : tempMap.keySet())
		{
			//the priority is higher if the value is smaller
			reports.add(tempMap.get(otherhit));
		}
		
	}
	
	public static void LRU2(Vector<KNNReportItem> reports)
	{
		TreeMap<Double,KNNReportItem> tempMap = new TreeMap<Double,KNNReportItem>();
		for(KNNReportItem ri : reports)
		{
			tempMap.put((double)ri.getNumOfOtherHit2()/(double)ri.getSize(), ri);
		}
		reports.clear();
		for(Double otherhit : tempMap.keySet())
		{
			reports.add(tempMap.get(otherhit));
		}
	}
	
	
	public static void LFU1(Vector<KNNReportItem> reports)
	{
		TreeMap<Double,KNNReportItem> tempMap = new TreeMap<Double,KNNReportItem>();
		for(KNNReportItem ri : reports)
		{
			tempMap.put(ri.getFreq()/ri.getSize(), ri);
		}
		reports.clear();
		
		for(Double hit : tempMap.descendingKeySet())
		{
			//the priority is higher if the value is larger
			reports.add(tempMap.get(hit));
		}
		
	}
	
	
	public static void LFU2(Vector<KNNReportItem> reports)
	{
		TreeMap<Double,KNNReportItem> tempMap = new TreeMap<Double,KNNReportItem>();
		for(KNNReportItem ri : reports)
		{
			tempMap.put(ri.getFreq2()/ri.getSize(), ri);
		}
		reports.clear();
		for(Double hit : tempMap.descendingKeySet())
		{
			reports.add(tempMap.get(hit));
		}
		
	}

}
