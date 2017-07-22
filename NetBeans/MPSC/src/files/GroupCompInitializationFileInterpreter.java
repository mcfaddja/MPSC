/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

/**
 *
 * @author jamster
 */
public class GroupCompInitializationFileInterpreter {
    
// CLASS CONSTANTS
    /** 
     * Integer constant representing the array index where the list of player 
     *  information starts in the input file.
     */
    private static final int LIST_START = 4;
    
    /** String constant for the IP address error message. */
    private static final String IP_ERR = "IP Address Error at index ";
    
    
// CLASS VARIABLES
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
    private ArrayList<byte[]> myPlyrPubKeyBytes;

    /** Array of Public Key objects holding the public keys of every player. */
    private PublicKey[] myPlyrPubKeys;
    
    /** Array of Signature objects holding the signatures of every player. */
    private Signature[] myPlyrSigs;
    
    /** String with the Algorithm used to generate the hash of the file. */
    private String myHashAlgo;
    
    /** String holding the hash read in from the file. */
    private String myImportedHash;
    
    /** Byte Array holding the hash read in from the file. */
    private byte[] myImportedHashBytes;
    
//    /** MessageDigest Object holding the hash read in from the file. */
//    private MessageDigest myImportedHashMsgDigest;
    
    /** Byte Array holding the hash read in from the file. */
    private byte[] myComputedHashBytes;
    
    /** Instance of the GeneralFileReader class to read the raw file data. */
    final GeneralFileReader myGenReader;
    
    
    
// CONSTRUCTOR(S)
    public GroupCompInitializationFileInterpreter(final String theFileName) {
        myFileName = theFileName;
        
        myGenReader = new GeneralFileReader(myFileName);
        myRawData = myGenReader.readItGetIt();
        
        readAndParseThem();
        loadPlyrKeysTasks();
        hashTaskRunner();
    }
    
    
// PRIVATE HELPER METHODS FOR THE CONSTRUCTOR
    
  // PRIVATE HELPERS FOR IMPORTING RAW PLAYER DATA
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

  // PRIVATE HELPERS RELATED TO PLAYER KEYS
    /**
     * Private helper method for the CONSTRUCTOR.  This method is called 
     *  directly by the CONSTRUCTOR so that this method itself can call a 
     *  series of additional private helper methods for performing tasks 
     *  related to the importation, generation, and storage of cryptographic 
     *  keys for each player.
     */
    private void loadPlyrKeysTasks() {
        makeByteArrays();
        makePubKeys();
    } // END loadPlyrKeysTasks() PRIVATE METHOD
    
    /**
     * Private helper method for the CONSTRUCTOR.  This method is called by 
     *  loadPlyrKeysTasks(), a private helper method which is directly called 
     *  by the CONSTRUCTOR.  This method, makeByteArrays(), gets the Strings 
     *  holding the encoded public keys for each player, converts the Strings 
     *  to byte arrays and stores them in a array of byte arrays for later use.
     */
    private void makeByteArrays() {
        myPlyrPubKeyBytes = new ArrayList<byte[]>();
        
        for (int i = 0; i < myGrpSize; i++) {
            myPlyrPubKeyBytes.add(myPlyrsNinfo[i][2].getBytes());
        }
    } // END makeByteArrays() PRIVATE HELPER METHOD
    
    /**
     * Private helper method for the CONSTRUCTOR.  This method is called by 
     *  loadPlyrKeysTasks(), a private helper method which is directly called 
     *  by the CONSTRUCTOR.  This method, makePubKeys(), uses the array of byte 
     *  arrays holding the encoded public keys for the players to generate 
     *  PublicKey and Signature objects for each of the players, storing them 
     *  in separate arrays with one for PublicKey objects and the other for 
     *  Signature objects.
     */
    private void makePubKeys() {
        // Get size of byte arrays with encoded public keys
        int tempSize = myPlyrPubKeyBytes.get(0).length;
        
        // Create a 2D byte array to hold the keys of all players [plyr][key]
        byte[][] tempByteKeyArray = new byte[myGrpSize][tempSize];
        // Covert imported player encoded public keys from Strings 
        //      and load into "tempByteKeyArray" for later use and conversion.
        for (int i = 0; i < myGrpSize; i++) {
            tempByteKeyArray[i] = myPlyrPubKeyBytes.get(i);
        } // END FIRST for LOOP OF METHOD
        
        // Create arrays for the public keys and signatures of each player
        myPlyrPubKeys = new PublicKey[myGrpSize];
        myPlyrSigs = new Signature[myGrpSize];
        // Load the arrays for the public keys and signatures of each player
        for (int i = 0; i < myGrpSize; i++) {
            // Temporary KeyFactories and X509EncodedKeySpec variables
            KeyFactory tempKeyFactory;
            X509EncodedKeySpec tempKeySpec;
            
            try {
                tempKeyFactory = KeyFactory.getInstance("RSA");
                tempKeySpec = new X509EncodedKeySpec(tempByteKeyArray[i]);
                
                myPlyrPubKeys[i] = tempKeyFactory.generatePublic(tempKeySpec);
                myPlyrSigs[i] = Signature.getInstance("RSA");
                myPlyrSigs[i].initVerify(myPlyrPubKeys[i]);
            } // END try BLOCK
            catch (NoSuchAlgorithmException theException1) {
                System.out.println("No Such Algrogristm Exception for a "
                                        + "CRYPTOGRAPHIC ALGORITHM " 
                                        + theException1.getMessage());
            } // END THIRD catch BLOCK
            catch (InvalidKeySpecException theException2) {
                System.out.println("Invaide Key Spec Exception from a "
                                        + "X509EncodedKeySpec KEY SPEC " 
                                        + theException2.getMessage());
            } // END SECOND catch BLOCK
            catch (InvalidKeyException theException3) {
                System.out.println("Invaide Key Exception from a "
                                        + "PUBLIC KEY KEY " 
                                        + theException3.getMessage());
            } // END THIRD catch BLOCK
            // END try/catch BLOCK
        } // END SECOND for LOOP IN METHOD
        
    } // END makePubKeys() PRIVATE HELPER METHOD
    
  // PRIVATE HELPERS FOR HASHES
    /**
     * Private helper method for the CONSTRUCTOR.  This method is called 
     *  directly by the CONSTRUCTOR so that this method itself can call a 
     *  series of additional private helper methods for performing tasks 
     *  related to the importation and conversion of the hash of the file 
     *  supplied by the file.
     */
    private void hashTaskRunner() {
        importHashAlgo();
        importGivenHash();
    }
    
    /**
     * Private helper method for the CONSTRUCTOR.  This method is called by 
     *  hashTaskRunner(), private helper method which is directly called 
     *  by the CONSTRUCTOR.  This method, importHashAlgo(), imports and stores 
     *  the String holding the hash algorithm used to generate the file hash 
     *  stored in the file.
     */
    private void importHashAlgo() {
        int tempCnt = 4 + 3 * myGrpSize;
        
        myHashAlgo = myRawData[tempCnt];
    }
    
    /**
     * Private helper method for the CONSTRUCTOR.  This method is called by 
     *  hashTaskRunner(), private helper method which is directly called 
     *  by the CONSTRUCTOR.  This method, importGivenHash(), imports the file 
     *  hash supplied by the file into a String variable and then converts that 
     *  String variable to a byte array.
     */
    private void importGivenHash() {
        int tempCnt = 4 + 3 * myGrpSize;
        myImportedHash = myRawData[tempCnt + 1];
        
        myImportedHashBytes = myImportedHash.getBytes();
    }
    
    
    
    
    
    
}
