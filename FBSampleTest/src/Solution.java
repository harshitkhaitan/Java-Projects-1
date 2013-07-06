import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;


public class Solution {

	String filename; 
	Integer K=0;
	Integer N=0;
	Integer Num_Moves=0;
	
	
	public Solution(String filename) {
		
		this.filename = filename;
	}


	private void Execute() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String line;
			line = in.readLine();
			String[] splitline = line.split(" +");  
			N = Integer.parseInt(splitline[0]);
			K = Integer.parseInt(splitline[1]);
			
			HashMap<Integer,LinkedList<Integer>> orig = new HashMap<Integer,LinkedList<Integer>>(); 
			HashMap<Integer,LinkedList<Integer>> fin = new HashMap<Integer,LinkedList<Integer>>();
			
			line = in.readLine();
			String[] splitline2 = line.split(" +");  
			for (int i=1;i<=N;i++){
				orig.put(Integer.parseInt(splitline2[i]),orig.get(Integer.parseInt(splitline2[i])).add(i));
			}
			
			line = in.readLine();
			String[] splitline3 = line.split(" +");  
			for (int i=1;i<=N;i++){
				fin.put(Integer.parseInt(splitline2[i]),i);
			}
			
			for(Integer Key: fin.keySet()){
				Integer cur = getmax(fin.get(Key));
				for(Integer n: fin.get(Key)){
					
				}
			}
			
			in.close();
	}
	
	
	private Integer getmax(LinkedList<Integer> linkedList) {
		// TODO Auto-generated method stub
		Integer maxValue,maxPos=0;
		maxValue = linkedList.get(0);
		Integer N = linkedList.size();
		for (int i=0;i<N; i++){
			if(maxValue<=linkedList.get(i)){
				maxValue=linkedList.get(i);
				maxPos=i;
			}
		}
		linkedList.remove(maxPos);
		return null;
	}


	public static void main(String[] args) {
		// Check usage. 
		if(args.length == 0)  {
			System.err.print("Usage: orderEngine <json filename with orders>");

		}else{
			try {
				new Solution(args[0]).Execute();
			} catch (IOException e) {
				System.err.print("Could not read the file: " + args[0]);
			}
		}
	}
	
	private class Bucket{
		LinkedList<Integer> ll;

		public Bucket() {
			this.ll = new LinkedList<Integer>();
		}

		public void add(Integer x) {
			for(int i=0; i<ll.size(); i++){
				if(ll.get(i)>=x){
					ll.add(i, x);
					return;
				}
			}
		}
		
		private LinkedList<Integer> getLl() {
			return ll;
		}

		private void setLl(LinkedList<Integer> ll) {
			this.ll = ll;
		}
		
	}
}


