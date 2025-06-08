package association.database.newDatabase.Entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class UserModel {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    
    private String Name;
    private String Email;

    private String Password;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_card_id")
    private IdCardModel iCardModel;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressModel> addressModel;

    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleModel> roles = new ArrayList<>();

    UserModel(){}

    public UserModel(String Email, String Name, String Password){
        this.Email = Email;
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
        this.Email = email;
    }

    public String getEmail() {
        return Email;
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

    public void addRole(RoleModel role) {
        this.roles.add(role);
        role.getUser().add(this);
    }

    public void removeRole(RoleModel role) {
        this.roles.remove(role);
        role.getUser().remove(this);
    }

    public void setRoleModel(List<RoleModel> roleModel) {
        this.roles = roleModel;
    }

    public List<RoleModel> getRoleModel() {
        return roles;
    }
}
