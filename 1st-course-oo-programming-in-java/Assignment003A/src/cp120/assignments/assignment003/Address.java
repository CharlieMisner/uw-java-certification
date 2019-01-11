package cp120.assignments.assignment003;

public class Address {

    String street;
    String city;
    String county;
    String state;
    String zip;

    public Address(String str, String city, String cty, String sta, String z){
        this.street = str;
        this.city = city;
        this.county = cty;
        this.state = sta;
        this.zip = z;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }
}
