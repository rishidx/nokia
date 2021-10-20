package in.rajarshi.eventsgenerate.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePunchData {

	private String employeeName;

	private String employeeDept;

	private Integer employeeId;

	private Date punchTime;
}
