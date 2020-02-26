package lin.louis.heart.rate.calculator.heartbeat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeatConverter;


@Configuration
public class HeartBeatConfig {

	@Bean
	@ConfigurationProperties("heart-beat")
	HeartBeatProperties heartBeatProperties() {
		return new HeartBeatProperties();
	}

	@Bean
	HeartBeatConverter heartBeatConverter(HeartBeatProperties heartBeatProperties) {
		return new HeartBeatConverter(heartBeatProperties.getSeparator());
	}
}
