package app.hyperdata.audit.config;

import app.hyperdata.audit.listener.HDAuditPostDeleteEventListener;
import app.hyperdata.audit.listener.HDAuditPostInsertEventListener;
import app.hyperdata.audit.listener.HDAuditPostUpdateEventListener;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

@Component
public class ListenerConfig {

    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Autowired
    HDAuditPostInsertEventListener postInsertEventListener;
    @Autowired
    HDAuditPostUpdateEventListener postUpdateEventListener;
    @Autowired
    HDAuditPostDeleteEventListener postDeleteEventListener;

    @PostConstruct
    protected void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);

        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(postInsertEventListener);
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(postUpdateEventListener);
        registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(postDeleteEventListener);
    }
}
