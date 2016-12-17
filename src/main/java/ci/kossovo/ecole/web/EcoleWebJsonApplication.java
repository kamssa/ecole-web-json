package ci.kossovo.ecole.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import ci.kossovo.ecole.DomainAndPersistence;

@SpringBootApplication
@Import({DomainAndPersistence.class})
public class EcoleWebJsonApplication {

	private static final Logger log = LoggerFactory.getLogger(EcoleWebJsonApplication.class);
 
	public static void main(String[] args) {
		SpringApplication.run(EcoleWebJsonApplication.class, args);

	}

	

	}