//Wenxuan Gao  spring 2009
package driver;

import java.util.*;


public class QueryBook {
	static private long gQN = 0;//global Query number control
	private LinkedList<QueryItem> QueryList;
	private final int sizeLimit = 10; 
	
	public QueryBook(){
		QueryList = new LinkedList<QueryItem>();
	}
	public QueryBook(QueryBook book){
		gQN = book.gQN;
		QueryList = book.getQueryList();
	}

	
	synchronized public long addQuery(int node, double center, double range){
		//add a new query
		//Every new query have to be added using this method
		//to ensure its id is unique
		QueryItem query = new QueryItem();
		query.setQuery_id(++gQN);
		query.setQuery(center, range);
		return addQuery(query);
	}
	
	synchronized public long addQuery(QueryItem q){
		//add a existing query
		this.getQueryList().add(q);				
		return gQN;
	}
	
	
	synchronized public boolean delQuery(long q_id ){
		//delete an query
		QueryItem queryItem = null;
		Iterator<QueryItem> it=null;
		it = this.getQueryList().iterator();
		while(it.hasNext())
		{
			queryItem =  it.next();

			if(queryItem.getQuery_id() == q_id)
			{
				//this is the order we should remove
				it.remove();
				return true;
			}
		}
		return false;
	}
	
	synchronized public boolean delQuery(QueryItem q){
		long qid= q.getQuery_id();
		return delQuery(qid);
		
	}
	
	//FIFO, delete the first item
	synchronized public void delFirst(){
		this.getQueryList().removeFirst();		
	}
	
	
	//FIFO, add new item to the tail
	synchronized public void addLast(QueryItem q){
		this.getQueryList().addLast(q);		
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

}
