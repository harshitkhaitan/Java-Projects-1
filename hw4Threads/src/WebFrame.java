
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WebFrame extends JFrame{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton SingleThreadFetchButton, ConcurrentFetchButton, StopButton;
	private JTextField NoOfThreadsFeild;
	private JLabel RunningLabel, CompletedLabel, ElapsedLabel;
	private Integer Running, Completed;
	private Object RunningLock, ModelLock, LaunchLock;
	private JProgressBar ProgressBar;
	private DefaultTableModel model;
	private Launcher launcher;
	private long LauncherStartTime;
	private long LauncherEndTime;

	public WebFrame(String str){
		super("WebDownloaderLoader");
		
		RunningLock = new Object();
		ModelLock = new Object();
		LaunchLock = new Object();
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));	
		
		model = new DefaultTableModel(new String[] { "url", "status"}, 0);
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(600,300));
		this.add(scrollpane);
		
		try{
		    FileInputStream fstream = new FileInputStream(str);
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
			while ((strLine = br.readLine()) != null)   {
				String[] rowData = {strLine, ""};
				model.addRow(rowData);
			}
			in.close();
		}catch (Exception e){//Catch exception if any
			  System.out.println("Error: " + e.getMessage());
		}
	
		SingleThreadFetchButton = new JButton("Single Thread Fetch");
		this.add(SingleThreadFetchButton);
		SingleThreadFetchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("Start button pressed");
//				setRunning(0);
				if(launcher != null) launcher.interrupt();
				launcher = new Launcher();
				launcher.start();
//				StopButton.setEnabled(true);
				StopButton.setEnabled(false);

			}
		});
		
		ConcurrentFetchButton = new JButton("Concurrent Fetch");
		this.add(ConcurrentFetchButton);
		ConcurrentFetchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				setRunning(0);
				
				Integer NumThreads = Integer.parseInt(NoOfThreadsFeild.getText()); 
				if(launcher != null) launcher.interrupt();
				launcher = new Launcher(NumThreads);
				launcher.start();
//				StopButton.setEnabled(true);
			}
		});
	
		
		NoOfThreadsFeild = new JTextField("4");
		NoOfThreadsFeild.setMaximumSize(new Dimension(50,2));
		this.add(NoOfThreadsFeild);
		
		RunningLabel = new JLabel("Running: 0");
		this.add(RunningLabel);
		
		CompletedLabel = new JLabel("Completed: 0");
		this.add(CompletedLabel);
		
		ElapsedLabel = new JLabel("Elapsed:");
		this.add(ElapsedLabel);
		
		ProgressBar = new JProgressBar();
		this.add(ProgressBar);
		
		StopButton = new JButton("Stop");
		StopButton.setEnabled(false);
		this.add(StopButton);
		StopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("Stop button pressed");
				if(launcher != null) launcher.interrupt();
				StopButton.setEnabled(false);
//				SingleThreadFetchButton.setEnabled(true);
//				ConcurrentFetchButton.setEnabled(true);
			}
		});

	}
	
	
	private class Launcher extends Thread{
	
		private int Maxthreads;
		private Semaphore MaxWorkers;
		private int TotalUrls;
		private ArrayList <WebWorker> workers;

		private Launcher(){
			this.Maxthreads = 1;
			this.MaxWorkers = new Semaphore(Maxthreads);
			this.TotalUrls = model.getRowCount();
			workers = new ArrayList<WebWorker>();
		}
		
		private Launcher(int maxthreads){
			this.Maxthreads = maxthreads;
			this.MaxWorkers = new Semaphore(Maxthreads);
			this.TotalUrls = model.getRowCount();
			workers = new ArrayList<WebWorker>();
		}
		
		@Override
		public void run(){
			synchronized (LaunchLock) {
				LauncherStartTime = System.currentTimeMillis();				
				cleanUrlStatus();
				setRunning(0);
				IncrRunning();
				setCompleted(0);
				ResetElapsedTime();
				ResetProgressBar(TotalUrls);
				SwingUtilities.invokeLater(	new Runnable(){
					public void run() {
						StopButton.setEnabled(true);
						SingleThreadFetchButton.setEnabled(false);
						ConcurrentFetchButton.setEnabled(false);

					}
				});					

				for(int i=0; i<TotalUrls; i++){
					if(this.isInterrupted()){
						//Interrupt all worker threads
						InterruptBehavior();
						break;
					}
					try {
						MaxWorkers.acquire();
						WebWorker worker = new WebWorker(MaxWorkers, i);
						worker.start();
						workers.add(worker);
						
					} catch (InterruptedException e) {
						//Interrupt all worker threads
						InterruptBehavior();
						break;
					}
					
				}
				while(MaxWorkers.availablePermits() != Maxthreads){
					if(this.isInterrupted()){
						InterruptBehavior();
					}	
				}			
				DecrRunning();
				LauncherEndTime = System.currentTimeMillis();
				PublishElapsedTime((double) (LauncherEndTime-LauncherStartTime)/1000);
				ResetProgressBar(TotalUrls);
								
				SwingUtilities.invokeLater(	new Runnable(){
					public void run() {
						StopButton.setEnabled(false);
						SingleThreadFetchButton.setEnabled(true);
						ConcurrentFetchButton.setEnabled(true);
						ProgressBar.setValue(0);
					}
				});					
			}
		}
		
		private void InterruptBehavior(){
			for(WebWorker worker: workers){
				if(worker != null) {
					worker.interrupt();
//					System.out.println("Worker Interrupted" + worker.getName());
				}
				
			}
		}
		
	}
	
	private class WebWorker extends Thread {
		
		private Semaphore MaxWorkers;
		private StringBuilder contents;
		private int ModelRow;
		private String UrlString;
		private boolean interruptedFlag;
		private long StartTime;
		private long EndTime;
		
		private WebWorker (Semaphore maxWorkers, int row){
			this.MaxWorkers = maxWorkers;
			contents = null;
			this.ModelRow = row;
			interruptedFlag = false;
		}
		
		@Override
		public void run() {
			IncrRunning();
			try {
				Thread.sleep(100);
				UrlString = getUrl(ModelRow);
				StartTime = System.currentTimeMillis();				
				downloader(UrlString);
				String status;
				if(interruptedFlag){
					// Call interrupt handler
					status = "interrupted";
				}else if(contents == null){
					status = "err";
				}else{
					Date curTime = new Date();
					DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
					EndTime = System.currentTimeMillis();
					Long elapsed = EndTime - StartTime;
					int totalChars = contents.length(); 
					status = dateFormat.format(curTime.getTime()) + "  " + Long.toString(elapsed) + "ms  " + totalChars + " Bytes";
				}
				setUrlStatus(ModelRow, status.toString());
			} catch (InterruptedException e) {
				String status;
				status = "interrupted";
				setUrlStatus(ModelRow, status.toString());
				DecrRunning();
				IncrCompleted();
				MaxWorkers.release();
				return;
			}
			DecrRunning();
			IncrCompleted();
//			IncrProgressBar();
			MaxWorkers.release();
		}
				
		private void downloader(String urlString){	
		
	 		InputStream input = null;
	 		contents = null;
			try {
				URL url = new URL(urlString);
				URLConnection connection = url.openConnection();
			
				// Set connect() to throw an IOException
				// if connection does not succeed in this many msecs.
				connection.setConnectTimeout(5000);
				
				connection.connect();
				input = connection.getInputStream();

				BufferedReader reader  = new BufferedReader(new InputStreamReader(input));
			
				char[] array = new char[1000];
				int len;
				contents = new StringBuilder(1000);
				while ((len = reader.read(array, 0, array.length)) > 0) {
					contents.append(array, 0, len);
					Thread.sleep(100);
				}
				
				// Successful download if we get here
				
			}
			// Otherwise control jumps to a catch...
			catch(MalformedURLException ignored) {}
			catch(InterruptedException exception) {
				// YOUR CODE HERE
				// deal with interruption
				interruptedFlag = true;
				return;
			}
			catch(IOException ignored) {}
			// "finally" clause, to close the input stream
			// in any case
			finally {
				try{
					if (input != null) input.close();
				}
				catch(IOException ignored) {}
			}

	
		}
		
		
	}

	private String getUrl(int row){
		synchronized (ModelLock) {
			return  (String) model.getValueAt(row, 0);
		}
	}
	
	private void setUrlStatus(final int row, final String status){
		synchronized (ModelLock) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					model.setValueAt(status, row, 1);
				}
			});

		}
	}

	private void cleanUrlStatus(){
		synchronized (ModelLock) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					for(int i=0;i<model.getRowCount();i++){
						model.setValueAt("", i, 1);
					}
				}
			});
		}		
	}
	

	private void setRunning(Integer running) {
		synchronized (RunningLock) {
			Running = running;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					RunningLabel.setText("Running: " + Running);
				}
			});
		}
	}

	private void IncrRunning() {
		synchronized (RunningLock) {
			Running++;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					RunningLabel.setText("Running: " + Running);
				}
			});
		}
	}
	
	private void DecrRunning() {
		synchronized (RunningLock) {
			Running--;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					RunningLabel.setText("Running: " + Running);
				}
			});
		}
	}

	private void setCompleted(Integer running) {
		synchronized (RunningLock) {
			Completed = running;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					CompletedLabel.setText("Completed: " + Completed);
				}
			});
		}
	}
	
	private void IncrCompleted() {
		synchronized (RunningLock) {
			Completed++;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					CompletedLabel.setText("Completed: " + Completed);
					ProgressBar.setValue(Completed);
				}
			});
		}
	}
	
	private void PublishElapsedTime(final double elapsed) {
		synchronized (RunningLock) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ElapsedLabel.setText("Elapsed: " + elapsed);
				}
			});
		}
	}

	private void ResetElapsedTime() {
		synchronized (RunningLock) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ElapsedLabel.setText("Elapsed: ");
				}
			});
		}
	}

	private void ResetProgressBar(final int max) {
		synchronized (RunningLock) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ProgressBar.setMaximum(max);
					ProgressBar.setValue(0);
				}
			});
		}
	}



	public static void main(String[] args) {
		final String str = args[0];
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception ignored) { };
				WebFrame frame = new WebFrame(str);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
}
