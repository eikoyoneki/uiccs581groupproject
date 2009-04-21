package driver;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class GlobalDB
{
	private static HashMap<Long,ReportItem> globalReportDB = new HashMap<Long,ReportItem>();
	private static HashMap<Long,QueryItem> globalQueryDB = new HashMap<Long,QueryItem>();
	//queryid, query hit by how many different reports in real communication process
	private static HashMap<Long, HashSet<Long>> queryAnswerMap = new HashMap<Long, HashSet<Long>>();
	//query should be answered by how many reports
	private static HashMap<Long, HashSet<Long>> queryIdealAnswerMap = new HashMap<Long, HashSet<Long>>();
	
	
	public static void addReport(ReportItem report)
	{
		globalReportDB.put(report.getReport_id(), report);
		for(long queryId : globalQueryDB.keySet())
		{
			if(match(report, globalQueryDB.get(queryId)))
				queryIdealAnswerMap.get(queryId).add(report.getReport_id());
		}
		
	}
	
	public static void addQuery(QueryItem query)
	{
		globalQueryDB.put(query.getQuery_id(), query);
		queryIdealAnswerMap.put(query.getQuery_id(), new HashSet<Long>());
		queryAnswerMap.put(query.getQuery_id(), new HashSet<Long>());
		for(long reportId : globalReportDB.keySet())
		{
			if(match(globalReportDB.get(reportId), query))
				queryIdealAnswerMap.get(query.getQuery_id()).add(reportId);
		}
	}
	
	
	public static boolean match(ReportItem r, QueryItem q)
	{
		if (r.getValue() <= (q.getCenter() + q.getRange())
				&& r.getValue() >= (q.getCenter() - q.getRange()))
			return true;
		else
			return false;
	}
	
	public static void addMatchPair(QueryItem query, ReportItem report)
	{
		queryAnswerMap.get(query.getQuery_id()).add(report.getReport_id());
	}
	
	public static void computeResult()
	{
		long throughput = 0;
		for(long queryId : queryAnswerMap.keySet())
			throughput += queryAnswerMap.get(queryId).size();
		System.out.println("The throught put is " + throughput);
		long recall = 0;
		for(long queryId : queryIdealAnswerMap.keySet())
			recall += queryIdealAnswerMap.get(queryId).size();
		System.out.println("The ideal throughtput is " + recall);
		System.out.println("The recall is " + (double)throughput / (double)recall);
	}
}
