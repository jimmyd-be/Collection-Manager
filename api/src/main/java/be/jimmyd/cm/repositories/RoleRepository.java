package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("FROM Role WHERE active = true")
    List<Role> getActiveRoles();
}
