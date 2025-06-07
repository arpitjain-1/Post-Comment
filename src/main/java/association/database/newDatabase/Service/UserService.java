package association.database.newDatabase.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import association.database.newDatabase.Entity.IdCardModel;
import association.database.newDatabase.Entity.UserModel;
import association.database.newDatabase.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private IdCardService idCardService;

    public List<UserModel> getAllUser(){
        return repo.findAll();
    }

    public IdCardModel createUser(UserModel user) {
        return idCardService.createId(repo.save(user).getId());
    }

    public Optional<UserModel> currentUser(int id) {
        return Optional.empty();
    }

    public Optional<UserModel> updateUser(int id, UserModel user) {
        if (repo.existsById(id)) {
            user.setId(id); 
            return Optional.of(repo.save(user));
        } else {
            return Optional.empty(); 
        }
    }

    public boolean deleteUser(int id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
