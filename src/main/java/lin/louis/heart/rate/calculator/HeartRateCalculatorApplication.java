package lin.louis.heart.rate.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


@SpringBootApplication
public class HeartRateCalculatorApplication {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(HeartRateCalculatorApplication.class, args);
	}

	@Component
	public class MainApp implements CommandLineRunner {

		@Override
		public void run(String... args) {
			logger.info("Running heart-rate-calculator");
		}
	}
}
