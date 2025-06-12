package association.database.newDatabase.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import association.database.newDatabase.Entity.IdCardModel;
import association.database.newDatabase.Entity.UserModel;
import association.database.newDatabase.Repository.IdCardRepository;

@Service
public class IdCardService { 
    @Autowired
    IdCardRepository idCardRepository;

    public IdCardModel createIdCard(UserModel savedUser) {
        IdCardModel idCard = new IdCardModel();
        idCard.setUser(savedUser);
        idCard.setIssuedDate(new Date());

        return idCardRepository.save(idCard);
    }
}
