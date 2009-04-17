package driver;

import java.util.*;

import jist.swans.route.RouteGPSR.*;

public class NeighborHistory 
{
	public static Vector history;
	
	public static void init(int size)
	{
		history = new Vector(size);
		for(int i = 0; i < size; i++)
		{
			history.add(null);
		}
	}
	
	public static void update(int i, NeighborTable tab)
	{
		history.set(i, tab);
	}
	
	public static Vector findNewNeighbor(int i, NeighborTable newTab)
	{
		Vector results = new Vector();
		NeighborTable old = (NeighborTable)history.get(i);
		for(int j = 0; j < newTab.size(); j++)
		{
			NeighborEntry ne = (NeighborEntry) newTab.get(j);
			if(old == null)
			{
				results.add(ne);
				continue;
			}
			int index = old.indexOf(ne);
			if(index < 0)
			{
				results.add(ne);
			}
		}
		return results;
	}

}
