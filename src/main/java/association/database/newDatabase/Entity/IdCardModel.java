package association.database.newDatabase.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.PastOrPresent;

@Entity
public class IdCardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @PastOrPresent
    private Date issuedDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserModel user;

    public IdCardModel(){}

    public IdCardModel(Date d){
        this.issuedDate = d;
    }

    public void setIssuedDate(java.util.Date currentDate){
        this.issuedDate = currentDate;
    }

    public Date getIssuedDate(){
        return issuedDate;
    }

    // passing object vs variable directly
    public void setUser(UserModel user) {

        //parameters:- User user and set this.user = user
        this.user = user;
    }

    public UserModel getUser() {
        // return type:- user and return value is user;
        return user;
    }
}