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
import java.util.ArrayList;
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
        loadPlyrKeysTasks();
    }

    
    /**
     * Private helper method for the CONSTRUCTOR.  This method is called 
     *  directly by the CONSTRUCTOR so that this method can import the String 
     *  representation of the GROUP-FUNCTION & the DATA-TYPE used by the GROUP-
     *  -FUNCTION into their own String variables, import and parse from a  
     *  String to a BigInteger the GROUP-COMPUTATION-INITIALIZATION-KEY into 
     *  its own BigInteger variable, import and parse from a String to an int 
     *  the GROUP-SIZE into its own integer(int) variable, and then itself call 
     *  a series of additional private helper methods for performing tasks 
     *  related to the importation, parsing, and storage of raw input file 
     *  data, UIDs, and IP addresses of the players.  All input data (or raw 
     *  data) used by this method, or any of the helper methods it calls, is 
     *  obtained from the raw file data read by the CONSTRUCTOR during 
     *  initialization.  The private helpers called by this method are the 
     *  loadPlyrData2D(), loadPlyrUIDs(), and loadPlyrAddrs().
     */
    private void readAndParseThem() {
        myFuncStr = myRawData[0];
        myFuncDataType = myRawData[1];
        
        final String temp = myRawData[2];
        myGrpCompInitKey = new BigInteger(temp);
        
        myGrpSize = Integer.parseInt(myRawData[3]);
        
        loadPlyrData2D();
        loadPlyrUIDs();
        loadPlyrAddrs();
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
     *  the CONSTRUCTOR.  This method, loadPlyrUIDs(), imports the data on the 
     *  UIDs of players into an array specifically for holding player UIDs. 
     *  The data is imported from the array loaded by the loadPlyrData2D() 
     *  private helper method parsed to the BigInteger data type, and then 
     *  loaded into the array specifically for holding player UIDs, which is an 
     *  array of BigIntegers.  This method is indirectly called by the 
     *  CONSTRUCTOR, as this method is called directly by the runAndParseThem() 
     *  private helper method which the CONSTRUCTOR directly calls.
     */
    private void loadPlyrUIDs() {
        myPlyrsUIDs = new BigInteger[myGrpSize];
        
        for (int i = 0; i < myGrpSize; i++) {
            myPlyrsUIDs[i] = new BigInteger(myPlyrsNinfo[i][0]);
        } // END for LOOP
    } // END loadPlyrUIDs() PRIVATE HELPER METHOD
    
    /**
     * Private helper method for the CONSTRUCTOR.  This method is called by 
     *  readAndParseThem(), a private helper method which is directly called by 
     *  the CONSTRUCTOR.  This method, loadPlyrAddrs(), imports the data on the 
     *  IP addresses of players into an array specifically for holding player 
     *  IP addresses.  The data is imported from the array loaded by the 
     *  loadPlyrData2D() private helper method parsed to the InetAddress data 
     *  type, and then loaded into the array specifically for holding player 
     *  IP addresses, which is an array of InetAddresses.  This method is 
     *  indirectly called by the CONSTRUCTOR, as this method is called directly 
     *  by the runAndParseThem() private helper method which the CONSTRUCTOR 
     *  directly calls.
     */
    private void loadPlyrAddrs() {
        myPlyrsAddrs = new InetAddress[myGrpSize];
        
        for (int i = 0; i < myGrpSize; i++) {
            
            try {
                myPlyrsAddrs[i] = InetAddress.getByName(myPlyrsNinfo[i][1]);
            } catch (UnknownHostException e) {
                System.out.println(IP_ERR + i + e.getMessage());
            } // END try/catch BLOCK
            
        } // END for LOOP
    } // END loadPlyrAddrs() PRIVATE HELPER METHOD

    /**
     * Private helper method for the CONSTRUCTOR.  This method is called 
     *  directly by the CONSTRUCTOR so that this method itself can call a 
     *  series of additional private helper methods for performing tasks 
     *  related to the importation, generation, and storage of cryptographic 
     *  keys for each player.
     */
    private void loadPlyrKeysTasks() {
        createByteArrays();
    }
    
    private void createByteArrays() {
        myPlyrPubKeyBytes = new ArrayList<byte[]>();
        
        for (int i = 0; i < myGrpSize; i++) {
            myPlyrPubKeyBytes.add(myPlyrsNinfo[i][2].getBytes());
        }
    }
    
    
    
    
    
    
    
    
}
