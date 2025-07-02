package association.database.newDatabase.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import association.database.newDatabase.Entity.UserModel;

@Repository
public interface UserRepository extends JpaRepository <UserModel, Integer>{

    association.database.newDatabase.Entity.UserModel save(association.database.newDatabase.Entity.UserModel user);

    boolean existsByEmail(String email);

    // Creating JPA
    @Query("Select u from UserModel u")
    public List<UserModel> getAllUser();

    //Jpa with the Qery Perameter
    @Query("Select u from UserModel u where u.id =:id")
    Optional<UserModel> getUserByID(@Param(value = "id") int id);

    Optional<UserModel> findTopByOrderByIdDesc();    
}
