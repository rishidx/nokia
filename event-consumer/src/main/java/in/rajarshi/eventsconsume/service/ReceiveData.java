package in.rajarshi.eventsconsume.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.rajarshi.eventsconsume.dto.EmployeePunchData;
import in.rajarshi.eventsconsume.repository.EmployeePunchDataRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReceiveData {

	@Autowired
	private EmployeePunchDataRepository employeePunchDataRepository;

	private ObjectMapper mapper = new ObjectMapper();

	@KafkaListener(topics = "${eventcon.kafka.topic}", groupId = "emp-punch")
	public void receive(String message) throws JsonMappingException, JsonProcessingException {
		log.debug("Message: " + message + " : received at " + new Date());

		EmployeePunchData punchData = mapper.readValue(message, EmployeePunchData.class);
		log.debug(punchData);
		// Store value to DB
		employeePunchDataRepository.save(punchData);
	}
}
