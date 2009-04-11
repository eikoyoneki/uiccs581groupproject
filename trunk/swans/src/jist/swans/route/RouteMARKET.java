package jist.swans.route;

import driver.Query;
import driver.QueryDB;
import driver.ReportDB;
import jist.swans.field.Field;
import jist.swans.mac.MacAddress;
import jist.swans.misc.Message;
import jist.swans.net.NetAddress;
import jist.swans.route.geo.LocationDatabase;

//the node we use in our experiment
public class RouteMARKET extends RouteGPSR
{
	private QueryDB querDB;
	private ReportDB reportDB;
	
	
	
	public QueryDB getQuerDB()
	{
		return querDB;
	}

	public void setQuerDB(QueryDB querDB)
	{
		this.querDB = querDB;
	}

	public ReportDB getReportDB()
	{
		return reportDB;
	}

	public void setReportDB(ReportDB reportDB)
	{
		this.reportDB = reportDB;
	}

	public RouteMARKET(Field field, int selfId, LocationDatabase ldb)
	{
		super(field, selfId, ldb);
		// TODO Auto-generated constructor stub
	}
	
	public void receive(Message msg, NetAddress src, MacAddress lastHop,
            byte macId, NetAddress dst, byte priority, byte ttl) {
		
	}
	
	public void queryLocalDB(Query query)
	{
		
	}
	

}
