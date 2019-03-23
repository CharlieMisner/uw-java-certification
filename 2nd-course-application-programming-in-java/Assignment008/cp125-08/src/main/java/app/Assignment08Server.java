package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.net.server.InvoiceServer;
import com.scg.util.ListFactory;

import java.util.ArrayList;
import java.util.List;

public class Assignment08Server {

    public static final int DEFAULT_PORT = 10888;
    private static List<ClientAccount> accounts = new ArrayList<>();
    private static List<Consultant> consultants = new ArrayList<>();

    public static void main(String args[]){
        ListFactory.populateLists(accounts, consultants, new ArrayList<>());
        InvoiceServer invoiceServer = new InvoiceServer(DEFAULT_PORT, accounts, consultants, "test");
        invoiceServer.run();
    }
}
