package association.database.newDatabase.Entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.*;

@Entity
public class UserModel {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    
    @NotEmpty
    private String Name;

    @Email
    @Pattern(regexp="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email isn't formated properly")
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
        message = "Must have 1 digit, 1 lowercase, 1 uppercase, 1 special character, and be at least 8 characters"
    )
    @Column(name = "password", nullable = false)
    private String Password;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private IdCardModel iCardModel;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AddressModel> addressModel;

    public UserModel(){}

    public UserModel(String Email, String Name, String Password){
        this.email = Email;
        this.Name = Name;
        this.Password = Password;
    }

    public void setId(int id){
        this.Id = id;
    }

    public int getId(){
        return Id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPassword() {
        return Password;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setICardModel(IdCardModel card) {
        this.iCardModel = card;
    }

    public void setAddressModel(List<AddressModel> addressModel) {
        this.addressModel = addressModel;
    }

    public List<AddressModel> getAddressModel() {
        return addressModel;
    }

    public IdCardModel getiCardModel() {
        return iCardModel;
    }
}
