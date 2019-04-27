package edu.uw.cdm.dao;

import edu.uw.cdm.account.AccountCDM;
import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.account.AccountException;

import java.io.*;

public class AccountSerialize {

    Account account = null;
    private String accountFilePath;
    public AccountSerialize(Account account, String accountFilePath) {
        this.account = account;
        this.accountFilePath = accountFilePath;
    }

    public void write(){
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {

            fos = new FileOutputStream(this.accountFilePath + "/accountBinary");
            dos = new DataOutputStream(fos);

            dos.writeUTF(writeString(account.getName()));
            dos.writeInt(account.getPasswordHash().length);
            dos.write(account.getPasswordHash());
            dos.writeInt(account.getBalance());
            dos.writeUTF(writeString(account.getFullName()));
            dos.writeUTF(writeString(account.getPhone()));
            dos.writeUTF(writeString(account.getEmail()));
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

    public Account read(){

        FileInputStream fis = null;
        DataInputStream dis = null;
        Account account = new AccountCDM();

        try {

            fis = new FileInputStream(this.accountFilePath + "/accountBinary");
            dis = new DataInputStream(fis);


            account.setName(readString(dis.readUTF()));
            int passLength = dis.readInt();
            byte[] passBytes = new byte[passLength];
            dis.readFully(passBytes);
            account.setPasswordHash(passBytes);
            account.setBalance(dis.readInt());
            account.setFullName(readString(dis.readUTF()));
            account.setPhone(readString(dis.readUTF()));
            account.setEmail(readString(dis.readUTF()));

        }
        catch (FileNotFoundException fnfe) {

        }
        catch (IOException ioe) {
            System.out.println("Error while Reading file" + ioe);
        }
        catch (AccountException e) {
            System.out.println("Account Exception");
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

        return account;
    }

    private String writeString(String value){
        return value == null ? "null" : value;
    }

    private String readString(String value){
        return value == "null" ? null : value;
    }

}
