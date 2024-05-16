package bg.tu.varna.events.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "bg.tu.varna.events")
@EntityScan(basePackages = "bg.tu.varna.events.persistence.entities")
@EnableJpaRepositories(basePackages = "bg.tu.varna.events.persistence.repositories")
public class EventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsApplication.class, args);
	}

}
