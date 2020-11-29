package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.UserCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionUserRepository extends JpaRepository<UserCollection, Long> {

    @Query("SELECT u FROM UserCollection u JOIN FETCH u.user JOIN FETCH u.roleId WHERE u.collectionId.id = :id")
    List<UserCollection> getByCollectionId(@Param("id") long collectionId);

    @Query("SELECT u FROM UserCollection u WHERE u.user.id = :userId AND u.collectionId.id = :collectionId")
    List<UserCollection>  getByCollectionAndUser(@Param("collectionId")long collectionId, @Param("userId")long userId);
}
