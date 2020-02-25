package lin.louis.heart.rate.calculator.heartrate;

import java.util.Arrays;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lin.louis.heart.rate.calculator.heartrate.reset.ResetCheckerFacade;
import lin.louis.heart.rate.calculator.heartrate.reset.GapResetChecker;
import lin.louis.heart.rate.calculator.heartrate.reset.HriResetChecker;
import lin.louis.heart.rate.calculator.heartrate.reset.QRSResetChecker;
import lin.louis.heart.rate.calculator.heartrate.reset.TimestampResetChecker;


@Configuration
public class HeartRateConfig {
	@Bean
	@ConfigurationProperties("heart-rate")
	HeartRateProperties heartRateProperties() {
		return new HeartRateProperties();
	}

	@Bean
	ResetCheckerFacade checkerFacade(HeartRateProperties heartRateProperties) {
		return new ResetCheckerFacade(Arrays.asList(
				new GapResetChecker(heartRateProperties.getGapDuration()),
				new HriResetChecker(heartRateProperties.getHri().getMin(), heartRateProperties.getHri().getMax()),
				new QRSResetChecker(),
				new TimestampResetChecker()
		));
	}
}
