package app.hyperdata.audit.listener;

import app.hyperdata.audit.config.HDAudit;
import app.hyperdata.audit.db.model.AuditAction;
import app.hyperdata.audit.service.AuditService;
import app.hyperdata.audit.util.AuditUtil;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPostDeleteEventListenerImpl;
import org.hibernate.event.spi.PostDeleteEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HDAuditPostDeleteEventListener extends EnversPostDeleteEventListenerImpl {

    @Autowired
    private AuditService auditService;

    public HDAuditPostDeleteEventListener(EnversService enversService) {
        super(enversService);
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {

        try {
            HDAudit audited = event.getEntity().getClass().getAnnotation(HDAudit.class);
            if (audited != null) {
                auditService.auditAction(
                        AuditAction.DELETE,
                        AuditUtil.getEntityNameWithDbTableName(event.getEntity()),
                        (Long)event.getId(),
                        null,
                        null,
                        null);
            }
        }
        catch (Exception e) {
            Logger.getLogger(HDAuditPostDeleteEventListener.class.getSimpleName()).log(Level.WARNING, e.getMessage(), e);
        }
    }
}
