package driver;

import java.util.Calendar;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.Vector;

public class MALENA
{
	public class SupplyIV
	{
		long reportId;
		Calendar reportTime;
		int timeEncounteratNeighbor;
		boolean fresh;
		int age = 0;
		
		public SupplyIV(ReportItem report, boolean fresh)
		{
			reportId = report.getReport_id();
			reportTime = report.getCreateTime();
			timeEncounteratNeighbor = report.getTimeEncounteratNeighbor();
			this.fresh = fresh;
		}
		
		public SupplyIV(KNNReportItem report, boolean fresh)
		{
			reportId = report.getReport_id();
			reportTime = report.getCreateTime();
			timeEncounteratNeighbor = report.getTimeEncounteratNeighbor();
			this.fresh = fresh;
		}

		public SupplyIV(boolean fresh, long reportId, Calendar reportTime,
				int timeEncounteratNeighbor)
		{
			super();
			this.fresh = fresh;
			this.reportId = reportId;
			this.reportTime = reportTime;
			this.timeEncounteratNeighbor = timeEncounteratNeighbor;
		}
		
		
	}
	
	public class Example
	{
		int age;
		int fin;
		int totalrecord;
		int newrecord;
		public Example()
		{
			super();
			// TODO Auto-generated constructor stub
		}
		public Example(int age, int fin, int newrecord, int totalrecord)
		{
			super();
			this.age = age;
			this.fin = fin;
			this.newrecord = newrecord;
			this.totalrecord = totalrecord;
		}
	}
	
	private Vector<Example> trainData = new Vector<Example>();
	//the older one is in the front in this map
	private TreeMap<Calendar,SupplyIV> trainExample = new TreeMap<Calendar,SupplyIV>();
	
	public void bayesianTrain(ReportBook reportbook)
	{
		for(Calendar c : trainExample.keySet())
		{
			for(Example example : trainData)
			{
				if(trainExample.get(c).age == example.age && trainExample.get(c).timeEncounteratNeighbor == example.fin)
				{
					for(ReportItem report : reportbook.getReportList())
					{
						if(report.getReport_id() == trainExample.get(c).reportId)
							report.setSupply((double)example.newrecord / (double)example.totalrecord);
					}
				}
			}
		}
	}
	
	public void normalizeExample()
	{
		HashSet<Integer> tdiff = new HashSet<Integer>();
		
		for(Calendar c : trainExample.keySet())
		{
			tdiff.add(trainExample.get(c).timeEncounteratNeighbor);
			
		}
		int num = tdiff.size() + 1;
		int div = trainExample.size();
		div = div / num;
		int i = 0;
		int age = 0;
		for(Calendar c : trainExample.keySet())
		{
			int fin = trainExample.get(c).timeEncounteratNeighbor;
			
			boolean exist = false;
			for(Example ex : trainData)
			{
				if(ex.age == age && ex.fin == fin)
				{
					if(trainExample.get(c).fresh)
						ex.newrecord++;
					ex.totalrecord++;
					exist = true;
				}
				
			}
			if(!exist)
			{
				int newex = 0;
				if(trainExample.get(c).fresh)
					newex = 1;
				Example ex = new Example(age, fin, newex, 1 );
				trainData.add(ex);
			}
			trainExample.get(c).age = age;
			i++;
			if(i % div == 0)
			{
				age++;
			}
			
			
		}
	}
	
	
	public void bayesianTrain(KNNReportBook reportbook)
	{
		for(Calendar c : trainExample.keySet())
		{
			for(Example example : trainData)
			{
				if(trainExample.get(c).age == example.age && trainExample.get(c).timeEncounteratNeighbor == example.fin)
				{
					for(KNNReportItem report : reportbook.getReportList())
					{
						if(report.getReport_id() == trainExample.get(c).reportId)
							report.setSupply((double)example.newrecord / (double)example.totalrecord);
					}
				}
			}
		}
	}
	

	public void addIV(ReportItem report, boolean fresh)
	{
		trainExample.put(report.getCreateTime(), new SupplyIV(report,fresh));
	}
	
	public void addIV(KNNReportItem report, boolean fresh)
	{
		trainExample.put(report.getCreateTime(), new SupplyIV(report,fresh));
	}
	
	
	
	
}
