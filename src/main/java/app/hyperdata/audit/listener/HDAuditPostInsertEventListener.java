package app.hyperdata.audit.listener;

import app.hyperdata.audit.config.HDAudit;
import app.hyperdata.audit.db.model.AuditAction;
import app.hyperdata.audit.service.AuditService;
import app.hyperdata.audit.util.AuditUtil;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPostInsertEventListenerImpl;
import org.hibernate.event.spi.PostInsertEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HDAuditPostInsertEventListener extends EnversPostInsertEventListenerImpl {

    @Autowired
    private AuditService auditService;

    public HDAuditPostInsertEventListener(EnversService enversService) {
        super(enversService);
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {

        try {
            HDAudit audited = event.getEntity().getClass().getAnnotation(HDAudit.class);
            if (audited != null) {
                for (int index = 0; index < event.getPersister().getPropertyNames().length; index++) {
                    String propertyName = event.getPersister().getPropertyNames()[index];
                    if (AuditUtil.isPropertyAudited(event.getEntity(), propertyName)) {
                        auditService.auditAction(
                                AuditAction.INSERT,
                                AuditUtil.getEntityNameWithDbTableName(event.getEntity()),
                                (Long)event.getId(),
                                AuditUtil.getPropertyNameWithDbColumnName(event.getEntity(), propertyName),
                                null,
                                String.valueOf(event.getState()[index]));
                    }
                }
            }
        }
        catch (Exception e) {
            Logger.getLogger(HDAuditPostInsertEventListener.class.getSimpleName()).log(Level.WARNING, e.getMessage(), e);
        }
    }
}
