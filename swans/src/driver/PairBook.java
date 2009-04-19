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
		//get the largest time in the file
		
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
			System.out.println("exit");
			System.exit(0);//stop here
		}
	
	}
	
	public void getNewBook(long secs) throws IOException
	{
		String sec = String.valueOf(secs);
		
		pairList.clear();
		
		LinkedList[] ll = new LinkedList [42];
		for(int i=0;i<42;i++)
		{
			ll[i] = new LinkedList ();
		}
		
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
						test = ll[indexNode2].indexOf(indexNode1);
						if(test == -1)
						{
							ll[indexNode1].add(indexNode2);
						}
						
					}
					strLine = br.readLine();
					String str2[] = strLine.split(" ");
					if(str2[0].equals("starter")) break;
			}
			
		}
		//Close the input stream
		in.close();
		
		int k=0;
		while(k<42)
		{
			int size = ll[k].size();
			if(size==0) k++;
			else
			{
				for(int i = 0; i<ll[k].size();i++)
				{	
					int p = Integer.parseInt(ll[k].get(i).toString());
					NodePair node = new NodePair(k,p);
					
					pairList.add(node);
				}
				k++;
			}
		}		
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
			
			//abc.toArray();

	 }

	public Vector<NodePair> getPairList() {
		return pairList;
	}

		
	

}
