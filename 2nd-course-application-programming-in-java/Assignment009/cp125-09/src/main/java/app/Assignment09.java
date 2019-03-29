package app;

import com.scg.domain.TimeCard;
import com.scg.net.client.InvoiceClient;
import com.scg.util.ListFactory;

import java.util.ArrayList;
import java.util.List;

public class Assignment08 {

    private static List<TimeCard> timeCards = new ArrayList<>();
    public static final int DEFAULT_PORT = 10888;
    public static final String DEFAULT_HOST = "127.0.0.1";

    public static void main(String args[]){
        ListFactory.populateLists(new ArrayList<>(), new ArrayList<>(), timeCards);
        InvoiceClient client = new InvoiceClient(DEFAULT_HOST, DEFAULT_PORT, timeCards);
        client.run();
    }
}
