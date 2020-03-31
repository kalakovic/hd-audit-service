package app.hyperdata.audit.db.repository.test;

import app.hyperdata.audit.db.model.test.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
