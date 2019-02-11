package com.scg.domain;

import com.scg.util.Address;

import java.time.LocalDate;

public class InvoiceHeader {

    private String businessName;
    private Address businessAddress;
    private ClientAccount client;
    private java.time.LocalDate invoiceDate;
    private java.time.LocalDate invoiceForMonth;

    public InvoiceHeader(String businessName, Address businessAddress, ClientAccount client, LocalDate invoiceDate, LocalDate invoiceForMonth) {
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.client = client;
        this.invoiceDate = invoiceDate;
        this.invoiceForMonth = invoiceForMonth;
    }

    @Override
    public String toString() {
        StringBuilder headerString = new StringBuilder();
        headerString.append(String.format("%s %n %s %n %n",
            this.businessName,
            this.businessAddress.toString()
        ));
        headerString.append(String.format("Invoice for: %n %s %n %s %n %s %n%n",
                this.client.getName(),
                this.client.getAddress().toString(),
                this.client.getContact().toString()
        ));
        return headerString.toString();
    }
}
