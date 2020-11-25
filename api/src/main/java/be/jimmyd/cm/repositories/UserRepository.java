package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User WHERE mail=:mail")
    User findByMail(@Param("mail") String mail);
}
