package lin.louis.heart.rate.calculator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import lin.louis.heart.rate.calculator.heartrate.HeartRateGenerator;


@SpringBootApplication
public class HeartRateCalculatorApplication {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(HeartRateCalculatorApplication.class, args);
	}

	@Component
	public class MainApp implements CommandLineRunner {

		@Autowired
		private HeartRateGenerator heartRateGenerator;

		@Override
		public void run(String... args) {
			logger.info("Running heart-rate-calculator");
			if (args == null || args.length < 2) {
				logger.error("Missing arguments");
				printHelp();
				System.exit(1);
			}
			var start = LocalDateTime.now();
			var inputFileName = args[0];
			var outputFileName = args[1];
			try (
					InputStream in = new FileInputStream(inputFileName);
					OutputStream out = new FileOutputStream(outputFileName)
			) {
				heartRateGenerator.generate(in, out);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			var timeExecuted = Duration.between(start, LocalDateTime.now());
			logger.info("Finished writing heart rates from '{}' to '{}' in {}ms",
					inputFileName,
					outputFileName,
					timeExecuted.toMillis());
		}

		private void printHelp() {
			logger.info("\n\nComputing heart rates from heart beats.\n\n"
					+ "Usage:\n"
					+ "\theart-rate-calculator-spring-boot.jar <input_file> <output_file>\n\n"
					+ "\t\tor\n"
					+ "\tjava -jar heart-rate-calculator.jar <input_file> <output_file>\n"
					+ "Examples:\n"
					+ "\theart-rate-calculator-spring-boot.jar /path/to/heartbeat.in /path/to/heartrate.out\n"
					+ "\t\tor\n"
					+ "\tjava -jar heart-rate-calculator.jar /path/to/heartbeat.in /path/to/heartrate.out\n"
			);
		}
	}
}
