package edu.uw.cdm.web;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class TextResponseWrapper extends HttpServletResponseWrapper {
    private final CharArrayWriter output;

    public TextResponseWrapper(final HttpServletResponse response) {
        super(response);
        output = new CharArrayWriter();
    }

    public PrintWriter getWriter() {
        return new PrintWriter(output);
    }

    @Override
    public String toString() {
        return output.toString();
    }
}
