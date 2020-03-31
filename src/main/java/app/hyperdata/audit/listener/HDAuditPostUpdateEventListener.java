package app.hyperdata.audit.listener;

import app.hyperdata.audit.config.HDAudit;
import app.hyperdata.audit.db.model.AuditAction;
import app.hyperdata.audit.service.AuditService;
import app.hyperdata.audit.util.AuditUtil;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPostUpdateEventListenerImpl;
import org.hibernate.event.spi.PostUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HDAuditPostUpdateEventListener extends EnversPostUpdateEventListenerImpl {

    @Autowired
    private AuditService auditService;

    public HDAuditPostUpdateEventListener(EnversService enversService) {
        super(enversService);
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        try {
            HDAudit audited = event.getEntity().getClass().getAnnotation(HDAudit.class);
            if (audited != null) {
                for (int index = 0; index < event.getPersister().getPropertyNames().length; index++) {
                    String propertyName = event.getPersister().getPropertyNames()[index];
                    if (AuditUtil.isPropertyAudited(event.getEntity(), propertyName)) {
                        boolean propertyChanged = false;
                        if (event.getOldState()[index] instanceof String
                                || event.getOldState()[index] instanceof Date
                                || event.getOldState()[index] instanceof Timestamp) {
                            if (!event.getOldState()[index].equals(event.getState()[index])) {
                                propertyChanged = true;
                            }
                        } else if (event.getOldState()[index] != event.getState()[index]) {
                            propertyChanged = true;
                        }
                        if (propertyChanged) {
                            auditService.auditAction(
                                    AuditAction.UPDATE,
                                    AuditUtil.getEntityNameWithDbTableName(event.getEntity()),
                                    (Long)event.getId(),
                                    AuditUtil.getPropertyNameWithDbColumnName(event.getEntity(), propertyName),
                                    String.valueOf(event.getOldState()[index]),
                                    String.valueOf(event.getState()[index]));
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.WARNING, e.getMessage(), e);
        }
    }
}
