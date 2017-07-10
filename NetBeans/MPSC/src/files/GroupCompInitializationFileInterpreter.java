/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jamster
 */
public class GroupCompInitializationFileInterpreter {
    
    /** 
     * Integer constant representing the array index where the list of player 
     *  information starts in the input file.
     */
    private static final int LIST_START = 4;
    
    /** String constant for the IP address error message. */
    private static final String IP_ERR = "IP Address Error at index ";
    
    
    
    /** String with the name of the file to be read and interpreted. */
    private final String myFileName;
    
    /** String array with the raw data read in from the file. */
    private final String[] myRawData;
    
    /** A string representation of the function that will be computed. */
    private String myFuncStr;
    
    /** A string holding the name of the data-type used in the function. */
    private String myFuncDataType;
    
    /** A BigInteger to hold the value of the group initialization key. */
    private BigInteger myGrpCompInitKey;
    
    /** An integer holding the number of Players in the group. */
    private int myGrpSize;
    
    /** 
     * A 2D String array holding the initial interpretation of values in the 
     *  list of UIDs, IP addressees, and Public Keys for every player in the 
     *  group.
     */
    private String[][] myPlyrsNinfo;
    
    /** An array of BigIntegers holding the UIDs of every player. */
    private BigInteger[] myPlyrsUIDs;
    
    /** Array of InetAddress object holding the IP addresses of all players. */
    private InetAddress[] myPlyrsAddrs;
    
    /** 
     * An ArrayList of byte arrays to help import, convert, and store the 
     *  public keys of all the players as instances of the "PublicKey" class.
     */
    private List<byte[]> myPlyrPubKeyBytes;
    
    /** 
     * Array of KeyFactory objects to help import, convert, and store the 
     *  public keys of all the players as instances of the "PublicKey" class.
     */
    private KeyFactory[] myKeyFactories;
    
    /** Array of Public Key objects holding the public keys of every player. */
    private PublicKey[] myPlyrPubKeys;
    
    /** String with the Algorithm used to generate the hash of the file. */
    private String myHashAlgo;
    
    /** String holding the hash read in from the file. */
    private String myImportedHash;
    
    /** Instance of the GeneralFileReader class to read the raw file data. */
    final GeneralFileReader myGenReader;
    
    
    
    
    public GroupCompInitializationFileInterpreter(final String theFileName) {
        myFileName = theFileName;
        
        myGenReader = new GeneralFileReader(myFileName);
        myRawData = myGenReader.readItGetIt();
        
        readAndParseThem();
    }

    private void readAndParseThem() {
        myFuncStr = myRawData[0];
        myFuncDataType = myRawData[1];
        
        final String temp = myRawData[2];
        myGrpCompInitKey = new BigInteger(temp);
        
        myGrpSize = Integer.parseInt(myRawData[3]);
        
        loadPlyrData2D();
        loadPlyrUIDsNaddrs();
        loadPlyrKeysTasks();
    } // END readAndParseThem() PRIVATE HELPER METHOD

    /**
     * Private helper method for the CONSTRUCTOR.  This method is called by 
     *  readAndParseThem(), a private helper method which is directly called by 
     *  the CONSTRUCTOR.  This method, loadPlyrData2D(), parses data from the 
     *  raw input data into a 2D array of Strings, where the raw inport data is 
     *  read from the input file and stored in the myRawData String arrat by 
     *  the CONSTRUCTOR during initialization.  The 2D array of Strings parsed 
     *  by this file holds the initial interpretation of values in the 
     *  list of UIDs, IP addressees, and Public Keys for every player in the 
     *  group.
     */
    private void loadPlyrData2D() {
        myPlyrsNinfo = new String[myGrpSize][3];
        
        for (int i = 0; i < myGrpSize; i++) {
            for (int j = 0; j < 3; j++) {
                myPlyrsNinfo[i][j] = myRawData[LIST_START + i*3 + j];
            } // END for LOOP (INDEX j)
        } // END for LOOP (INDEX i)
    } // END loadPlyrData2D() PRIVATE HELPER METHOD

    /**
     * Private helper method for the CONSTRUCTOR.  This method is called by 
     *  readAndParseThem(), a private helper method which is directly called by 
     *  the CONSTRUCTOR.  This method, loadPlyrUIDsNaddrs(), 
     */
    private void loadPlyrUIDsNaddrs() {
        myPlyrsUIDs = new BigInteger[myGrpSize];
        myPlyrsAddrs = new InetAddress[myGrpSize];
        
        for (int i = 0; i < myGrpSize; i++) {
            myPlyrsUIDs[i] = new BigInteger(myPlyrsNinfo[i][0]);
            
            try {
                myPlyrsAddrs[i] = InetAddress.getByName(myPlyrsNinfo[i][1]);
            } catch (UnknownHostException e) {
                System.out.println(IP_ERR + i + e.getMessage());
            } // END try/catch BLOCK
        } // END for LOOP
    } // END loadPlyrUIDsNaddrs() PRIVATE HELPER METHOD

    private void loadPlyrKeysTasks() {
        
    }
    
    
    
    
    
    
    
}
