package driver;

import java.util.*;


public class KNNQueryBook extends QueryBook{
	
	
	private final int sizeLimit = 1; 	
	//protected LinkedList<KNNQueryItem> QueryList;
	
	public KNNQueryBook(int node,double x, double y){
		super(node);
		QueryList = new LinkedList();
		addNewQuery(node,x,y);
	}
	

	


	
	public int getSize()
	{
		int size = 0;
		size = QueryList.size();
		return size;
	}
	
	
	//when globaly generate a query, we can use this method to add the query to the node
	//this is the only method should use to generate new query
	public void addNewQuery(int node, double x, double y)
	{
		KNNQueryItem query = new KNNQueryItem(++gQN, node, x, y);
		if(QueryList.size() == sizeLimit)
		{
			delFirst();
			this.getQueryList().addLast(query);		
			this.queryIdSet.add(query.getQuery_id());
		}
		else
		{
			this.getQueryList().addLast(query);		
			this.queryIdSet.add(query.getQuery_id());
		}
		
	}

	public int getSizeLimit()
	{
		return sizeLimit;
	}	
	
}

