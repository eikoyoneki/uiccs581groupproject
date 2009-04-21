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
	
	public void generateNewReport()
	{
		reportbook.addReport(this.selfId, querybook);
	}
	
	public void generateNewQuery()
	{
		querybook.addNewQuery(this.selfId);
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
	 * when B receive the msg1
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
		//store the query list from A
		querybook.setOtherQueryList(msg1.getQuerybook().getQueryList());
		
		HashSet<Long> iUnknow = new HashSet<Long>(); // IDSa - TSb
		iUnknow = msg1.getreportCanOffer();
		iUnknow.removeAll(reportbook.getTrackSet());
		msg2.setReportUnknown(iUnknow);
		
		HashSet<Long> iOffer = new HashSet<Long>();//IDSb - IDSa
		iOffer = reportbook.getReportIdList();
		iOffer.removeAll(msg1.getreportCanOffer());

		
		//querybook.updateBook(msg1.getQuerybook());

		return msg2;
		
	}
	
	
	
	
	/**
	 * after get msg2, A knows what B want and what B can offer
	 * first A go through B's query and update the hit information of its reports
	 * in the msg3, A first put the id's that A want from B
	 * then A put all the report that hit by B's query and also B want
	 * then if there is any space left, A put all the report that B want
	 * @param msg2
	 * @return
	 */
	public MARKETMsg3 sendMsg3(MARKETMsg2 msg2)
	{
		reportbook.setSelfunknowIdList(msg2.getreportCanOffer());
		reportbook.setNeighborWantIdList(msg2.getReportUnknown());
		//get the query book of B
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
	
	/**
	 * after receive the msg3 from A,
	 * B know what A want,
	 * first B go through A's query and update the hit information of its reports
	 * Then B put in the msg the report hit by A's query and also A want
	 * then if any space left, B put the rest of the report want by A
	 * @param msg3
	 * @return
	 */
	public MARKETMsg4 sendMsg4(MARKETMsg3 msg3)
	{
		MARKETMsg4 msg4 = new MARKETMsg4();
		reportbook.setNeighborWantIdList(msg3.getReportNeed());
		reportbook.getHitReport(querybook.getOtherQueryList());
		
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
		
		//update the query database
		querybook.updateBook();
		//merge the report
		reportbook.mergeReport(msg3.getAnswers());
		if(msg3.getBrokerReport().size() != 0)
			reportbook.mergeReport(msg3.getBrokerReport());

		return msg4;
		
	}
	
	/**
	 * after receive the msg from B
	 * A just merge the report
	 * @param msg4
	 */
	public void receiveMSg4(MARKETMsg4 msg4)
	{
		reportbook.mergeReport(msg4.getAnswers());
		if(msg4.getBrokerReport().size() != 0)
			reportbook.mergeReport(msg4.getBrokerReport());
		querybook.updateBook();
	}

	
	public MARKETADVMsg sendAdvMsg()
	{
		MARKETADVMsg advMsg = new MARKETADVMsg(reportbook.createAdvMsg(msgSize));
		return advMsg;
	}
	
	public MARKETREQMsg sendREQMsg(MARKETADVMsg advMsg)
	{
		MARKETREQMsg reqMsg = new MARKETREQMsg(reportbook.createREQMsg(advMsg.getAdvReportIds()));
		return reqMsg;
	}
	
	public MARKETRelayMsg sendRelayMsg(MARKETREQMsg reqMsg)
	{
		if(reqMsg.isReq())
		{
			MARKETRelayMsg relayMsg = new MARKETRelayMsg(reportbook.createRelayMsg());
			return relayMsg;
		}
		else
			return null;
	}
	
	public void receiveRelayMsg(MARKETRelayMsg relayMsg)
	{
		reportbook.mergeReport(relayMsg.getRelayReports());
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
	
	public NeighborTable getNeighbor()
	{
		return ntab_;
	}


}
