package jist.swans.route;

import java.util.HashSet;
import java.util.Vector;

import driver.QueryBook;
import driver.QueryItem;
import driver.ReportBook;
import driver.ReportItem;
import jist.swans.field.Field;
import jist.swans.mac.MacAddress;
import jist.swans.misc.Message;
import jist.swans.net.NetAddress;
import jist.swans.route.geo.LocationDatabase;

//the node we use in our experiment
/**
 * @author Xiaowen
 *
 */
/**
 * @author Xiaowen
 *
 */
public class RouteMARKET extends RouteGPSR
{
	public RouteMARKET(Field field, int selfId, LocationDatabase ldb)
	{
		super(field, selfId, ldb);
		// TODO Auto-generated constructor stub
	}

	//store the queries and reports in a node
	private QueryBook querybook;
	private ReportBook reportbook;
	private final int msgSize = 10;

	
	
	public void queryLocalDB(QueryItem query)
	{
		
	}
	
	
	/**
	 * A send the first msg to the new neighbor B
	 * include all the query in the database
	 * and the reportId that in the report database
	 * whichi means what the node can offer
	 * @return
	 */
	public MARKETMsg1 sendMsg1()
	{
		MARKETMsg1 msg1 = new MARKETMsg1(querybook, reportbook.getReportIdList());
		return msg1;
	}
	
	/**
	 * when B reeive the msg1
	 * the node need to tell A all its quries
	 * and what B does not know, and what B can offer
	 * @param msg1
	 * @return
	 */
	public MARKETMsg2 sendMsg2(MARKETMsg1 msg1)
	{
		//do I have to query the report database here?
		//and also update my query database?
		MARKETMsg2 msg2 = new MARKETMsg2();
		msg2.setQuerybook(querybook);
		querybook.setOtherQueryList(msg1.getQuerybook().getQueryList());
		
		HashSet<Long> iUnknow = new HashSet<Long>();
		iUnknow = msg1.getreportCanOffer();
		iUnknow.removeAll(reportbook.getTrackSet());
		msg2.setReportUnknown(iUnknow);
		
		HashSet<Long> iOffer = new HashSet<Long>();
		iOffer = reportbook.getReportIdList();
		iOffer.removeAll(msg1.getreportCanOffer());

		
		//querybook.updateBook(msg1.getQuerybook());

		return msg2;
		
	}
	
	public MARKETMsg3 sendMsg3(MARKETMsg2 msg2)
	{
		reportbook.setSelfunknowIdList(msg2.getreportCanOffer());
		reportbook.setNeighborWantIdList(msg2.getReportUnknown());
		
		//get the query book of the other node
		querybook.setOtherQueryList(msg2.getQuerybook().getQueryList());
		
		
		MARKETMsg3 msg3 = new MARKETMsg3();
		msg3.setAnswers(reportbook.createAnswerMsg(msgSize, querybook.getOtherQueryList()));
		int size = 0;
		for(ReportItem report : msg3.getAnswers())
		{
			size += report.getSize();
		}
		if(size < msgSize)
		{
			msg3.setBrokerReport(reportbook.createBrokerMsg(msgSize - size));
		}
		else
			msg3.setBrokerReport(new Vector());

		return msg3;
	}
	
	public MARKETMsg4 sendMsg4(MARKETMsg3 msg3)
	{
		MARKETMsg4 msg4 = new MARKETMsg4();
		reportbook.setNeighborWantIdList(msg3.getReportNeed());
		
		msg4.setAnswers(reportbook.createAnswerMsg(msgSize, querybook.getOtherQueryList()));
		int size = 0;
		for(ReportItem report : msg3.getAnswers())
		{
			size += report.getSize();
		}
		if(size < msgSize)
		{
			msg4.setBrokerReport(reportbook.createBrokerMsg(msgSize - size));
		}
		else
			msg4.setBrokerReport(new Vector());

		return msg4;
		
	}
	
	public void receiveMSg4(MARKETMsg4 msg4)
	{
		reportbook.mergeReport(msg4.getAnswers());
	}

	public QueryBook getQuerybook()
	{
		return querybook;
	}

	public void setQuerybook(QueryBook querybook)
	{
		this.querybook = querybook;
	}

	public ReportBook getReportbook()
	{
		return reportbook;
	}

	public void setReportbook(ReportBook reportbook)
	{
		this.reportbook = reportbook;
	}
	
	


}
