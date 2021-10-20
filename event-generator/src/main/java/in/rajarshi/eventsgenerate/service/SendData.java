package in.rajarshi.eventsgenerate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.rajarshi.eventsgenerate.dto.EmployeePunchData;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SendData {

	@Value("${eventgen.kafka.topic}")
	String topic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private ObjectMapper mapper = new ObjectMapper();

	public void send(EmployeePunchData message) throws JsonProcessingException {
		log.debug("Message: " + message + " : sent to topic: " + topic);
		kafkaTemplate.send(topic, mapper.writeValueAsString(message));
	}
}
