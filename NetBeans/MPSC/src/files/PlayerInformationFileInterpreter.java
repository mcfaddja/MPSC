/*
 * Part of a program to implement a Multi-Party Secure Computing protocol.
 */
package files;

import java.math.BigInteger;

/**
 * Utility class which creates a reader and interpreter to read and then 
 * interpret the data from a file whose filename is passed via the 
 * constructor.  This class calls methods from the 'GeneralFileReader' to 
 * carry out the reading of the file into a string array.  Once the data is 
 * in the string array, this class will interpret the data from the string 
 * array based on the assumption that the read file follows the specified 
 * format for a "Player Information File".
 * 
 * @author Jonathan McFadden (mcfaddja@uw.edu)
 * @version 0.1
 */
public class PlayerInformationFileInterpreter {
    
    /** String with the name of the file to be read and interpreted. */
    private final String myFileName;
    
    /** String array with the raw data read in from the file. */
    private final String[] myRawData;
    
    /** BigInterger type to hold the user's UID imported from the file. */
    private BigInteger myUID;
    
    /** String with the name of the file that holds the user's public key. */
    private String myPubKeyFileName;
    
    /** String with the Algorithm used to generate the user's public key. */
    private String myPubKeyAlgo;
    
    /** String with the name of the file that holds the user's private key. */
    private String myPvtKeyFileName;
    
    /** String with the Algorithm used to generate the user's private key. */
    private String myPvtKeyAlgo;
    
    /** String with the Algorithm used to generate the hash of the file. */
    private String myHashAlgo;
    
    /** String holding the hash read in from the file. */
    private String myImportedHash;
    
    /** Instance of the GeneralFileReader class to read the raw file data. */
    final GeneralFileReader myGenReader;
    
    
    
    /**
     * At instantiation this immediately creates and executes, in sequence, 
     * both a reader and interpreter which will immediately read the file 
     * specified by the filename at instantiation, store the data in a string 
     * array, interpret the stored data based on the format of specified for 
     * "Player Information Files", and, upon the completion of instantiation, 
     * allow access to the interpreted data though several getter methods.
     * 
     * @param theFileName The filename of the file to be read and interpreted.
     */
    public PlayerInformationFileInterpreter(final String theFileName) {
        myFileName = theFileName;
        
        myGenReader = new GeneralFileReader(myFileName);
        myRawData = myGenReader.readItGetIt();
        
        readAndParseThem();
    } // END constructor

    /**
     * Private helper method called in the constructor and used to keep the 
     * code readable.
     */
    private void readAndParseThem() {
        final long tempLong = Integer.parseInt(myRawData[0]);
        myUID = BigInteger.valueOf(tempLong);
        
        myPubKeyFileName = myRawData[1];
        myPubKeyAlgo = myRawData[2];
        
        myPvtKeyFileName = myRawData[3];
        myPvtKeyAlgo = myRawData[4];
        
        myHashAlgo = myRawData[5];
        myImportedHash = myRawData[6];
    } // END readAndParseThem() PRIVATE HELPER METHOD
    
    
    /**
     * Returns the value of the UID imported from the input file.
     * 
     * @return myUID
     */
    public BigInteger getUID() {
        return myUID;
    }
    
    /**
     * Returns a string, as imported from the input file, with the name of the 
     * key file holding the public key of the player represented by the input 
     * file.
     * 
     * @return myPubKeyFileName
     */
    public String getPubKeyFileName() {
        return myPubKeyFileName;
    }
    
    /**
     * Returns a string, as imported from the input file, with the name of the 
     * algorithm used to generate the public key for the player represented by 
     * the input file.
     * 
     * @return myPubKeyAlgo
     */
    public String getPubKeyAlgo() {
        return myPubKeyAlgo;
    }
    
    /**
     * Returns a string, as imported from the input file, with the name of the 
     * key file holding the private key of the player represented by the input 
     * file.
     * 
     * @return myPvtKeyFileName
     */
    public String getPvtKeyFileName() {
        return myPvtKeyFileName;
    }
    
    /**
     * Returns a string, as imported from the input file, with the name of the 
     * algorithm used to generate the private key for the player represented by 
     * the input file.
     * 
     * @return myPvtKeyAlgo
     */
    public String getPvtKeyAlgo() {
        return myPvtKeyAlgo;
    }
    
    /**
     * Returns a string, as imported from the input file, with the name of the 
     * algorithm used to generate the hash of the file data which was included 
     * with the file itself.
     * 
     * @return myHashAlgo
     */
    public String getHashAlgo() {
        return myHashAlgo;
    }
    
    /**
     * Returns a string, as imported from the input file, which represents the 
     * value of the hash that was included with the file.
     * 
     * @return myImportedHash
     */
    public String getImportedHash() {
        return myImportedHash;
    }
    

} // END PlayerInformationFileInterpreter.java CLASS
