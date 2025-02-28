package PS_project.API_dummy_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "PS_project.API_dummy_data.model")
public class ApiDummyDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDummyDataApplication.class, args);
		System.out.println("server Started");
	}

}
