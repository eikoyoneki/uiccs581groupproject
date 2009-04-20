package driver;

import java.util.*;


public class KNNQueryBook {
	
	static private long gQN = 0;//global Query number control
	private LinkedList<KNNQueryItem> QueryList;
	private final int sizeLimit = 1; 
	private HashSet<Long> queryIdSet = new HashSet<Long>();
	private LinkedList<KNNQueryItem> otherQueryList;//queylist get from other node in 
	
	public KNNQueryBook(int node,double x, double y){
		QueryList = new LinkedList<KNNQueryItem>();
		addNewQuery(node,x,y);
	}
	public KNNQueryBook(KNNQueryBook book){
		gQN = book.gQN;
		QueryList = book.getQueryList();
	}

	
	public void updateBook(KNNQueryBook queryfromOther)
	{
		HashSet<KNNQueryItem> queryneedSet = new HashSet();
		for(KNNQueryItem query : queryfromOther.getQueryList())
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
		for(KNNQueryItem query : queryneedSet)
			this.addLast(query);
	}
	
	public void updateBook()
	{
		HashSet<KNNQueryItem> queryneedSet = new HashSet();
		for(KNNQueryItem query : otherQueryList)
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
		for(KNNQueryItem query : queryneedSet)
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
	synchronized public void addLast(KNNQueryItem q){
		this.getQueryList().addLast(q);		
		this.queryIdSet.add(q.getQuery_id());
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
	
	public boolean isQueryExisting(long q_id) {
		// 
		KNNQueryItem KNNQueryItem = null;
		Iterator<KNNQueryItem> it=null;
		it = this.getQueryList().iterator();
		while(it.hasNext())
		{
			KNNQueryItem =  it.next();

			if(KNNQueryItem.getQuery_id() == q_id)
			{
				return true;				
			}
		}		
		return false;
	}
	
	public boolean isQueryExisting(KNNQueryItem q) {
		long qid=q.getQuery_id();
		return isQueryExisting(qid);
	}
	
	public static long getGQN() {
		return gQN;
	}
	public static void setGQN(long gqn) {
		gQN = gqn;
	}
	public LinkedList<KNNQueryItem> getQueryList() {
		return QueryList;
	}
	public void setQueryList(LinkedList<KNNQueryItem> queryList) {
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
	public LinkedList<KNNQueryItem> getOtherQueryList()
	{
		return otherQueryList;
	}
	public void setOtherQueryList(LinkedList<KNNQueryItem> otherQueryList)
	{
		this.otherQueryList = otherQueryList;
	}

	
	
}

