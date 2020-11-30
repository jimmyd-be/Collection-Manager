package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.Item;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i JOIN FETCH i.itemdata d JOIN i.collections c WHERE c.id = :id ORDER BY i.name")
    List<Item> getByCollectionId(@Param("id") long collectionId, Pageable page);
}
