package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {


    @Query("FROM Field WHERE collections IN (SELECT c FROM Collection c WHERE c.id = :collectionId) AND type IS NOT NULL")
    List<Field> findBasicFieldByCollectionId(@Param("collectionId") long collectionId);

    @Query("FROM Field WHERE collections IN (SELECT c FROM Collection c WHERE c.id = :collectionId) AND type IS NULL")
    List<Field> findCustomFieldByCollectionId(@Param("collectionId") long collectionId);
}
