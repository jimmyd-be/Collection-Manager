package be.jimmyd.cm.repositories;

import be.jimmyd.cm.entities.Itemdata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDataRepository extends JpaRepository<Itemdata, Long> {
}
