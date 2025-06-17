package association.database.newDatabase.DTO.Response;

import association.database.newDatabase.Entity.UserModel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressResponseDTO {

    private String city;
    private String country;

    public AddressResponseDTO(){}
    public AddressResponseDTO(int street, String city, String country){
        this.city = city;
        this.country = country;
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
}
