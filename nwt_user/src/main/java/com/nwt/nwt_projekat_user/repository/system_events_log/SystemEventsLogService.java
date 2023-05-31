package com.nwt.nwt_projekat_user.repository.system_events_log;

import com.nwt.nwt_projekat_user.models.SystemEventsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemEventsLogService {

    @Autowired
    SystemEventsLogRepository systemEventsLogRepository;

    public SystemEventsLog getSystemEventsLogById(Long id){
        return systemEventsLogRepository.findById(id).orElse(null);
    }

    public void createOrUpdate(SystemEventsLog log){
        systemEventsLogRepository.save(log);
    }

}

