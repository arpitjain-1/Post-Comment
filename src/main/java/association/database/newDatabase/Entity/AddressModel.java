package association.database.newDatabase.Entity;

import org.hibernate.annotations.ManyToAny;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class AddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    private int street;
    private String city;
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    public AddressModel(){}
    AddressModel(int street, String city, String country){
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
