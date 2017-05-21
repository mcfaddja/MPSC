/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.math.BigInteger;

/**
 *
 * @author jamster
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
    
    
    
    public PlayerInformationFileInterpreter(final String theFileName) {
        myFileName = theFileName;
        
        myGenReader = new GeneralFileReader(myFileName);
        myRawData = myGenReader.readItGetIt();
        
        readAndParseThem();
    }

    private void readAndParseThem() {
        final long tempLong = Integer.parseInt(myRawData[0]);
        myUID = BigInteger.valueOf(tempLong);
        
        myPubKeyFileName = myRawData[1];
        myPubKeyAlgo = myRawData[2];
        
        myPvtKeyFileName = myRawData[3];
        myPvtKeyAlgo = myRawData[4];
        
        myHashAlgo = myRawData[5];
        myImportedHash = myRawData[6];
    }
    
    
    public BigInteger getUID() {
        final BigInteger aUID = myUID;
        
        return aUID;
    }
    
    
    public String getPubKeyFileName() {
        return myPubKeyFileName;
    }
    
    
    public String getPubKeyAlgo() {
        return myPubKeyAlgo;
    }
    
    
    public String getPvtKeyFileName() {
        return myPvtKeyFileName;
    }
    
    
    public String getPvtKeyAlgo() {
        return myPvtKeyAlgo;
    }
    
    
    public String getHashAlgo() {
        return myHashAlgo;
    }
    
    
    public String getImportedHash() {
        return myImportedHash;
    }
    
    
    
    
    
}
