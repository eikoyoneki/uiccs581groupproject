package jist.swans.route;

import driver.QueryBook;
import driver.QueryItem;
import driver.ReportBook;
import jist.swans.field.Field;
import jist.swans.mac.MacAddress;
import jist.swans.misc.Message;
import jist.swans.net.NetAddress;
import jist.swans.route.geo.LocationDatabase;

//the node we use in our experiment
public class RouteMARKET extends RouteGPSR
{
	//store the queries and reports in a node
	private QueryBook querDB;
	private ReportBook reportDB;
	
	public QueryBook getQuerDB()
	{
		return querDB;
	}

	public void setQuerDB(QueryBook querDB)
	{
		this.querDB = querDB;
	}

	public ReportBook getReportDB()
	{
		return reportDB;
	}

	public void setReportDB(ReportBook reportDB)
	{
		this.reportDB = reportDB;
	}

	public RouteMARKET(Field field, int selfId, LocationDatabase ldb)
	{
		super(field, selfId, ldb);
		// TODO Auto-generated constructor stub
	}
	
	//
	public void receive(Message msg, NetAddress src, MacAddress lastHop,
            byte macId, NetAddress dst, byte priority, byte ttl) {
		
	}
	
	public void queryLocalDB(QueryItem query)
	{
		
	}
	
	
	


}
