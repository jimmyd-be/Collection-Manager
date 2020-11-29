package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.CollectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionTypeRepository extends JpaRepository<CollectionType, Long> {

    @Query("SELECT t FROM CollectionType t WHERE t.active = true")
    List<CollectionType> getAllTypes();
}
