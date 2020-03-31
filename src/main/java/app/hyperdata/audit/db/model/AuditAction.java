package app.hyperdata.audit.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AUDIT_ACTION")
public class AuditAction {

    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ENTITY_NAME")
    private String entityName;

    @Column(name = "PROPERTY_NAME")
    private String propertyName;

    @Column(name = "ITEM_ID")
    private Long itemId;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "ACTION_DATE")
    private Timestamp actionDate;

    @Column(name = "ACTION_BY")
    private String actionBy;

    @Column(name = "OLD_VALUE")
    private String oldValue;

    @Column(name = "NEW_VALUE")
    private String newValue;
}
