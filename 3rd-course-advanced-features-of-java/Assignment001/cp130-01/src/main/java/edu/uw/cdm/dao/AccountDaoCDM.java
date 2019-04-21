package edu.uw.cdm.dao;

import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.dao.AccountDao;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AccountDaoCDM implements AccountDao, AutoCloseable{

    private Account account;
    private String accountFilePath;
    private final String ACCOUNT_FILE_NAME = "/account.dat";
    private final String ADDRESS_FILE_NAME = "/address.dat";
    private final String CC_FILE_NAME = "/credit-card.dat";

    public AccountDaoCDM() {
    }

    public void close() {
    };

    public void deleteAccount(String accountName) {
        this.accountFilePath = String.format("./target/accounts/%s", accountName);
        Path path = Paths.get(this.accountFilePath);
        if(Files.exists(path)){
            File accountsDirectory = new File(this.accountFilePath);
            FileSystemUtils.deleteRecursively(accountsDirectory);
        }
    };

    public void reset(){
        this.account = null;
        String pathName = "./target/accounts";
        Path path = Paths.get(pathName);
        if(Files.exists(path)){
            File accountsDirectory = new File(pathName);
            FileSystemUtils.deleteRecursively(accountsDirectory);
        }
    };

    public Account getAccount(String accountName) {
        this.accountFilePath = String.format("./target/accounts/%s", accountName);
        AccountsSer accountHandler = new AccountsSer(this.account, this.accountFilePath, this.ACCOUNT_FILE_NAME);
        Account account = (Account)accountHandler.read();

        return account;
    }

    @Override
    public void setAccount(Account account) {
        this.createAccountsDirectory();
        this.account = account;
        this.createAccountDirectory(this.account.getName());
        AccountsSer accountHandler = new AccountsSer(this.account, this.accountFilePath, this.ACCOUNT_FILE_NAME);
        accountHandler.write();
        AccountsSer addressHandler = new AccountsSer(this.account.getAddress(), this.accountFilePath, this.ADDRESS_FILE_NAME);
        addressHandler.write();
        AccountsSer creditCardHandler = new AccountsSer(this.account, this.accountFilePath, this.CC_FILE_NAME);
        creditCardHandler.write();
    }

    private void createAccountsDirectory(){
        String pathName = "./target/accounts";
        Path path = Paths.get(pathName);
        if(!Files.exists(path)){
            File accountsDirectory = new File(pathName);
            accountsDirectory.mkdir();
        }
    }

    private void createAccountDirectory(String accountName){
        this.accountFilePath = String.format("./target/accounts/%s", accountName);
        Path path = Paths.get(this.accountFilePath);
        if(!Files.exists(path)){
            File accountsDirectory = new File(this.accountFilePath);
            accountsDirectory.mkdir();
        }
    }
}
