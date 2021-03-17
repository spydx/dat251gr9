package no.hvl.dat251.gr9.lopbackend;

import no.hvl.dat251.gr9.lopbackend.services.SetupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class LopBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LopBackendApplication.class, args);
	}
		@Component
		public class ApplicationStartupRunner implements CommandLineRunner {
			protected final Logger logger = LoggerFactory.getLogger(getClass());

			@Autowired
			private SetupService setupService;

			@Override
			public void run(String... args) throws Exception {
				setupService.init();
				logger.info("ApplicationStartupRunner run method Started !!");
			}
		}
	}


