package edu.uw.cdm.web;

import javax.servlet.Servlet;
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

    private final String replyFmt = "<html><head><title>Greetings</title>"
            + "<body><h1>%s</h1></body></html>";

    private String greeting = "And it's gone...";

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

        String reply = String.format(replyFmt, greeting);
        response.setContentType("text/html");
        LOGGER.info(request.getMethod());
        response.setContentLength(reply.length());
        PrintWriter wtr = response.getWriter();
        wtr.print(reply);
    }
}
