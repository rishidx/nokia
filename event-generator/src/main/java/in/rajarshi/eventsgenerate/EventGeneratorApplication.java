package in.rajarshi.eventsgenerate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import in.rajarshi.eventsgenerate.dto.EmployeePunchData;
import in.rajarshi.eventsgenerate.service.SendData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.log4j.Log4j2;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Log4j2
@RestController
@EnableSwagger2
@EnableScheduling
@EnableDiscoveryClient
@RibbonClients
@Configuration
@SpringBootApplication
public class EventGeneratorApplication implements CommandLineRunner {

	private List<EmployeePunchData> punchDataList;

	public static void main(String[] args) {
		SpringApplication.run(EventGeneratorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		EmployeePunchData punchData1 = new EmployeePunchData("ABC1", "HR", 12345, null);
		EmployeePunchData punchData2 = new EmployeePunchData("ABC2", "HR", 23451, null);
		EmployeePunchData punchData3 = new EmployeePunchData("ABC3", "FINANCE", 34512, null);
		EmployeePunchData punchData4 = new EmployeePunchData("ABC4", "FINANCE", 45123, null);
		EmployeePunchData punchData5 = new EmployeePunchData("ABC5", "CONSULTANT", 54321, null);
		punchDataList = new ArrayList<>();
		punchDataList.add(punchData1);
		punchDataList.add(punchData2);
		punchDataList.add(punchData3);
		punchDataList.add(punchData4);
		punchDataList.add(punchData5);
	}

	@Bean
	public Docket eventGenApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("in.rajarshi.eventsgenerate")).paths(PathSelectors.any())
				.build();
	}

	/**
	 * Controller for Event generator
	 * 
	 */
	private EmployeePunchData employeePunchData;

	@Autowired
	private SendData sendData;

	@GetMapping("/")
	public String hello() {
		return "Hello World!";
	}

	@ApiOperation(value = "Swipe employee card", response = ResponseEntity.class)
	@ApiResponse(code = 200, message = "Successfully punched employee card")
	@PostMapping("/punch")
	public ResponseEntity<Void> punchCard(@RequestBody EmployeePunchData punchData) {
		log.info("punchCard :: start");
		employeePunchData = punchData;
		return ResponseEntity.ok().build();
	}

	@Scheduled(fixedRate = 30000, initialDelay = 5000)
	void sendPunchData() throws JsonProcessingException {
		log.trace("sendPunchData - start");
		if (employeePunchData == null) {
			employeePunchData = punchDataList.get(new Random().nextInt(punchDataList.size()));
			employeePunchData.setPunchTime(new Date());
		}
		sendData.send(employeePunchData);
		employeePunchData = null;
		log.trace("sendPunchData - end");
	}
}
