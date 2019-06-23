package edu.uw.cdm.web;

import edu.uw.ext.quote.AlphaVantageQuote;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class StockQuoteServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(StockQuoteServlet.class.getName());

    public void init(final ServletConfig config) throws ServletException {
    }

    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {
        serviceRequest(request, response);
    }

    protected void doPost(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {
        serviceRequest(request, response);
    }

    void serviceRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String price = "0";
        try {
            price = String.valueOf(AlphaVantageQuote.getQuote(request.getParameter("symbol")).getPrice());
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        String reply = this.requestToXml(request, price);
        response.setContentType("text/xml");
        response.setContentLength(reply.length());
        PrintWriter wtr = response.getWriter();
        wtr.print(reply);
    }

    private String requestToXml(HttpServletRequest request, String price){
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><stock><symbol>%s</symbol><price>%s</price></stock>";
        String formattedXmlString = String.format(xmlString, request.getParameter("symbol"), price);
        return formattedXmlString;
    }
}
