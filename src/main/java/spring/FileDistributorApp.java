package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
		"spring.cdrfiles",
		"spring.rakscode",
		"spring.repository",
		"spring.service"
})

@SpringBootApplication
public class FileDistributorApp {

	public static void main(String[] args) {
		SpringApplication.run(FileDistributorApp.class, args);
	}
}
