package association.database.newDatabase.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class IdCardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date issuedDate;

    // @OneToOne
    // @JoinColumn(name = "user_id")
    // private UserModel user;

    @Column(name = "user_id")
    private int userId;

    public IdCardModel(){}

    IdCardModel(Date d){
        this.issuedDate = d;
    }

    public void setIssuedDate(java.util.Date currentDate){
        this.issuedDate = currentDate;
    }

    public Date getIssuedDate(){
        return issuedDate;
    }

    // passing object vs variable directly
    public void setUser(int userId) {

        //parameters:- User user and set this.user = user
        this.userId = userId;
    }

    public int getUser() {
        // return type:- user and return value is user;
        return userId;
    }
}