package in.rajarshi.eventsconsume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.rajarshi.eventsconsume.dto.EmployeePunchData;

@Repository
public interface EmployeePunchDataRepository extends JpaRepository<EmployeePunchData, Integer> {

}
