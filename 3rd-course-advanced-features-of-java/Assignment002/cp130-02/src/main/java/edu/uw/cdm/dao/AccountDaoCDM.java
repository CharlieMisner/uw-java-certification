package edu.uw.cdm.dao;

import edu.uw.cdm.account.AddressCDM;
import edu.uw.cdm.account.CreditCardCDM;
import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.account.Address;
import edu.uw.ext.framework.account.CreditCard;
import edu.uw.ext.framework.dao.AccountDao;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AccountDaoCDM implements AccountDao, AutoCloseable{

    private Account account;
    private String accountFilePath;

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
        this.account = null;
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
        Account binAccount = null;
        Path pathToAccount = Paths.get(accountFilePath);
        if(Files.exists(pathToAccount)){
            AccountSerialize accountSerialize = new AccountSerialize(this.account, this.accountFilePath);
            binAccount = accountSerialize.read();

            Path pathToAddress = Paths.get(this.accountFilePath + "/addressBinary");
            if(Files.exists(pathToAddress)){
                AddressSerialize addressSerialize = new AddressSerialize(new AddressCDM(), this.accountFilePath);
                Address binAddress = addressSerialize.read();
                binAccount.setAddress(binAddress);
            }

            Path pathToCreditCard = Paths.get(this.accountFilePath + "/creditCardBinary");
            if(Files.exists(pathToCreditCard)){
                CreditCardSerialize creditCardSerialize = new CreditCardSerialize(new CreditCardCDM(), this.accountFilePath);
                CreditCard binCreditCard = creditCardSerialize.read();
                binAccount.setCreditCard(binCreditCard);
            }
        }

        return binAccount;
    }

    @Override
    public void setAccount(Account account) {
        this.deleteAccount(account.getName());
        this.createAccountsDirectory();
        this.account = account;
        this.createAccountDirectory(this.account.getName());
        AccountSerialize accountSerialize = new AccountSerialize(this.account, this.accountFilePath);
        accountSerialize.write();

        if(this.account.getAddress() != null){
            AddressSerialize addressSerialize = new AddressSerialize(this.account.getAddress(), this.accountFilePath);
            addressSerialize.write();
        }
        if(this.account.getCreditCard() != null){
            CreditCardSerialize creditCardSerialize = new CreditCardSerialize(this.account.getCreditCard(), this.accountFilePath);
            creditCardSerialize.write();
        }


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
