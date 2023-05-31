package com.nwt.nwt_projekat_user.repository.system_events_log;

import com.nwt.nwt_projekat_user.models.Cart;
import com.nwt.nwt_projekat_user.models.SystemEventsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemEventsLogRepository extends JpaRepository<SystemEventsLog, Long> {

    @Override
    Optional<SystemEventsLog> findById(Long id);
}
