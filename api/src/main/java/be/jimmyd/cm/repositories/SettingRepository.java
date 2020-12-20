package be.jimmyd.cm.repositories;


import be.jimmyd.cm.entities.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, String>  {

    @Query("FROM Setting WHERE key = :key")
    String getById(@Param("key") String apiKey);
}
