package fr.eseo.equipe2.pglback.dao;

import fr.eseo.equipe2.pglback.model.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStopDao extends JpaRepository<BusStop, String> {
}
