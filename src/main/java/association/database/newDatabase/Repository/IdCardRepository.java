package association.database.newDatabase.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import association.database.newDatabase.Entity.IdCardModel;

public interface IdCardRepository extends JpaRepository<IdCardModel, Integer>{

}
