package edu.uw.cdm.account;

import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.account.AccountException;
import edu.uw.ext.framework.account.AccountFactory;
import edu.uw.ext.framework.account.AccountManager;
import edu.uw.ext.framework.dao.AccountDao;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AccountManagerCDM implements AccountManager {

    private AccountDao accountDao;
    private AccountFactory accountFactory = new AccountFactoryCDM();
    private Account persistedAccount;
    private boolean daoIsOpen = true;

    public AccountManagerCDM(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void close() {
        try {
            this.accountDao.close();
            this.daoIsOpen = false;
        } catch (AccountException e) {
            System.out.println(e.getMessage());
        }
    };

    public Account createAccount(String accountName, String password, int balance)throws AccountException {

        Account account = this.accountFactory.newAccount(accountName, this.hashPassword(password), balance);

        try{
            this.checkDuplicateUserName(accountName);
            accountDao.setAccount(account);
        } catch (AccountException exception){
            throw exception;
        }

        return account;
    };

    public void deleteAccount(String accountName) {
        try {
            accountDao.deleteAccount(accountName);
        } catch (AccountException e){
            e.getMessage();
        }

    };

    public Account getAccount(String accountName) {

        return accountDao.getAccount(accountName);

    };

    public void persist(Account account) {
        try {
            accountDao.setAccount(account);
        } catch (AccountException e){

        }

    };

    public boolean validateLogin(String accountName, String password) {
        byte[] enteredPassword = this.hashPassword(password);
        Account account = accountDao.getAccount(accountName);
        try{
            byte[] storedPassword = account.getPasswordHash();
            return Arrays.equals(enteredPassword, storedPassword);
        } catch (NullPointerException ex){
            return false;
        }
    };

    private void checkDuplicateUserName(String accountName) throws AccountException {
        String accountFilePath = String.format("./target/accounts/%s", accountName);
        Path path = Paths.get(accountFilePath);
        if(Files.exists(path)){
            throw new AccountException("That username already exists.");
        }
    }

    private byte[] hashPassword(String password) {
        byte[] hashedPassword = new byte[40];
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e){

        }
        return hashedPassword;
    }
}
