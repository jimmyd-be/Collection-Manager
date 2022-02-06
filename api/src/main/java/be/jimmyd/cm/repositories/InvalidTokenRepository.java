package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {

    @Query("FROM InvalidToken WHERE invalidFrom < :date")
    List<InvalidToken> findOldTokens(LocalDateTime date);
}
