package driver;

import java.util.*;

import jist.swans.misc.Location;
import jist.swans.route.*;
import jist.swans.route.RouteGPSR.*;

public class NeighborHistory 
{
	public static int size;
	public static Vector nodes;
	public static Vector neighborHistory;
	public static Vector neighborCurrent;
	
	public static void init(int s)
	{
		size = s;
		nodes = new Vector(size);
		neighborHistory = new Vector(size);
		neighborCurrent = new Vector(size);
		for(int i = 0; i < size; i++)
		{
			nodes.add(null);
			neighborHistory.add(null);
			neighborCurrent.add(null);
		}
	}
	
	public static void currentToHistory()
	{
		//neighborHistory = neighborCurrent;
		for(int i = 0; i < size; i++)
		{
			Vector currN = (Vector)neighborCurrent.get(i);
			Vector histN = new Vector();
			for(int j = 0; j < currN.size(); j++)
			{
				int temp = (Integer)currN.get(j);
				histN.add(temp);
			}
			neighborHistory.set(i, histN);
		}
	}
	
	public static void updateNodes(int i, RouteGPSR rm)
	{
		nodes.set(i, rm);
	}
	
	public static void updateCurrentNeighbor()
	{
		for(int i = 0; i < size; i++)
		{
			// add the neighbors for the ith node
			Vector nb = new Vector();
			for(int j = 0; j < size; j++)
			{
				if(i == j) continue;
				Location li = ((RouteGPSR)nodes.get(i)).getCurrentLocation();
				Location lj = ((RouteGPSR)nodes.get(j)).getCurrentLocation();
				if(distance(li, lj) < JistExperiment.market_radius)
				{
					nb.add(j);
				}
			}
			neighborCurrent.set(i, nb);
		}
	}
	
	public static Vector findNewNeighbor(int pos)
	{
		Vector result = new Vector();
		Vector cur = (Vector)neighborCurrent.get(pos);
		Vector his = (Vector)neighborHistory.get(pos);
		if(his == null || his.size() == 0) return cur;
		for(int i = 0; i < cur.size(); i++)
		{
			if(!his.contains(cur.get(i)))
			{
				result.add(cur.get(i));
			}
		}
		return result;
	}
	
	public static double distance(Location i, Location j)
	{
		return Math.sqrt((i.getX() - j.getX())*(i.getX() - j.getX()) + 
				(i.getY() - j.getY())*(i.getY() - j.getY()));
	}
	
//	public static Vector findNewNeighbor(int i, RouteMARKET newRM)
//	{
//		Vector results = new Vector();
//		NeighborTable old;
//		old = (NeighborTable)history.get(i);
//		for(int j = 0; j < newTab.size(); j++)
//		{
//			NeighborEntry ne = (NeighborEntry) newTab.get(j);
//			if(old == null)
//			{
//				results.add(ne);
//				continue;
//			}
//			int index = old.indexOf(ne);
//			if(index < 0)
//			{
//				results.add(ne);
//			}
//		}
//		return results;
//	}

}
