package fr.eseo.equipe2.pglback.dao;

import fr.eseo.equipe2.pglback.model.BusLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusLineDao extends JpaRepository<BusLine, String> {
}
