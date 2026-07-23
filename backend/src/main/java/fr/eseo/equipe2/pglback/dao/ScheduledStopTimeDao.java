package fr.eseo.equipe2.pglback.dao;

import fr.eseo.equipe2.pglback.model.ScheduledStopTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ScheduledStopTimeDao extends JpaRepository<ScheduledStopTime, Long> {
    List<ScheduledStopTime> findByTripIdIn(Collection<String> tripIds);
}
