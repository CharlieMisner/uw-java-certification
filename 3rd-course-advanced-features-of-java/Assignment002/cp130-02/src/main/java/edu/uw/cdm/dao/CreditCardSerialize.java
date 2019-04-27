package edu.uw.cdm.dao;

import edu.uw.cdm.account.AddressCDM;
import edu.uw.cdm.account.CreditCardCDM;
import edu.uw.ext.framework.account.Address;
import edu.uw.ext.framework.account.CreditCard;

import java.io.*;

public class CreditCardSerialize {

    CreditCard creditCard = null;
    private String accountFilePath;

    public CreditCardSerialize(CreditCard creditCard, String accountFilePath) {
        this.creditCard = creditCard;
        this.accountFilePath = accountFilePath;
    }

    public void write(){
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {

            fos = new FileOutputStream(this.accountFilePath + "/creditCardBinary");
            dos = new DataOutputStream(fos);

            dos.writeUTF(writeString(creditCard.getAccountNumber()));
            dos.writeUTF(writeString(creditCard.getExpirationDate()));
            dos.writeUTF(writeString(creditCard.getHolder()));
            dos.writeUTF(writeString(creditCard.getIssuer()));
            dos.writeUTF(writeString(creditCard.getType()));
            dos.flush();

        }
        catch (FileNotFoundException fnfe) {
            System.out.println("File not found" + fnfe);
        }
        catch (IOException ioe) {
            System.out.println("Error while writing to file" + ioe);
        }
        finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
            catch (Exception e) {
                System.out.println("Error while closing streams" + e);
            }
        }
    }

    public CreditCard read(){

        FileInputStream fis = null;
        DataInputStream dis = null;
        CreditCard creditCard = new CreditCardCDM();

        try {

            fis = new FileInputStream(this.accountFilePath + "/creditCardBinary");
            dis = new DataInputStream(fis);

            creditCard.setAccountNumber(readString(dis.readUTF()));
            creditCard.setExpirationDate(readString(dis.readUTF()));
            creditCard.setHolder(readString(dis.readUTF()));
            creditCard.setIssuer(readString(dis.readUTF()));
            creditCard.setType(readString(dis.readUTF()));

        }
        catch (FileNotFoundException fnfe) {

        }
        catch (IOException ioe) {
            System.out.println("Error while Reading file" + ioe);
        }
        finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            }
            catch (Exception e) {
                System.out.println("Error while closing streams" + e);
            }
        }

        return creditCard;
    }

    private String writeString(String value){
        return value == null ? "null" : value;
    }

    private String readString(String value){
        return value == "null" ? null : value;
    }
}
