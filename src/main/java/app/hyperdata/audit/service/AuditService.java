package app.hyperdata.audit.service;

import app.hyperdata.audit.db.model.AuditAction;
import app.hyperdata.audit.db.repository.AuditActionRepository;
import app.hyperdata.audit.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

@Service
public class AuditService {

    @Value("${hyperdata.audit.enabled:true}")
    private boolean auditEnabled;

    @Autowired
    private AuditActionRepository auditActionRepository;

    public void auditAction(String action, String entityName, Long itemId, String propertyName, String oldValue, String newValue) {
        if (auditEnabled && !StringUtils.isEmpty(action) && !StringUtils.isEmpty(entityName)) {
            saveAuditAction(action, entityName, itemId, propertyName, oldValue, newValue);
        }
    }

    private AuditAction saveAuditAction(String action, String entityName, Long itemId, String propertyName, String oldValue, String newValue) {
        AuditAction audit = new AuditAction();
        audit.setAction(action);
        audit.setActionDate(new Timestamp(System.currentTimeMillis()));
        audit.setEntityName(entityName);
        audit.setItemId(itemId);
        audit.setPropertyName(propertyName);
        audit.setOldValue(oldValue);
        audit.setNewValue(newValue);
        if (SecurityUtil.getCurrentUserLogin().isPresent()) {
            audit.setActionBy(SecurityUtil.getCurrentUserLogin().get());
        }
        return auditActionRepository.save(audit);
    }
}
