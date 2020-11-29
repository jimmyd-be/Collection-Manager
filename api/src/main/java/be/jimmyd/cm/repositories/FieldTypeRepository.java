package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.FieldType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldTypeRepository extends JpaRepository<FieldType, Long> {

    @Query("FROM FieldType WHERE type = :name")
    FieldType findByName(@Param("name")String name);
}
