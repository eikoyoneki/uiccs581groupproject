//Wenxuan Gao  spring 2009
package driver;

import java.util.*;


public class QueryBook {
	
	static private long gQN = 0;//global Query number control
	protected LinkedList<QueryItem> QueryList;
	protected final int sizeLimit = JistExperiment.queryBookSize; 
	protected HashSet<Long> queryIdSet = new HashSet<Long>();
	protected LinkedList<QueryItem> otherQueryList;//queylist get from other node in 
	
	public QueryBook(int node){
		QueryList = new LinkedList<QueryItem>();
		addNewQuery(node);
	}
	public QueryBook(QueryBook book){
		gQN = book.gQN;
		QueryList = book.getQueryList();
	}

	
	public void updateBook(QueryBook queryfromOther)
	{
		HashSet<QueryItem> queryneedSet = new HashSet();
		for(QueryItem query : queryfromOther.getQueryList())
		{
			if(!queryIdSet.contains(query.getQuery_id()))
				queryneedSet.add(query);
		}
		int spaceneed = this.getSize() + queryneedSet.size() - sizeLimit;
		if(spaceneed > 0)
		{
			for(int i = 0 ; i < spaceneed; ++i)
				this.delFirst();
		}
		for(QueryItem query : queryneedSet)
			this.addLast(query);
	}
	
	public void updateBook()
	{
		HashSet<QueryItem> queryneedSet = new HashSet();
		for(QueryItem query : otherQueryList)
		{
			if(!queryIdSet.contains(query.getQuery_id()))
				queryneedSet.add(query);
		}
		int spaceneed = this.getSize() + queryneedSet.size() - sizeLimit;
		if(spaceneed > 0)
		{
			for(int i = 0 ; i < spaceneed; ++i)
				this.delFirst();
		}
		for(QueryItem query : queryneedSet)
			this.addLast(query);
	}
	
	
	public int getSize()
	{
		int size = 0;
		size = QueryList.size();
		return size;
	}
	
	//FIFO, delete the first item
	synchronized public void delFirst(){

		this.queryIdSet.remove(this.QueryList.getFirst().getQuery_id());
		this.getQueryList().removeFirst();	
		
	}
	
	
	
	//FIFO, add new item to the tail
	synchronized public void addLast(QueryItem q){
		this.getQueryList().addLast(q);		
		this.queryIdSet.add(q.getQuery_id());
	}
	
	//when globaly generate a query, we can use this method to add the query to the node
	//this is the only method should use to generate new query
	public void addNewQuery(int node)
	{
		QueryItem query = new QueryItem(++gQN, node);
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
	
	public boolean isQueryExisting(long q_id) {
		// 
		QueryItem queryItem = null;
		Iterator<QueryItem> it=null;
		it = this.getQueryList().iterator();
		while(it.hasNext())
		{
			queryItem =  it.next();

			if(queryItem.getQuery_id() == q_id)
			{
				return true;				
			}
		}		
		return false;
	}
	
	public boolean isQueryExisting(QueryItem q) {
		long qid=q.getQuery_id();
		return isQueryExisting(qid);
	}
	
	public static long getGQN() {
		return gQN;
	}
	public static void setGQN(long gqn) {
		gQN = gqn;
	}
	public LinkedList<QueryItem> getQueryList() {
		return QueryList;
	}
	public void setQueryList(LinkedList<QueryItem> queryList) {
		QueryList = queryList;
	}
	public HashSet<Long> getQueryIdSet()
	{
		return queryIdSet;
	}
	public void setQueryIdSet(HashSet<Long> queryIdSet)
	{
		this.queryIdSet = queryIdSet;
	}
	public int getSizeLimit()
	{
		return sizeLimit;
	}
	public LinkedList<QueryItem> getOtherQueryList()
	{
		return otherQueryList;
	}
	public void setOtherQueryList(LinkedList<QueryItem> otherQueryList)
	{
		this.otherQueryList = otherQueryList;
	}

	
	
}
