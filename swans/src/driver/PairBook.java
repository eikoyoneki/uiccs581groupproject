package driver;

import java.io.*;
import java.util.*;

public class PairBook {	


	/**
	 * @param args
	 */
	//private static Vector<NodePair> pairList;
	private String filename = "src/neighbors";
	Vector<NodePair> pairList; //pairs list
	static long sec_cur = 0; //current second
	long max_sec=28800;
	
	public PairBook() {
		pairList = new Vector<NodePair>();		
		max_sec = 28800;			
	}
	
	
	public void getNewBook() throws IOException
	{
		if(sec_cur <= max_sec)
		{
			System.out.println(sec_cur);
			this.getNewBook(sec_cur);
			sec_cur ++;
			
		}
		else
		{
			System.out.println("run out of time, exit");
			//System.exit(0);//stop here
		}
	
	}
	
	public void getNewBook(long secs) throws IOException
	{
		String sec = String.valueOf(secs);
		
		pairList.clear();
		
		FileInputStream fstream = new FileInputStream(filename);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		//Read File Line By Line
		strLine = br.readLine();
		int indexNode1, indexNode2;
		while ((strLine != null)) 
		{	
			String str[] = strLine.split(" ");
			if(!(str[0].equals(sec))) 
				strLine = br.readLine();
			else
			{	
				String str1[] = strLine.split(" ");
					for(int i=2;i<str1.length;i++)
					{
						indexNode1=Integer.parseInt(str1[1]);
						indexNode2=Integer.parseInt(str1[i]);
						int test;
						NodePair node = new NodePair(indexNode1, indexNode2);
						test = whetherExist(indexNode1, indexNode2);
						if(test == 0)
						{
							pairList.add(node);
						}	
					}
					strLine = br.readLine();
					String str2[] = strLine.split(" ");
					if(str2[0].equals("starter")) break;
			}
			
		}
		//Close the input stream
		in.close();		
		
	}
	
	public int whetherExist(int indexNode1, int indexNode2)
	{
		int l=0, m,n, i;
		int num = pairList.size();
		
		for(i = 0; i < num; i++)
		{
			m = pairList.get(i).getX();
			n = pairList.get(i).getY();
			 if((indexNode1==n)&&(indexNode2==m))
				 {l=1;break;}
			 
		}
		
		return l;
	}
	
	public Vector<NodePair> getPairList() {
		return pairList;
	}
	
	public static void main(String[] args) throws IOException 
	{
	
			// TODO Auto-generated method stub
			PairBook vec = new PairBook();
			while(true)
			{
			vec.getNewBook();
			for(int i = 0; i<vec.getPairList().size();i++)
			{
				System.out.print("Node: ");
				System.out.print((vec.getPairList().get(i)).getX());
				System.out.print("  Neighbor: ");
				System.out.println((vec.getPairList().get(i)).getY());
			}
			}	
	 }	

}
