package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.mail=:mail")
    User findByMail(@Param("mail") String mail);

    @Query("SELECT u FROM User u WHERE u.mail=:userName OR u.username = :userName")
    User findByMailOrUserName(@Param("userName")String userName);
}
