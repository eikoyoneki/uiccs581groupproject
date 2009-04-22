package driver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Vector;

public class PairBook {

	

	/**
	 * @param args
	 */
	//private static Vector<NodePair> pairList;
	private String filename = "e:\\neighbors";
	Vector<NodePair> pairList;
	
	public PairBook() {
		pairList = new Vector<NodePair>();
	}
	
	public Vector getNewBook(String sec) throws IOException
	{	
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
		return pairList;
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
				 {l=i+1;break;}
			 
		}
		
		return l;
	}
	public int whetherExistComparePreSec(int indexNode1, int indexNode2)
	{
		int l=0, m,n, i;
		int num = pairList.size();
		
		for(i = 0; i < num; i++)
		{
			m = pairList.get(i).getX();
			n = pairList.get(i).getY();
			 if((indexNode1==m)&&(indexNode2==n))
				 {l=i+1;break;}
			 
		}
		
		return l;
	}
	public Vector getNewBookComparePreSec(String sec) throws IOException
	{
		FileInputStream fstream = new FileInputStream(filename);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		//Read File Line By Line
		strLine = br.readLine();
		int indexNode1, indexNode2;
		String presec = String.valueOf(Integer.parseInt(sec) - 1);		
		while ((strLine != null)) 
		{	
			String str[] = strLine.split(" ");
			if(!(str[0].equals(presec))) 
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
					//test = this.whetherExist(indexNode1, indexNode2);
					//if(test==0)
					pairList.add(node);
				}
				strLine = br.readLine();
				String str2[] = strLine.split(" ");
				if(str2[0].equals("starter")) break;
			}			
		}
		strLine = br.readLine();
		while(true)
		{			
			String str1[] = strLine.split(" ");
			for(int i=2;i<str1.length;i++)
			{
				indexNode1=Integer.parseInt(str1[1]);
				indexNode2=Integer.parseInt(str1[i]);
				int test1,test2;
				test1 = whetherExistComparePreSec(indexNode1, indexNode2);
				if(test1 == 0)
				{
					test2 = whetherExist(indexNode1, indexNode2); 
					if(test2 == 0)
					{
						NodePair node = new NodePair(indexNode1, indexNode2);
						pairList.add(node);
					}
				}
				else pairList.remove(test1-1);
			}
			strLine = br.readLine();
			String str2[] = strLine.split(" ");
			if(str2[0].equals("starter")) break;
		}
		
		//Close the input stream
		in.close();
		return pairList;
	}
	
	
	public static void main(String[] args) throws IOException 
	{
	
			// TODO Auto-generated method stub
			PairBook abc = new PairBook();
			Vector<NodePair> vec;
			vec = abc.getNewBookComparePreSec("440");
			for(int i = 0; i<vec.size();i++)
			{
				System.out.print("Node: ");
				System.out.print((vec.get(i)).getX());
				System.out.print("  Neighbor: ");
				System.out.println((vec.get(i)).getY());
			}
			
			//abc.toArray();

	 } 
}
