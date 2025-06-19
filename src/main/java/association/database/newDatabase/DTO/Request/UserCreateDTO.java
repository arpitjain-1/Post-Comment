package association.database.newDatabase.DTO.Request;

import java.util.List;


public class UserCreateDTO {

    private String Name;
    private String Email;
    private String Password;
    private List<AddressDTO> addressModel;

    public UserCreateDTO() {}

    public UserCreateDTO(String name, String email, String password) {
        this.Name = name;
        this.Email = email;
        this.Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public List<AddressDTO> getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(List<AddressDTO> addressModel) {
        this.addressModel = addressModel;
    }
}