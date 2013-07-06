
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;


public class MetropolisFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MetropolisTableModel model;
	private JTable table;
	private JButton AddButton, SearchButton;
	private JTextField MetropolisText, ContinentText, PopulationText;
	private JComboBox ExactMatchComboBox, PopulationComboBox;
	private static final String ExactMatchString = "Exact Match";
	private static final String PartialMatchString = "Partial Match";
	private static final String PopulationLargerString = "Population Larger Than";
	private static final String PopulationSmallerString = "Population Smaller Than";

	
	public MetropolisFrame() {
		super("Metropolis Viewer");
	
		JPanel content = new JPanel(); 
		content.setLayout(new BorderLayout(4,4));
		
		JPanel top = new JPanel();
		JLabel Metropolis = new JLabel("Metropolis:");
		JLabel Continent = new JLabel("Continent:");
		JLabel Population = new JLabel("Population:");
		MetropolisText = new JTextField(20);
		ContinentText = new JTextField(20);
		PopulationText = new JTextField(20);

		top.add(Metropolis);
		top.add(MetropolisText);
		top.add(Continent);
		top.add(ContinentText);
		top.add(Population);	
		top.add(PopulationText);

		
		content.add(top, BorderLayout.PAGE_START);
		
		model = new MetropolisTableModel();		
		table = new JTable(model);
		
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(300,200));
		content.add(scrollpane, BorderLayout.CENTER);

		JPanel right = new JPanel();	
		right.setPreferredSize(new Dimension(150,200));
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		AddButton = new JButton("Add");		
		
		right.add(AddButton);	
		AddButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.addRow(MetropolisText.getText(), ContinentText.getText(), PopulationText.getText());
			}
		});
		
		
		SearchButton = new JButton("Search");
		right.add(SearchButton);
		SearchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.search(MetropolisText.getText(), ContinentText.getText(), PopulationText.getText(), ExactMatchComboBox.getSelectedItem().equals(ExactMatchString), PopulationComboBox.getSelectedIndex());
			}
		});
		
		
		JPanel rightBottom = new JPanel();
//		rightBottom.setLayout(new BoxLayout(rightBottom, BoxLayout.Y_AXIS));
		rightBottom.setBorder(new TitledBorder("Search Options"));
		
		
		String[] PopulationOptions = {"", PopulationLargerString, PopulationSmallerString};
		PopulationComboBox =  new JComboBox(PopulationOptions);
		
		rightBottom.add(PopulationComboBox);
		
		String[] ExactMatchOptions = {ExactMatchString,PartialMatchString};
		ExactMatchComboBox = new JComboBox(ExactMatchOptions);
		
		rightBottom.add(ExactMatchComboBox);
		
		right.add(rightBottom);
		content.add(right, BorderLayout.EAST);
				
		
		setContentPane(content);				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		@SuppressWarnings("unused")
		MetropolisFrame frame = new MetropolisFrame();		
		
//		frame.model.search("London", "", "", true, -1);
		
	}

}
