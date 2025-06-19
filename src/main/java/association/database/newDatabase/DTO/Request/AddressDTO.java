package association.database.newDatabase.DTO.Request;


import association.database.newDatabase.Entity.UserModel;


public class AddressDTO {
    private int street;
    private String city;
    private String country;

    
    private UserModel user;

    public AddressDTO(){}
    public AddressDTO(int street, String city, String country){
        this.street = street;
        this.city = city;
        this.country = country;
    }

    public int getStreet() {
        return street;
    }

    public void setStreet(int street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
