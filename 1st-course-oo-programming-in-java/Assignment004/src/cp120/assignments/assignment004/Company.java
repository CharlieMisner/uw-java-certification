package cp120.assignments.assignment004;

public class Company {

    String companyName;
    String companyWebsite;
    String companyPhoneNumber;
    Address companyAddress;

    public Company(String name, String webSite, String phone, Address address){
        this.companyName = name;
        this.companyWebsite = webSite;
        this.companyPhoneNumber = phone;
        this.companyAddress = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public Address getCompanyAddress() {
        return companyAddress;
    }
}
