package association.database.newDatabase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import association.database.newDatabase.Entity.AddressModel;

public interface AddressRepository extends JpaRepository<AddressModel, Integer>{
    
}
