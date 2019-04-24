package edu.uw.cdm.dao;

import edu.uw.ext.framework.account.Account;

import java.io.*;

public class AccountSerialize {

    Account account = null;
    private String accountFilePath;
    private String fileName;

    public AccountSerialize(Account account, String accountFilePath, String fileName) {
        this.account = account;
        this.accountFilePath = accountFilePath;
        this.fileName = fileName;
    }

    public void write(){
        ByteArrayOutputStream bos;
        ObjectOutputStream oos;
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {

            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            fos = new FileOutputStream(this.accountFilePath + this.fileName);
            dos = new DataOutputStream(fos);

            oos.writeObject(this.account);
            oos.flush();
            byte[] accountBytes = bos.toByteArray();
            dos.write(accountBytes);

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

    public Object read(){

        ByteArrayInputStream bis;
        ObjectInputStream ois;
        FileInputStream fis = null;
        DataInputStream dis = null;
        Object object = null;

        try {
            File file = new File(this.accountFilePath + this.fileName);

            fis = new FileInputStream(this.accountFilePath + this.fileName);
            dis = new DataInputStream(fis);

            byte[] byteArray = new byte[(int) file.length()];
            fis.read(byteArray);

            bis = new ByteArrayInputStream(byteArray);
            ois = new ObjectInputStream(bis);

            try {
                object = ois.readObject();
            } catch (ClassNotFoundException exception){

            }

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

        return object;
    }

}
