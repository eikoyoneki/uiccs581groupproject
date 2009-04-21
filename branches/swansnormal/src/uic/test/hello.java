package uic.test;

import jist.runtime.JistAPI;
import jist.runtime.JistAPI.Entity;

public class hello implements JistAPI.Entity
{
	public static void main(String[] args)
	{
		System.out.println("Start");
		hello h = new hello();
		h.myEvent();
	}
	
	public void myEvent()
	{
		JistAPI.sleep(1);
		myEvent();
		System.out.println("hello world, t = " + JistAPI.getTime());
	}
}
