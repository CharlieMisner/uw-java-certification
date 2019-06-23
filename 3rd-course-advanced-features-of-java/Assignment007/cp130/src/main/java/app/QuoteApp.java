package app;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuoteApp {

    private static void getQuote(final String symbol, final String type) {
        HttpURLConnection connection = null;
        try {
            String urlStr = String.format(
                    "http://localhost:8080/StockQuote/StockQuoteServlet?type=%s&symbol=%s",
                    type,
                    symbol
            );

            URL url = new URL(urlStr);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();
            Reader reader = new InputStreamReader(inputStream);
            char buffer[] = new char[1000];
            int length = 0;
            while ((length = reader.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {
        String symbol = "EXPD";

        System.out.println("JSON:");
        getQuote(symbol, "json");
        System.out.println();
        System.out.println();

        System.out.println("Plain Text:");
        getQuote(symbol, "text");
        System.out.println();
        System.out.println();

        System.out.println("HTML:");
        getQuote(symbol, "html");
        System.out.println();
        System.out.println();

        System.out.println("XML:");
        getQuote(symbol, "xml");
        System.out.println();
        System.out.println();
    }
}
