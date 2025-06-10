package association.database.newDatabase.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class RoleModel {
    @Id
    private int roleId;

    private String role;

    // @ManyToMany(mappedBy = "roles")
    // private List<UserModel> user = new ArrayList<>();

    public RoleModel(){};

    public RoleModel(String Role, int roleId){
        this.role = Role;
        this.roleId = roleId;
    }

    // public List<UserModel> getUser() {
    //     return user;
    // }
}
