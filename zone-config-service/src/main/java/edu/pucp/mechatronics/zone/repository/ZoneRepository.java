package edu.pucp.mechatronics.zone.repository;

import edu.pucp.mechatronics.zone.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
}
