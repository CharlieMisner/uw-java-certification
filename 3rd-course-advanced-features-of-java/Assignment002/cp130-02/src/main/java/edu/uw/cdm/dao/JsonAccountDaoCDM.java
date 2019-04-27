package edu.uw.cdm.dao;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.uw.cdm.account.AccountCDM;
import edu.uw.cdm.account.AddressCDM;
import edu.uw.cdm.account.CreditCardCDM;
import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.account.Address;
import edu.uw.ext.framework.account.CreditCard;
import edu.uw.ext.framework.dao.AccountDao;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonAccountDaoCDM implements AccountDao {
    private Account account;

    public JsonAccountDaoCDM() {
    }

    public void close() {
    };

    public void deleteAccount(String accountName) {
        String jsonAccountFilePath = String.format("./target/accounts/%s.json", accountName);
        Path jsonPath = Paths.get(jsonAccountFilePath);
        if(Files.exists(jsonPath)){
            File accountsDirectory = new File(jsonAccountFilePath);
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

        String jsonAccountFilePath = String.format("./target/accounts/%s.json", accountName);
        Path jsonPath = Paths.get(jsonAccountFilePath);
        File jsonFile = new File(jsonAccountFilePath);

        SimpleModule module = new SimpleModule();
        module.addAbstractTypeMapping(Address.class, AddressCDM.class);
        module.addAbstractTypeMapping(CreditCard.class, CreditCardCDM.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);

        Account jsonAccount = new AccountCDM();

        if (Files.exists(jsonPath)) {
            try {
                jsonAccount = objectMapper.readValue(jsonFile, AccountCDM.class);
            } catch (FileNotFoundException e){

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            jsonAccount = null;
        }

        return jsonAccount;
    }

    @Override
    public void setAccount(Account account) {
        this.deleteAccount(account.getName());

        this.createAccountsDirectory();
        this.account = account;

        String jsonFilePath = String.format("./target/accounts/%s.json", account.getName());
        Path path = Paths.get(jsonFilePath);
        File jsonFile = new File(jsonFilePath);

        ObjectMapper mapper = new ObjectMapper();


        if (!Files.exists(path)) {
            try{
                mapper.writeValue(jsonFile, this.account);
            } catch (IOException e) {
            }
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

}
