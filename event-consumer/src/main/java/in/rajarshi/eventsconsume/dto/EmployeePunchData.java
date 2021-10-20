package in.rajarshi.eventsconsume.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmployeePunchData {

	private String employeeName;
	
	private String employeeDept;
	
	@Id
	private Integer employeeId;
	
	private Date punchTime;
}
