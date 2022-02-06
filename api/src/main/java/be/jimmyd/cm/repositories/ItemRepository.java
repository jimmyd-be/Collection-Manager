package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i JOIN FETCH i.itemdata d JOIN i.collections c WHERE c.id = :id ORDER BY i.name")
    List<Item> getByCollectionId(@Param("id") long collectionId, Pageable page);

    @Query("SELECT i FROM Item i WHERE i.collections IS EMPTY ")
    List<Item> findItemsWithoutCollection();

    @Modifying
    @Query(value = "DELETE FROM cm_collectionitem WHERE collectionId = :collectionId AND itemId = :itemId", nativeQuery = true)
    void deleteItemFromCollection(@Param("itemId") long itemId, @Param("collectionId") long collectionId);

    @Query("SELECT i FROM Item i JOIN FETCH i.itemdata d JOIN i.collections c WHERE c.id = :id AND upper(i.name) LIKE %:queryName% ORDER BY i.name")
    List<Item> getByCollectionIdAndQuery(@Param("id") long collectionId, @Param("queryName") String query, Pageable page);

    @Query("SELECT DISTINCT i FROM Item i JOIN FETCH i.itemdata d JOIN i.collections c JOIN c.userCollections uc " +
            "WHERE uc.user.mail = :userMail AND (LOWER(d.fieldValue) LIKE  %:term% OR LOWER(i.name) LIKE %:term%)")
    List<Item> findBySearch(@Param("term") String searchTerm, @Param("userMail") String mail);
}
