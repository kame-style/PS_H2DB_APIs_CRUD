package PS_project.API_dummy_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = "PS_project.API_dummy_data.model")
@EnableScheduling
public class ApiDummyDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDummyDataApplication.class, args);
		System.out.println("server Started");
	}

}
