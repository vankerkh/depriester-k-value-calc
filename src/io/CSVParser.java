/**
*Class:             CSVParser.java
*Project:          	AVA Smart Home
*Author:            Jason Van Kerkhoven
*Date of Update:    05/10/2017
*Version:           1.0.0
*
*Purpose:           Parse a CSV file formated as follows:
*					[CompoundName, IconPath, aT1, aT2, aT3, aT4, aT5, aT6, aP1, aP2, aP3, aP4, aP5]
*					CompoundName is loaded as a string. HTML is allowed for formating
*					IconPath is the full path + filename of the associated icon. Can be PNG or JPG.
*					Note that IconPath must include file extension (ie img/methane.png)
*					aT1 - aP5 are coefficients and are loaded as double precision floating points
* 
*
*Update Log			v1.0.0
*						- null
*/
package io;



//generic imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import javax.swing.ImageIcon;



public class CSVParser 
{
	//declaring static class constants
	private static final int TOTAL_COEFFS = 11;
	
	
	//declaring local instance variables
	private TreeMap<String, double[]> coeffMap;
	private HashMap<String, ImageIcon> icons;
	
	
	//generic constructor
	public CSVParser()
	{
		coeffMap = null;
		icons = null;
	}
	
	
	//generic getters
	public TreeMap<String, double[]> getCoeffs()
	{
		return coeffMap;
	}
	public HashMap<String, ImageIcon> getIcons()
	{
		return icons;
	}

	
	//read file as string
	private String read(String path) throws FileNotFoundException
	{
		//read file
		String contents = new String();
		Scanner scanner = new Scanner(new File(path));
		try
		{
			contents = scanner.useDelimiter("\\Z").next();
			return contents;
		}
		finally
		{
			scanner.close();
		}
	}
	
	
	//parse csv at path for coefficients and icons
	public void parse(String csvPath) throws FileNotFoundException, CSVFormatException
	{
		//declaring hashmap to hold compounds-coefficient pairs and get file contents
		coeffMap = new TreeMap<String, double[]>();
		icons = new HashMap<String, ImageIcon>();
		String contents = this.read(csvPath);
		
		//split contents by compounds
		String[] compounds = contents.split("\n");
		for (int line=0; line<compounds.length; line++)
		{
			String compound = compounds[line];
			
			//declaring variables to parse from compound
			String compoundName = "";
			double[] coeffs = new double[TOTAL_COEFFS];
			
			//split compound into coefficients (first extry name exception)
			String[] vals = compound.split(",");
			
			//check correct number of values
			if (vals.length == (TOTAL_COEFFS+2))
			{
				//iterate through all values
				for (int i=0; i<vals.length; i++)
				{
					//special case for 1st entry denoting compound name
					if (i == 0)
					{
						//hack for row height to be uniform
						if (!vals[i].contains("<sub>"))
						{
							compoundName = "<html>" + vals[i] + "<sub> </sub></html>";
						}
						else
						{
							compoundName = "<html>" + vals[i] + "</html>";
						}
					}
					//special case for icon path
					else if (i == 1)
					{
						//load icon from disk and put in map
						icons.put(compoundName, new ImageIcon(vals[i]));
					}
					//generic case for coefficient
					else
					{
						try
						{
							coeffs[i-2] = Double.parseDouble(vals[i]);
						}
						catch (NumberFormatException e)
						{
							throw new CSVFormatException("ERROR line " + line + " at \"" + vals[i] + "\" -- NaN");
						}
					}
				}
				
				//save to map
				coeffMap.put(compoundName, coeffs);
			}
			else if (vals.length > (TOTAL_COEFFS+2))
			{
				throw new CSVFormatException("CSV PARSE ERROR line " + line + " -- Too many values found");
			}
			else
			{
				throw new CSVFormatException("CSV PARSE ERROR line " + line + " -- Too few values found");
			}
		}
	}
}
