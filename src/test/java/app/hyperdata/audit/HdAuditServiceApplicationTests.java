package app.hyperdata.audit;

import app.hyperdata.audit.db.model.AuditAction;
import app.hyperdata.audit.db.model.test.Department;
import app.hyperdata.audit.db.model.test.Employee;
import app.hyperdata.audit.db.repository.AuditActionRepository;
import app.hyperdata.audit.db.repository.test.DepartmentRepository;
import app.hyperdata.audit.db.repository.test.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class HdAuditServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private AuditActionRepository auditActionRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private DepartmentRepository departmentRepository;

	@Test
	public void testAuditing() {
		//create department and save it
		Department department = new Department(null, "Department 1", "Responsibility 1");
		department = departmentRepository.saveAndFlush(department);
		Assert.assertTrue(departmentRepository.findById(department.getId()).isPresent());

		//test that audit exists for insert
		Assert.assertNotNull(auditActionRepository.findFirstByActionAndItemId(AuditAction.INSERT, department.getId()));


		//create employee and save it
		Employee employee = new Employee(null, "John Smith", "Address 1", "js@mail.com", department);
		employee = employeeRepository.saveAndFlush(employee);
		Assert.assertTrue(departmentRepository.findById(employee.getId()).isPresent());

		//test that audit exists for insert with newValue for audited property
		Assert.assertNotNull(auditActionRepository.findFirstByActionAndItemIdAndNewValue(AuditAction.INSERT, employee.getId(), "js@mail.com"));

		//test that audit does not exist for insert with newValue for not audited property
		Assert.assertNull(auditActionRepository.findFirstByActionAndItemIdAndNewValue(AuditAction.INSERT, employee.getId(), "Address 1"));

		employee.setEmail("john.smith@mail.com");
		employee.setAddress("Address 2");
		employee = employeeRepository.saveAndFlush(employee);

		//test that audit exists for update
		Assert.assertNotNull(auditActionRepository.findFirstByActionAndItemId(AuditAction.UPDATE, employee.getId()));

		//test that audit exists for insert with newValue for audited property
		Assert.assertNotNull(auditActionRepository.findFirstByActionAndItemIdAndNewValue(AuditAction.UPDATE, employee.getId(), "john.smith@mail.com"));

		//test that audit does not exist for insert with newValue for not audited property
		Assert.assertNull(auditActionRepository.findFirstByActionAndItemIdAndNewValue(AuditAction.UPDATE, employee.getId(), "Address 2"));

		employeeRepository.delete(employee);
		Assert.assertFalse(employeeRepository.findById(employee.getId()).isPresent());

		//test that audit exists for delete
		Assert.assertNotNull(auditActionRepository.findFirstByActionAndItemId(AuditAction.DELETE, employee.getId()));
	}
}
