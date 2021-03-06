package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.Itemdata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ItemDataRepository extends JpaRepository<Itemdata, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cm_itemdata WHERE itemId = :id", nativeQuery = true)
    void deleteByItemId(@Param("id") long id);

}
