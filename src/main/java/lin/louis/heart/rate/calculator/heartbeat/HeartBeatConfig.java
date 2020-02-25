package lin.louis.heart.rate.calculator.heartbeat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HeartBeatConfig {
	private static final String SEPARATOR = " ";

	@Bean
	HeartBeatConverter heartBeatConverter() {
		return new HeartBeatConverter(SEPARATOR);
	}
}
