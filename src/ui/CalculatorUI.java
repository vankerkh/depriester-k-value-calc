/**
*Class:             CalculatorUI.java
*Project:          	DePriester K Value Calculator
*Author:            Jason Van Kerkhoven
*Date of Update:    05/10/2017
*Version:           1.0.0
*
*Purpose:           User interface.
*					Really what it says on the tin.
*					
* 
*Update Log			v1.0.0
*						- null
*/
package ui;



//import external libraries
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Set;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;



public class CalculatorUI extends JFrame
{
	//declaring class constants
	private final String[] COEFF_NAMES = { "a<sub>T1</sub>",
									"a<sub>T2</sub>",
									"a<sub>T3</sub>",
									"a<sub>T4</sub>",
									"a<sub>T5</sub>",
									"a<sub>T6</sub>",
									"a<sub>P1</sub>",
									"a<sub>P2</sub>",
									"a<sub>P3</sub>",
									"a<sub>P4</sub>",
									"a<sub>P5</sub>"};
	private static final Dimension INPUT_DIMENSION =  new Dimension(70, 20);
	public static final String WINDOW_TITLE = "DePriester Calculator";
	public static final String COMPOUND_SELECT = "input/compound";
	
	
	//declaring local instance variables
	private JPanel contentPane;
	private JLabel[] coeff;
	private JTextField kOutput;
	private JTextField pressureInput;
	private JTextField tempInput;
	private JLabel chemImage;
	private JComboBox compoundSelect;
	private Set<String> compoundSet;

	
	//no version constructor
	public CalculatorUI(ActionListener listener, Set<String> compounds)
	{
		this("", listener, compounds);
	}
	
	
	//generic constructor
	public CalculatorUI(String version, ActionListener listener, Set<String> compounds) 
	{
		//set up display frame
		this.setTitle(WINDOW_TITLE + " " + version);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 700, 415);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		this.contentPane.setLayout(new BorderLayout(30, 0));
		
		//set up west pane
		JPanel westPane = new JPanel();
		contentPane.add(westPane, BorderLayout.WEST);
		westPane.setLayout(new BorderLayout(0, 0));
		
		//set up east pane
		JPanel eastPane = new JPanel();
		contentPane.add(eastPane, BorderLayout.CENTER);
		eastPane.setLayout(new BorderLayout(0, 0));
		
		//set up panel for input
		JPanel inputPanel = new JPanel();
		eastPane.add(inputPanel, BorderLayout.SOUTH);
		inputPanel.setLayout(new BorderLayout(0, 0));
		JPanel inputPane = new JPanel();
		inputPanel.add(inputPane, BorderLayout.SOUTH);
		inputPane.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		
		//set combo box for compound selection
		compoundSelect = new JComboBox();
		compoundSet = compounds;
		for (String item : compoundSet)
		{
			compoundSelect.addItem(item);
		}
		compoundSelect.setActionCommand(COMPOUND_SELECT);
		compoundSelect.addActionListener(listener);
		inputPanel.add(compoundSelect, BorderLayout.NORTH);
		
		//set up panel for coefficient display
		JPanel coefficientPane = new JPanel();
		westPane.add(coefficientPane, BorderLayout.NORTH);
		coefficientPane.setLayout(new GridLayout(6, 2, 10, 10));
		
		//add text fields for output K
		kOutput = new JTextField("K = ");
		kOutput.setEditable(false);
		kOutput.setFont(new Font("Tahoma", Font.BOLD, 35));
		kOutput.setPreferredSize(new Dimension(0,80));
		westPane.add(kOutput, BorderLayout.SOUTH);
		
		//add text boxes for coefficient
		coeff = new JLabel[11];
		for (int i=0; i<11; i++)
		{
			coeff[i] = new JLabel();
			coeff[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			coeff[i].setText("<html><b>" + COEFF_NAMES[i] + "</b>: </html>");
			coeff[i].setPreferredSize(new Dimension(150,30));
			coefficientPane.add(coeff[i]);
		}
		
		//add molecule icon nested in JPanel
		JPanel icon = new JPanel();
		chemImage = new JLabel();
		chemImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		chemImage.setMaximumSize(new Dimension(700,500));
		icon.add(chemImage);
		eastPane.add(icon, BorderLayout.CENTER);
		
		//setup inputs
		JTextField pressureLabel = new JTextField("P (psia): ");
		pressureLabel.setEditable(false);
		pressureLabel.setBorder(null);
		
		pressureInput = new JTextField();
		pressureInput.setPreferredSize(INPUT_DIMENSION);
		pressureInput.addActionListener(listener);
		
		JTextField spacer = new JTextField("          ");
		spacer.setBorder(null);
		spacer.setEnabled(false);
		spacer.setBackground(inputPane.getBackground());
		
		JTextField tempLabel = new JTextField("T (\u00b0R): ");
		tempLabel.setEditable(false);
		tempLabel.setBorder(null);
		
		tempInput = new JTextField();
		tempInput.setPreferredSize(INPUT_DIMENSION);
		tempInput.addActionListener(listener);
		
		inputPane.add(pressureLabel);
		inputPane.add(pressureLabel);
		inputPane.add(pressureInput);
		inputPane.add(spacer);	
		inputPane.add(tempLabel);
		inputPane.add(tempInput);
	}

	
	//generic getter for selected compound
	public String getSelectedCompound()
	{
		return compoundSelect.getItemAt(compoundSelect.getSelectedIndex()).toString();
	}
	//generic getter for pressure
	public Double getPressure()
	{
		try
		{
			return Double.parseDouble(pressureInput.getText());
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
	//generic getter for temperature
	public Double getTemperature()
	{
		try
		{
			return Double.parseDouble(tempInput.getText());
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
	
	
	//print K value to screen
	public void printKValue(double k)
	{
		kOutput.setText("K = " + k);
	}
	
	
	//change icon
	public void setIcon(ImageIcon icon)
	{
		chemImage.setIcon(icon);
	}
	
	
	//print coefficient array to screen
	public void printCoefficients(double[] cf)
	{
		//print to screen if array correct length
		if (cf.length == coeff.length)
		{
			for (int i=0; i<cf.length; i++)
			{
				coeff[i].setText("<html><b>" + COEFF_NAMES[i] + "</b>" + cf[i] + "</html>");
			}
		}
	}
}
