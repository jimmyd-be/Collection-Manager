package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query("SELECT c FROM Collection c JOIN c.userCollections u WHERE u.user.id = :userId")
    List<Collection> getByUser(@Param("userId") long userId);

    @Modifying
    @Query(value = "DELETE FROM cm_collection WHERE id=:id", nativeQuery = true)
    void deleteNative(@Param("id") long id);

    @Query("SELECT c FROM Collection c WHERE c.userCollections IS EMPTY ")
    List<Collection> getWithoutLink();

    @Query("SELECT c FROM Collection c JOIN c.userCollections u WHERE u.user.mail = :user")
    List<Collection> getByUser(@Param("user") String userMail);
}
