package association.database.newDatabase;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;

import association.database.newDatabase.Entity.AddressModel;
import association.database.newDatabase.Entity.IdCardModel;
import association.database.newDatabase.Entity.RoleModel;
import association.database.newDatabase.Entity.UserModel;
import association.database.newDatabase.Exception.DataValidationException;
import association.database.newDatabase.Exception.UserAlreadyExistsException;
import association.database.newDatabase.Repository.UserRepository;
import association.database.newDatabase.Repository.AddressRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;


@SpringBootApplication
public class newDatabaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(newDatabaseApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository, AddressRepository addressRepository) {
        return args -> {
            try{
                UserModel user1 = createValidUser("arpit@gmail.com", "Arpit", "SecurePass123@", 1, "Delhi", "India", 2, "Noida", "India", 3, "Shivpuri", "India");
                if (userRepository.existsByEmail(user1.getEmail())) {
                    throw new UserAlreadyExistsException("User already exists with email: " + user1.getEmail());
                }
                userRepository.save(user1);
                System.out.println("User created");

                UserModel user2 = createValidUser("arpitjain@gmail.com", "Arpit", "SecurePass123@", 1, "Delhi", "India", 2, "Noida", "India", 3, "Shivpuri", "India");
                if (userRepository.existsByEmail(user2.getEmail())) {
                    throw new UserAlreadyExistsException("User already exists with email: " + user2.getEmail());
                }
                userRepository.save(user2);
                System.out.println("User2 created");

                printUser(addressRepository);

            }catch(UserAlreadyExistsException ex){
                System.out.println("Error Occured: "+ ex.getMessage());
            }catch(DataValidationException e){
                System.out.println("Error: "+ e.getMessage());
            }
        };
    }

    // JPA to apply join
    @Transactional
    public void printUser(AddressRepository addressRepository){
        List<AddressModel> allAddress =  addressRepository.findAll();
        Set<String> printedUser = new HashSet<>();
        for(AddressModel add : allAddress){
            String name = add.getUser().getName();
            String email = add.getUser().getEmail();
            String key = name+" - "+email;
            if(!printedUser.contains(key)){
                System.out.println("\n" + key);
                printedUser.add(key);
            }
            System.out.println("Current user's address is "+add.getStreet() + " " + add.getCity()  + " " + add.getCountry());            
        }
    }

    private UserModel createValidUser(String email, String name, String password, int street, String city, String country, int street2, String city2, String country2, int street3, String city3, String country3) {
        
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            throw new DataValidationException("Password Doesn't matched", null);
        }

        UserModel user = new UserModel(email, name, password);
        
        IdCardModel card = new IdCardModel(new Date());
        card.setUser(user);
        user.setICardModel(card);

        AddressModel address1 = new AddressModel(street, city, country);
        AddressModel address2 = new AddressModel(street2, city2, country2);
        AddressModel address3 = new AddressModel(street3, city3, country3);
        address1.setUser(user);
        address2.setUser(user);
        address3.setUser(user);
        user.setAddressModel(List.of(address1, address2, address3));

        return user;
    }
}
