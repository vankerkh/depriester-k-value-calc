# Purpose

This calculator essentially acts as a DePrieseter chart, calculating the vapor-liquid equilibrium ratio *(ie K-Value)* for a variety of compounds at a particular temperature and pressure.



# How to Use

The pre-built executable `.jar` can be run by double clicking on the icon from a file explorer. Alternatively, the `.jar` can be called from terminal using the standard command: `java -jar kcalc.jar`.

The directory of the executable `.jar` file **must** contain the `.csv` file, from which all the compound names, coefficients, and icons paths are. The default used `.csv` file is "*compund-data.csv*". If double-clicking the `.jar` to run, or running the `.jar` without arguments through the terminal, it will launch using the default `.csv` path and file name.

Above the directory the `.jar` file is located in, there **must** be a directory called _img_, in which all compound icons are located in.

To run the program using compound information from a `.csv` file that is not the default, simply call the program from terminal using the `.csv` path/file as the program argument.<br>
(i.e. command: `java -jar kcalc.jar path/myCompoundData.csv`)



# Adding New Compounds

Adding new compounds can be trivially done by editing the `.csv` file. Each compound is located on a new line (row) in the `.csv`. There are a total of 13 entries per compound in the `.csv`. They are as follows:

| **Value Number** | **Value** | **Type** | **Example** |
|------------------|-----------|----------|-------------|
| 0 | Compound Name (HTML formating allowed) | String | Propylene
| 1 | Icon Path + Filename | String | img/propylene.png
| 2 | aT1 coefficent| double | 1.222
| 3 | aT2 coefficent| double | 0.2323 
| 4 | aT3 coefficent| double | 12.323
| 5 | aT4 coefficent| double | 12312.444
| 6 | aT5 coefficent| double | 232.555
| 7 | aT6 coefficent| double | 0.000
| 8 | aP1 coefficent| double | 1.2323
| 9 | aP2 coefficent| double | 23.3466
| 10 | aP3 coefficent| double | 2352.2122
| 11 | aP4 coefficent| double | 23.2345
| 12 | aP5 coefficent| double | 0.000

*(Please note the coefficeint examples **ARE NOT** actual values)*

The image icons can be either a `.jpg` or a `.png`. The image value can left blank without consequence, and will just result in no image being displayed for that compound. All other values **must** be occupied.

If new images are added, they should be placed within the `/img` directory. It should be noted that the image path/filename given in the `.csv` file must be accurate for the icon to be loaded and displayed. Images are best displayed at *300x275* pixels.
