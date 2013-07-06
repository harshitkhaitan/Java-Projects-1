package harshit.banking;

import java.io.*;
import java.util.concurrent.*;

public class Bank {
	
private static final int TOTAL_ACCOUNTS = 20;
private static final String SHUT_THREAD = "-1 0 0";
private Account[] accounts;
private BlockingQueue<Transaction> transactions;
private int TotalThreads;
private Thread[] workers;

	public Bank(String file, int totalThreads){
	
		// Set Up Bank Accounts;
		accounts = new Account[TOTAL_ACCOUNTS];
		for(int i=0;i<TOTAL_ACCOUNTS;i++){
			accounts[i] = new Account(i, 1000);
		}

		transactions =  new ArrayBlockingQueue<Transaction>(100);
		
		this.TotalThreads = totalThreads; 
		
		workers = new Worker[TotalThreads];
		for(int j=0;j<TotalThreads;j++){
			workers[j] = new Worker();
			workers[j].start();
		}
		
		try{
		    FileInputStream fstream = new FileInputStream(file);
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
			while ((strLine = br.readLine()) != null)   {
//				  System.out.println ("Raw String: " + strLine);					
				  Transaction trn = new Transaction(strLine);
//				  System.out.println ("Queue: " + trn.toString());					
				  transactions.put(trn);
			}
			in.close();
			for (int i=0; i<TotalThreads; i++){
				Transaction trn = new Transaction(SHUT_THREAD);
				transactions.put(trn);
			}
		}catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		}
		
		for(int j=0;j<TotalThreads;j++){
			try {
				workers[j].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		printOutput();
		
	}
	
	private void printOutput(){
		for(Account acct: accounts){
			System.out.print(acct.toString());
		}
	}
	
	private class Worker extends Thread {
		
		private Transaction trnx;
		@Override
		public void run() {
			// TODO Auto-generated method stub
//			super.run();
			try {
				while(true){
					trnx = transactions.take();
					if ((trnx.getSrcId() == -1)) break;
					for(Account acct: accounts){
						if(trnx.getSrcId() == acct.getId()) acct.withdraw(trnx.getAmount());
						if(trnx.getDestID() == acct.getId()) acct.deposit(trnx.getAmount());
					}
//					System.out.println("This thread: " + Thread.currentThread().getName() + " Picked Up " + trnx.toString()) ;
				}
			} catch (InterruptedException e) {
			}
		}	
	}
	
	
	public static void main(String[] args){
		
		Integer threads = Integer.parseInt(args[1]);

		@SuppressWarnings("unused")
		Bank myBank = new Bank(args[0],threads);
		
		
	}
}
