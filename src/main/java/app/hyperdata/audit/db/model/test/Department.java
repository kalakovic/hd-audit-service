package app.hyperdata.audit.db.model.test;

import app.hyperdata.audit.config.HDAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DEPARTMENT")
@HDAudit
public class Department {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "NAME")
	String name;

	@Column(name = "RESPONSIBILITY")
	String responsibility;
}
