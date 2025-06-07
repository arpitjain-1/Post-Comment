package association.database.newDatabase.Entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class UserModel {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    
    private String Name;
    private String Email;

    private String Password;

    // When working with the object
    // @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // private IdCardModel iCardModel;

    // When working with a variable
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_card_id")
    private IdCardModel iCardModel;

    UserModel(){}

    UserModel(String Email, String Name, String Password){
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
}
