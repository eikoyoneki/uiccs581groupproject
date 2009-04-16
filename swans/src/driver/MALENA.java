package driver;

import java.util.Vector;

public class MALENA
{
	public class SupplyIV
	{
		long reportId;
		int age;
		int timeEncounteratNeighbor;
		boolean fresh;
		
		public SupplyIV(ReportItem report, boolean fresh)
		{
			reportId = report.getReport_id();
			age = report.getAge();
			timeEncounteratNeighbor = report.getTimeEncounteratNeighbor();
			this.fresh = fresh;
		}
		
		public SupplyIV(KNNReportItem report, boolean fresh)
		{
			reportId = report.getReport_id();
			age = report.getAge();
			timeEncounteratNeighbor = report.getTimeEncounteratNeighbor();
			this.fresh = fresh;
		}
	}
	
	private Vector<SupplyIV> trainExample = new Vector<SupplyIV>();
	
	public void bayesianTrain(ReportBook reportbook)
	{
		
	}
	
	public void bayesianTrain(KNNReportBook reportbook)
	{
		
	}

	public void addIV(ReportItem report, boolean fresh)
	{
		trainExample.add(new SupplyIV(report,fresh));
	}
	
	public void addIV(KNNReportItem report, boolean fresh)
	{
		trainExample.add(new SupplyIV(report,fresh));
	}
	
	
	public Vector<SupplyIV> getTrainExample()
	{
		return trainExample;
	}

	public void setTrainExample(Vector<SupplyIV> trainExample)
	{
		this.trainExample = trainExample;
	}
	
	
	
}
