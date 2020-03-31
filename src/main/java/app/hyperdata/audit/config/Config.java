package app.hyperdata.audit.config;

import app.hyperdata.audit.listener.HDAuditPostDeleteEventListener;
import app.hyperdata.audit.listener.HDAuditPostInsertEventListener;
import app.hyperdata.audit.listener.HDAuditPostUpdateEventListener;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class Config {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Bean
    HDAuditPostInsertEventListener hdAuditPostInsertEventListener() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EnversService enversService = sessionFactory.getServiceRegistry().getService(EnversService.class);
        return new HDAuditPostInsertEventListener(enversService);
    }

    @Bean
    HDAuditPostUpdateEventListener hdAuditPreUpdateEventListener() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EnversService enversService = sessionFactory.getServiceRegistry().getService(EnversService.class);
        return new HDAuditPostUpdateEventListener(enversService);
    }

    @Bean
    HDAuditPostDeleteEventListener hdAuditPostDeleteEventListener() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EnversService enversService = sessionFactory.getServiceRegistry().getService(EnversService.class);
        return new HDAuditPostDeleteEventListener(enversService);
    }
}
