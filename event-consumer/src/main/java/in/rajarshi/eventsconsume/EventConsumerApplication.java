package in.rajarshi.eventsconsume;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import in.rajarshi.eventsconsume.dto.EmployeePunchData;
import in.rajarshi.eventsconsume.repository.EmployeePunchDataRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Log4j2
@Configuration
@EnableSwagger2
@RestController
@EnableDiscoveryClient
@RibbonClients
@SpringBootApplication
@EntityScan(basePackages = "in.rajarshi.eventsconsume.dto")
@EnableJpaRepositories(basePackages = "in.rajarshi.eventsconsume.repository")
public class EventConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventConsumerApplication.class, args);
	}

	@Bean
	public Docket eventGenApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("in.rajarshi.eventsconsume")).paths(PathSelectors.any())
				.build();
	}

	@Autowired
	private EmployeePunchDataRepository employeePunchDataRepository;

	@ApiOperation(value = "View all swipe events", response = EmployeePunchData.class)
	@ApiResponse(code = 200, message = "Successfully return all swipe events")
	@GetMapping("/view-all")
	public ResponseEntity<List<EmployeePunchData>> viewAllEvents() {
		log.trace("viewAllEvents");
		return ResponseEntity.ok(employeePunchDataRepository.findAll());
	}

	@ApiOperation(value = "view a swipe event", response = EmployeePunchData.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully return swipe event"),
			@ApiResponse(code = 204, message = "No content") })
	@GetMapping("/view")
	public ResponseEntity<EmployeePunchData> viewEvent(@PathVariable Integer empId) {
		log.trace("viewEvent");
		Optional<EmployeePunchData> data = employeePunchDataRepository.findById(empId);
		if (data.isPresent())
			return ResponseEntity.ok(data.get());
		else
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
