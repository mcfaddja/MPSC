/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a file reading utility class to read from an input file, specified 
 *  by its filename, and store the resulting data in a String array.  
 * 
 * @author Jonathan McFadden (mcfaddja@uw.edu)
 * @version 0.1
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
     *  the reader.
     * 
     * @return myFileName
     */
    public String getFileName() {
        return myFileName;
    }
    
    
    /**
     * Utility method to change the filename currently specified for use by 
     *  the reader.
     */
    public void setFileName(final String theFileName) {
        myFileName = theFileName;
    }
    
    
}
