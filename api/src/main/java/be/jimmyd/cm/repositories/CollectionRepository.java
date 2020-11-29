package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query("SELECT c FROM Collection c JOIN FETCH c.fields f JOIN FETCH c.type t JOIN c.userCollections u WHERE u.user.id = :userId and f.active = true")
    List<Collection> getByUser(@Param("userId") long userId);
}
