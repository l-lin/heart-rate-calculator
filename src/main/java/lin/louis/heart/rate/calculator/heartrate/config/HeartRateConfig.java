package lin.louis.heart.rate.calculator.heartrate.config;

import java.util.Arrays;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeatConverter;
import lin.louis.heart.rate.calculator.heartrate.HeartRateComputor;
import lin.louis.heart.rate.calculator.heartrate.HeartRateConverter;
import lin.louis.heart.rate.calculator.heartrate.HeartRateFactory;
import lin.louis.heart.rate.calculator.heartrate.HeartRateGenerator;
import lin.louis.heart.rate.calculator.heartrate.HeartRateValueConverter;
import lin.louis.heart.rate.calculator.heartrate.reset.GapResetChecker;
import lin.louis.heart.rate.calculator.heartrate.reset.HriResetChecker;
import lin.louis.heart.rate.calculator.heartrate.reset.QRSResetChecker;
import lin.louis.heart.rate.calculator.heartrate.reset.ResetCheckerFacade;
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

	@Bean
	HeartRateConverter heartRateConverter(HeartRateProperties heartRateProperties) {
		return new HeartRateConverter(heartRateProperties.getSeparator());
	}

	@Bean
	HeartRateComputor heartRateComputor() {
		return new HeartRateComputor();
	}

	@Bean
	HeartRateValueConverter heartRateValueConverter() {
		return new HeartRateValueConverter();
	}

	@Bean
	HeartRateFactory heartRateFactory(
			HeartRateProperties heartRateProperties,
			ResetCheckerFacade resetCheckerFacade,
			HeartRateComputor heartRateComputor,
			HeartRateValueConverter heartRateValueConverter
	) {
		return new HeartRateFactory(
				heartRateProperties.getNbHeartBeats(),
				resetCheckerFacade,
				heartRateComputor,
				heartRateValueConverter
		);
	}

	@Bean
	HeartRateGenerator heartRateGenerator(
			HeartRateProperties heartRateProperties,
			HeartBeatConverter heartBeatConverter,
			HeartRateConverter heartRateConverter,
			HeartRateFactory heartRateFactory
	) {
		return new HeartRateGenerator(
				heartRateProperties.getNbHeartBeats(),
				heartBeatConverter,
				heartRateConverter,
				heartRateFactory
		);
	}
}
