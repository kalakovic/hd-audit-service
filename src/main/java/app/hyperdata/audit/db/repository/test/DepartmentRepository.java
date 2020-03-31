package app.hyperdata.audit.db.repository.test;

import app.hyperdata.audit.db.model.test.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
