package app.hyperdata.audit.db.repository;

import app.hyperdata.audit.db.model.AuditAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditActionRepository extends JpaRepository<AuditAction, Long> {

    AuditAction findFirstByActionAndItemId(String action, Long itemId);

    AuditAction findFirstByActionAndItemIdAndNewValue(String action, Long itemId, String newValue);
}
