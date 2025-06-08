package association.database.newDatabase;

import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import association.database.newDatabase.Entity.AddressModel;
import association.database.newDatabase.Entity.IdCardModel;
import association.database.newDatabase.Entity.RoleModel;
import association.database.newDatabase.Entity.UserModel;
import association.database.newDatabase.Repository.UserRepository;

@SpringBootApplication
public class newDatabaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(newDatabaseApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository) {
        return args -> {
            UserModel user = new UserModel("arpit@email.com", "Arpit", "123456");
            user.setEmail("arpit@a.com");

            IdCardModel card = new IdCardModel(new Date()); 

            AddressModel address = new AddressModel();
            address.setCity("Delhi");
            address.setCountry("Ind");
            address.setStreet(100);

            AddressModel address2 = new AddressModel();
            address2.setCity("Noida");
            address2.setCountry("Ind");
            address2.setStreet(101);

            RoleModel admin = new RoleModel("ADMIN", 1);
            RoleModel userRole = new RoleModel("USER", 2);
            
            // Setting bidirectional relationship
            user.setICardModel(card);
            card.setUser(user);
            address.setUser(user);
            address2.setUser(user);
            user.setAddressModel(List.of(address, address2));
            user.setRoleModel(admin, userRole);

            userRepository.save(user); 
            
            System.out.println("User saved successfully.");
            System.out.println("Address created");
            System.out.println("Id Card generated");

            address.getUser();
            user.getAddressModel();
            user.getiCardModel();
            user.getRoleModel();
        };
    }
}
