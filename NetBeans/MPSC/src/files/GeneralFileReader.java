/*
 * Part of a program to implement a Multi-Party Secure Computing protocol.
 */
package files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a file reading utility class to read from an input file, specified 
 * by its filename, and store the resulting data in a String array.  
 * 
 * @author Jonathan McFadden (mcfaddja@uw.edu)
 * @version 0.1.1
 */
public class GeneralFileReader {
    
    /** String to hold the name of the file to be read. */
    private final String myFileName;
    
    /** An ArrayList of String to store the data as it is read from the file. */
    private final List<String> myInput;
    
    
    
    /**
     * Creates a reader with methods to read the file specified by the passed
     * filename as well as to return the data read from the file in the form of
     * a String array.
     * 
     * @param theFileName The filename of the file to be read.
     */
    public GeneralFileReader(final String theFileName) {
        myFileName = theFileName;
        
        myInput = new ArrayList<String>();
    } // END constructor
    
    
    
    /**
     * Utility method to return the filename currently specified for use by 
     * the reader.
     * 
     * @return myFileName
     */
    public String getFileName() {
        return myFileName;
    } // END getFileName() METHOD
    
    /**
     * Method to read in the file specified by the filename passed to the
     * constructor.
     */
    public void readIt() {
        try {
            final FileReader aReader;
            aReader = new FileReader(myFileName);
            final BufferedReader in = new BufferedReader(aReader);
            
            String line = in.readLine();
            while (line != null) {
                myInput.add(line);
                line = in.readLine();
            } // END while LOOP
            
            in.close();
            aReader.close();
        } catch (final IOException exception) {
            System.out.println("IO input error" + exception.getMessage());
        } // END try/catch BLOCK
    } // END readIt() METHOD
    
    /**
     * Method to return the data read from the file by the readIt() method, this
     * method requires that the readIt() method is called first.
     * 
     * @return aStrings String array created from the ArrayList of Strings that
     *         contains the input read from the file.
     */
    public String[] getIt() {
        String[] aStrings = new String[1];
        aStrings = myInput.toArray(aStrings);
        
        return aStrings;
    } // END getIt() METHOD
    
    /**
     * Method to both read in the file specified by the file name passed to the 
     * constructor AND then return the data read from the file.  As such, this
     * method does not require that the readIt() method be called first.
     * 
     * @return aStrings String array created from the ArrayList of Strings that
     *         contains the input read from the file.
     */
    public String[] readItGetIt() {
        this.readIt();
        
        return this.getIt();
    } // END readItGetIt() METHOD
    
    
} // END GeneralFileReader.java CLASS
