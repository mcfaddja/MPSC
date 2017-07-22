/*
 * Part of a program to implement a Multi-Party Secure Computing protocol.
 */
package files;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jamster
 */
public class PlayerSecretDataFileReader {
    
    private final String myFileName;
    
    private final String myDataType;
    
    private final String[] myRawData;
    
    /** Instance of the GeneralFileReader class to read the raw file data. */
    private final GeneralFileReader myGenReader;
    
    private List myList;
    
    private boolean myDataTypeTest;
    
    
    
    
    
    
    public PlayerSecretDataFileReader(final String theFileName, final String theDataType) {
        myFileName = theFileName;
        myDataType = theDataType;
        
        myGenReader = new GeneralFileReader(myFileName);
        myRawData = myGenReader.readItGetIt();
        
        convertData();
    }
    
    private void convertData() {
        myList = new ArrayList<>();
        myDataTypeTest = true;
        
        switch (myDataType) {
            case "int":
                for (final String str : myRawData) {
                    myList.add(Integer.parseInt(str));
                } // END for LOOP   
                break;
            case "double":
                for (final String str : myRawData) {
                    myList.add(Double.parseDouble(str));
                } // END for LOOP   
                break;
            case "float":
                for (final String str : myRawData) {
                    myList.add(Float.parseFloat(str));
                } // END for LOOP   
                break;
            case "BigInteger":
                for (final String str : myRawData) {
                    final BigInteger temp = new BigInteger(str);
                    myList.add(temp);
                } // END for LOOP   
                break;
            case "BigDecimal":
                for (final String str : myRawData) {
                    final BigDecimal temp = new BigDecimal(str);
                    myList.add(temp);
                } // END for LOOP   
                break;
            default:
                myDataTypeTest = false;
                System.out.println("Invalid Data Type.");
                break;
        } // END switch STATEMENT
    } // END convertData() PRIVATE HELPER METHOD
    
    
    
// PUBLIC METHODS
  // GETTERS
    
    public String getFileName() {
        return myFileName;
    }
    
    public String getDataType() {
        return myDataType;
    }
    
    public List getList() {
        List tmpList = new ArrayList<>();
        
        if (myDataTypeTest) {
            tmpList.addAll(myList);
        } else {
            tmpList = null;
        }
        
        return tmpList;
    }
    
        
    public boolean getDataTypeTest() {
        return myDataTypeTest;
    }
    
    
    
    
}
