package driver;

import java.util.Vector;

//the query database which stored in each RouteMARKTE node
//contain a vector of query
public class QueryDB
{
	private Vector<Query> queryDB = new Vector<Query>();
	private int routeMARKTEId = 0;
	
	public Vector<Query> getQueryDB()
	{
		return queryDB;
	}
	public void setQueryDB(Vector<Query> queryDB)
	{
		this.queryDB = queryDB;
	}
	public int getRouteMARKTEId()
	{
		return routeMARKTEId;
	}
	public void setRouteMARKTEId(int routeMARKTEId)
	{
		this.routeMARKTEId = routeMARKTEId;
	}
}
