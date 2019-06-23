package edu.uw.cdm.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import edu.uw.ext.quote.AlphaVantageQuote;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Logger;

public class QuoteTransformFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(StockQuoteServlet.class.getName());

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        TextResponseWrapper wrapper = new TextResponseWrapper((HttpServletResponse)response);
        filterChain.doFilter(request, wrapper);

        Document xml = this.parseXmlStringToXmlDoc(wrapper.toString());
        String symbol = xml.getElementsByTagName("symbol").item(0).getTextContent();
        String price = xml.getElementsByTagName("price").item(0).getTextContent();

        String dataFormat = request.getParameter("type");
        LOGGER.info(symbol);
        LOGGER.info(price);
        String responseString;

        switch (dataFormat){
            case "xml":
                response.setContentType("text/xml");
                responseString = wrapper.toString();
                response.setContentLength(responseString.length());
                break;

            case "json":
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                responseString = this.getJsonResponse(symbol, price);
                response.setContentLength(responseString.length());
                break;

            case "html":
                response.setContentType("text/html");
                responseString = this.getHtmlResponse(symbol, price);
                response.setContentLength(responseString.length());
                break;

            case "text":
                response.setContentType("text/text");
                responseString = this.getPlainTextResponse(symbol, price);
                response.setContentLength(responseString.length());
                break;

            default:
                response.setContentType("text/xml");
                responseString = wrapper.toString();
                response.setContentLength(responseString.length());
                break;
        }
        response.getWriter().write(responseString);
    }

    public void destroy() {

    }

    private Document parseXmlStringToXmlDoc(String xmlString){
        Document doc = null;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(xmlString));

            doc = builder.parse(src);
        } catch (ParserConfigurationException e) {
            LOGGER.info(e.getMessage());
        } catch (SAXException e) {
            LOGGER.info(e.getMessage());
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return doc;
    }

    private String getPlainTextResponse(String symbol, String price){
        return String.format("symbol: %s, price: %s", symbol, price);
    }

    private String getJsonResponse(String symbol, String price){
        JsonRequestFilter jsonRequestFilter = new JsonRequestFilter(symbol, price);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(jsonRequestFilter);
        } catch (JsonProcessingException e) {
            LOGGER.info(e.getMessage());
        }
        return jsonString;
    }

    private String getHtmlResponse(String symbol, String price){
        String htmlString = "<div><strong>Symbol: </strong><span>%s</span></div>" +
                "<div><strong>Price: </strong>%s</div>";

        return String.format(htmlString, symbol, price);
    }
}
