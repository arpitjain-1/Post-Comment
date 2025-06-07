package association.database.newDatabase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import association.database.newDatabase.Entity.UserModel;

@Repository
public interface UserRepository extends JpaRepository <UserModel, Integer>{

    association.database.newDatabase.Entity.UserModel save(association.database.newDatabase.Entity.UserModel user);
    
}
