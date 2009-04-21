package jist.swans.route;


public class IDPair
{
	public IDPair(int a, int b)
	{
		if(a<b)
		{
			part1 = a;
			part2 = b;
		}
		else
		{
			part1 = b;
			part2 = a;
		}
	}
	
	public int part1;
	public int part2;
	
	public boolean equals(Object obj) {
        if (obj == null) return false;
        IDPair p2 = (IDPair)obj;
        if(p2.part1 == this.part1 && p2.part2 == this.part2)
        {
        	return true;
        }
        return false;
    }
}