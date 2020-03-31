package app.hyperdata.audit.db.model.test;

import app.hyperdata.audit.config.HDAudit;
import app.hyperdata.audit.config.HDNotAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EMPLOYEE")
@HDAudit
public class Employee {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "NAME")
    String name;

    @Column(name = "ADDRESS")
    @HDNotAudit
    String address;

    @Column(name = "EMAIL")
    String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName="ID")
    private Department department;
}
