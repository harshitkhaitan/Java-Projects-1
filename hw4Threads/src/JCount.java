
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class JCount extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField DesiredCountFeild;
	private JLabel CountLabel;
	private JButton StartButton, StopButton;
	private WorkerThread worker;
	public JCount(){
		//Set Layout
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//Initialize Text Field;
		DesiredCountFeild = new JTextField("100000000");
		this.add(DesiredCountFeild);
		
		//Initialize Label;
		CountLabel = new JLabel("0");
		this.add(CountLabel);
		
		//Add Start Button;
		StartButton = new JButton("Start");
		this.add(StartButton);
		StartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("Start button pressed");
				int currentCount = (int) Integer.parseInt(DesiredCountFeild.getText());
				if(worker != null) worker.interrupt();
				worker = new WorkerThread(currentCount);
				worker.start();
			}
		});
		
		//Add Stop Button;
		StopButton = new JButton("Stop");
		this.add(StopButton);
		StopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(worker != null) worker.interrupt();
			}
		});	
		
		this.add(Box.createRigidArea(new Dimension(0,40)));
		
		}
	
	private class WorkerThread extends Thread{
		private int CountTo;
		private Integer Count;
		private WorkerThread(int countTo){
			this.CountTo = countTo;
			this.Count = 0;
		}
		@Override
		public void run() {
			for(int i=0;i<=CountTo;i++){
				if (this.isInterrupted()) {
					break; 
				}
				if((i%10000)==0){
					Count = i;					
					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
						break;
					}
					SwingUtilities.invokeLater(	new Runnable(){
						public void run() {
							CountLabel.setText(Count.toString());
						}
					});			
				}
			}
			
		}
		
		
	}

	private static void createAndShowGUI() {
		// create your GUI HERE
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
	
		JFrame frame = new JFrame("Count");
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));		
		
		JCount count1 = new JCount();
		frame.add(count1);
		JCount count2 = new JCount();
		frame.add(count2);
		JCount count3 = new JCount();
		frame.add(count3);
		JCount count4 = new JCount();
		frame.add(count4);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
}
