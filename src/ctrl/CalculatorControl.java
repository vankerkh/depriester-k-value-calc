/**
*Class:             CalculatorContrl.java
*Project:          	AVA Smart Home
*Author:            Jason Van Kerkhoven
*Date of Update:    05/10/2017
*Version:           1.0.0
*
*Purpose:           null
*					
* 
*Update Log			v1.0.0
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

//import packages
import io.CSVFormatException;
import io.CSVParser;
import ui.CalculatorUI;



public class CalculatorControl implements ActionListener
{
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
		ui = new CalculatorUI("v1.0.0",this, coeffs.keySet());
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
	
	
	
	public static void main(String[] args) throws FileNotFoundException, CSVFormatException
	{
		CalculatorControl cc = new CalculatorControl("dat/DePriester-Equation-K-Calculator.csv");
	}
	
}
