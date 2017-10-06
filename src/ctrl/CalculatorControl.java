/**
*Class:             CalculatorContrl.java
*Project:          	DePriester K Value Calculator
*Author:            Jason Van Kerkhoven
*Date of Update:    06/10/2017
*Version:           1.1.0
*
*Purpose:           The main controller for the K value program.
*					Performs all computations, and handles all inputs/changes in GUI.
*					
* 
*Update Log			v1.1.0
*						- added default CSV path as global constant
*						- added error handling for file not found exception
*						- added error handling for CSV bad format exception
*						- added option to call from terminal and pass path of CSV file instead of
*						  of using default path
*					v1.0.0
*						- null
*/
package ctrl;



//generic imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

//import packages
import io.CSVFormatException;
import io.CSVParser;
import ui.CalculatorUI;



public class CalculatorControl implements ActionListener
{
	//declaring static constants
	private static final String VERSION = "v1.1.0";
	
	//declaring local instance variables
	private CalculatorUI ui;
	private TreeMap<String, double[]> coeffs;
	private HashMap<String, ImageIcon> icons;
	private double[] cf;
	
	//generic constructor
	public CalculatorControl(String csvPath) throws FileNotFoundException, CSVFormatException
	{
		//parse CSV for coefficient and compound info
		ArrayList<String> coeffNames = new ArrayList<String>();
		CSVParser parser = new CSVParser();
		parser.parse(csvPath);
		coeffs = parser.getCoeffs();
		icons = parser.getIcons();
		
		//setup UI
		ui = new CalculatorUI(VERSION,this, coeffs.keySet());
		ui.setVisible(true);
	}

	
	//compute DePriester K Value
	public double computeKValue(double[] cf, double temp, double psi)
	{
		return Math.exp(cf[0]/(Math.pow(temp, 2))+cf[1]/temp+cf[5]+cf[6]*Math.log(psi)+cf[7]/(Math.pow(psi, 2)))+cf[8]/psi;
	}
	

	@Override
	//respond to UI actions
	public void actionPerformed(ActionEvent ae) 
	{
		switch (ae.getActionCommand())
		{
			//new compound selected by user
			case(CalculatorUI.COMPOUND_SELECT):
				//get compound and coeffs
				String compound = ui.getSelectedCompound();
				cf = coeffs.get(compound);
				
				//print coeffs to screen and update image
				ui.printCoefficients(cf);
				ui.setIcon(icons.get(compound));
				
				//compute and print K value if calid temp and pressure
				Double temp = ui.getTemperature();
				Double psi = ui.getPressure();
				if (temp != null && psi != null)
				{
					ui.printKValue(computeKValue(cf, temp, psi));
				}
				break;
				
			//new values entered
			default:
				//compute and print K value if calid temp and pressure
				temp = ui.getTemperature();
				psi = ui.getPressure();
				if (temp != null && psi != null && cf != null)
				{
					ui.printKValue(computeKValue(cf, temp, psi));
				}
				break;
			
		}
	}
	
	
	
	public static void main(String[] args)
	{
		//determine run configuration
		boolean dialog = (args.length < 1);
		String path;
		if (dialog)
		{
			path = CalculatorControl.DEFAULT_CSV_PATH;
		}
		else
		{
			path = args[0];
		}
		
		//create instance and run
		try 
		{
			CalculatorControl cc = new CalculatorControl(path);
		} 
		catch (FileNotFoundException|CSVFormatException e) 
		{
			if(dialog)
			{
				JOptionPane.showMessageDialog(null, e.getMessage(), CalculatorUI.WINDOW_TITLE, JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				System.out.println(e.getMessage());
			}
		}
	}
	
}
