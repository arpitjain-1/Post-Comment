package association.database.newDatabase.DTO.Response;

import java.util.List;

public class UserResponseDTO {

    private String Name;
    private int Id;
    private String Email;
    private List<AddressResponseDTO> addressModel;

    public UserResponseDTO() {}

    public UserResponseDTO(int Id, String name, String email, String password) {
        this.Id = Id;
        this.Name = name;
        this.Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<AddressResponseDTO> getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(List<AddressResponseDTO> addresses) {
        this.addressModel = addresses;
    }
}
