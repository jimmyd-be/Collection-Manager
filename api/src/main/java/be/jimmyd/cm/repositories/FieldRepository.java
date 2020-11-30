package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {


    @Query("SELECT f FROM Field f WHERE f.collectiontype = (SELECT t FROM CollectionType t JOIN t.collections c WHERE c.id = :collectionId)")
    List<Field> findBasicFieldByCollectionId(@Param("collectionId") long collectionId);

    @Query("SELECT f FROM Field f JOIN f.collections c WHERE c.id = :collectionId")
    List<Field> findCustomFieldByCollectionId(@Param("collectionId") long collectionId);
}
