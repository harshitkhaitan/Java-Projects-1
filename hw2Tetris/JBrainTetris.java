import java.awt.Dimension;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JSlider;

import javax.swing.UIManager;


public class JBrainTetris extends JTetris{

	/**
	 * @param args
	 */
	private boolean brainMode;
	private JCheckBox brainButton;
	private DefaultBrain db;
	private Brain.Move myMove;
	private JSlider adversary;
	private JLabel adv_status;
	
	JBrainTetris(int pixels){
		super(pixels);
		
		db = new DefaultBrain();
		myMove = new Brain.Move();
	}
	
	
	
	@Override
	public void tick(int verb) {
		if(brainButton.isSelected() && verb==DOWN){
			if (currentPiece != null) {
				board.undo();	// remove the piece from its old position			
				myMove = db.bestMove(board, currentPiece, board.getHeight()-TOP_SPACE, myMove);
				if((myMove != null)){
					if(!(myMove.piece.equals(currentPiece))){
						super.tick(ROTATE);
					}
					if (myMove.x < currentX){
						super.tick(LEFT);
					} else if(myMove.x > currentX){
						super.tick(RIGHT);
					}
				}else{
					stopGame();
					return;					
				}
			}
		}
		
//		System.out.println("OLD X,Y " + currentX + "," + currentY + " New X,Y " + newX + " , " + newY );
		
		super.tick(verb);
		
	}



	@Override
	public Piece pickNextPiece() {
		// TODO Auto-generated method stub
		int randomNum = (int)(Math.random()*99);
		Piece my_piece = new Piece(Piece.PYRAMID_STR);
		Brain.Move myBadMove = new Brain.Move();
		double WorstScore = 0;
		
		if(randomNum < adversary.getValue()){	
			adv_status.setText("*Ok*");
			for(Piece cur_piece:Piece.getPieces()){
				myBadMove = db.bestMove(board, cur_piece, board.getHeight()-TOP_SPACE, myBadMove);	
				if(myBadMove == null) return super.pickNextPiece();
				if(myBadMove.score > WorstScore) {
					my_piece =cur_piece;
					WorstScore = myBadMove.score;
				}
//				System.out.println("Score " + myBadMove.score + "Worst Case"+" Piece " + cur_piece.getHeight() + " My Piece " + my_piece.getHeight() ) ;
			}
			return my_piece;		
		}else{
			adv_status.setText("Ok");
			return super.pickNextPiece();
		}
		
//		return my_piece;
	}



	@Override
	public JComponent createControlPanel() {
		JComponent result = super.createControlPanel();
		JPanel panel = new JPanel();
		panel.add(new JLabel("Brain:"));
		brainButton = new JCheckBox("Brain active");
		panel.add(brainButton);
		result.add(panel);
		JPanel little = new JPanel();
		little.add(new JLabel("Adversary:"));
		adversary = new JSlider(0, 100, 0); // min, max, current
		adversary.setPreferredSize(new Dimension(100,15));
		little.add(adversary);
		
		adv_status = new JLabel(" ");
		little.add(adv_status);
		adv_status.setText("OK");
		
		result.add(little);
		
		return result;
	}
	
	
	public static void main(String[] args) {
		// Set GUI Look And Feel Boilerplate.
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		JBrainTetris tetris = new JBrainTetris(16);
		JFrame frame = JBrainTetris.createFrame(tetris);
		frame.setVisible(true);
	}

	
	

}
