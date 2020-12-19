package com.github.ossvj.sf.portal.health;

import lombok.extern.slf4j.Slf4j;
import org.apache.isis.applib.services.health.Health;
import org.apache.isis.applib.services.health.HealthCheckService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HealthCheckServiceImpl implements HealthCheckService {
    @Override
    public Health check() {
        log.info("PORTAL HEALTH CHECK INVOKED...");
        return Health.ok();
    }
}
