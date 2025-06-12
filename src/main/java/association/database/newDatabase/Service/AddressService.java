package association.database.newDatabase.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import association.database.newDatabase.Entity.AddressModel;
import association.database.newDatabase.Entity.UserModel;
import association.database.newDatabase.Repository.AddressRepository;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
}
