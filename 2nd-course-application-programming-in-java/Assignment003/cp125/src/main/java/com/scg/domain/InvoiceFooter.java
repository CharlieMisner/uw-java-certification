package com.scg.domain;

/**
 * @author CharlieM
 */
public class InvoiceFooter {

    private String businessName;
    private int pageNumber = 1;

    public InvoiceFooter(String businessName) {
        this.businessName = businessName;
    }

    /**
     * Override of toString for invoice footer.
     * @return
     */
    @Override
    public String toString() {
        StringBuilder footerString = new StringBuilder();
        footerString.append(String.format(
                "%n%s %nPage:  %d%n",
                this.businessName,
                this.pageNumber
        ));

        footerString.append(String.format(
                "=====================================================================%n%n"
        ));

        return footerString.toString();
    }

    /**
     * Increases the page number
     */
    public void incrementPageNumber(){
        this.pageNumber++;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
