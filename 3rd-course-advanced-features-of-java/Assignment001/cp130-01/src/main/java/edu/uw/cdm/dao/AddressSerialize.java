package edu.uw.cdm.dao;

import edu.uw.cdm.account.AccountCDM;
import edu.uw.cdm.account.AddressCDM;
import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.account.AccountException;
import edu.uw.ext.framework.account.Address;

import java.io.*;

public class AddressSerialize {

    Address address = null;
    private String accountFilePath;

    public AddressSerialize(Address address, String accountFilePath) {
        this.address = address;
        this.accountFilePath = accountFilePath;
    }

    public void write(){
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {

            fos = new FileOutputStream(this.accountFilePath + "/addressBinary");
            dos = new DataOutputStream(fos);

            dos.writeUTF(writeString(address.getCity()));
            dos.writeUTF(writeString(address.getState()));
            dos.writeUTF(writeString(address.getStreetAddress()));
            dos.writeUTF(writeString(address.getZipCode()));
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

    public Address read(){

        FileInputStream fis = null;
        DataInputStream dis = null;
        Address address = new AddressCDM();

        try {

            fis = new FileInputStream(this.accountFilePath + "/addressBinary");
            dis = new DataInputStream(fis);

            address.setCity(readString(dis.readUTF()));
            address.setState(readString(dis.readUTF()));
            address.setStreetAddress(readString(dis.readUTF()));
            address.setZipCode(readString(dis.readUTF()));

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

        return address;
    }

    private String writeString(String value){
        return value == null ? "null" : value;
    }

    private String readString(String value){
        return value == "null" ? null : value;
    }
}
