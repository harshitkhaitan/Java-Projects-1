

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea area1, area2;
	 private JCheckBox AutoCheck;
	 
	public SudokuFrame() {
		super("Sudoku Solver");
		
		// YOUR CODE HERE
//		JPanel panel = new JPanel();
//		panel.add(new JLabel("Brain:"));
//		this.add(panel);
		JPanel content = new JPanel(); 
		content.setLayout(new BorderLayout(4,4));
		area1 = new JTextArea(15,20);
		area1.setBorder(new TitledBorder("Puzzle"));
		area2 = new JTextArea(15,20);
		area2.setBorder(new TitledBorder("Solution"));
		content.add(area1,BorderLayout.CENTER);
		area1.addKeyListener(new KeyAdapter() {
			
		});
		
		content.add(area2,BorderLayout.EAST); 
		
		JPanel bottom = new JPanel();
		
		JButton checkButton = new JButton("Check");
		bottom.add(checkButton);
		checkButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solveSudoku();
			}
		});
		
		AutoCheck = new JCheckBox("Auto Check");
		bottom.add(AutoCheck);		
		
		area1.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				solveSodokuifAuto();			
			}
			
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				solveSodokuifAuto();
			}
			
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				solveSodokuifAuto();
			}
			
			private void solveSodokuifAuto(){
				if(AutoCheck.isSelected()) solveSudoku();
			}
		});
		
		
		
		content.add(bottom,BorderLayout.SOUTH);
		
		setContentPane(content);
		
		// Could do this:
		// setLocationByPlatform(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
		
	private void solveSudoku(){
		
//		String[] lines = area1.getText().split("\\n");
//		int[][] puzzleGrid = Sudoku.stringsToGrid(lines);
			
		try{
			Sudoku sudoku;
			sudoku = new Sudoku(area1.getText());
			System.out.println(sudoku);
			int count = sudoku.solve();
			long elapsedTime = sudoku.getElapsed();
			area2.setText(sudoku.getSolutionText());
			area2.append("solutions:" + count + "\n");
			area2.append("elapsed:" + elapsedTime + "ms\n");
		} catch (Exception ignored){ 	
			area2.setText("Parsing problem");
		}
	}
	
//	private boolean checkforValid(int[][] cur_grid){
//		if (cur_grid == null) return false;
//		if (cur_grid.length != Sudoku.SIZE) return false;
//		if (cur_grid[0].length != Sudoku.SIZE) return false; 
//
//		return true;
//	}
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		@SuppressWarnings("unused")
		SudokuFrame frame = new SudokuFrame();
	}

}
