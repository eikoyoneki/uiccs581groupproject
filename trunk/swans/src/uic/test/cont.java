package uic.test;

import jist.runtime.JistAPI;
import jist.runtime.JistAPI.Entity;

public class cont implements JistAPI.Entity
{

	public void blocking() //throws JistAPI.Continuation
	{
		
		System.out.println("called at time t = " + JistAPI.getTime());
		JistAPI.sleep(1);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		cont c = new cont();
		for(int i = 0; i < 3; ++i)
		{
			System.out.println("i = "+ i + " t = " + JistAPI.getTime());
			c.blocking();
		}
	}

}
